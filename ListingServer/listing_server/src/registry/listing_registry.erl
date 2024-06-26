-module(listing_registry).

-export([start_listing_registry/0]).
-import(mysql_handler, [start_db/0]).

%% Start listing registry and database erlang processes
start_listing_registry()->
  Pid = spawn(fun() -> registry_loop(#{}) end),
  io:format("[Listing Registry] -> Starting registry at pid ~p~n",[Pid]),
  DBPid = spawn(fun() -> start_db() end),
  io:format("[Listing Registry] -> Starting Database Connection at pid ~p~n",[DBPid]),
  register(database_connection, DBPid),
  register(listing_registry, Pid).


%% Handles client-erlang-database message exchange
registry_loop(Mappings) ->
  receive
    % Add a new user to the registry
    {register, Username, Pid} ->
      io:format("[Listing Registry] -> adding user ~p with pid ~p~n",[Username, Pid]),
      Values = #{pid => Pid},
      NewMappings = maps:put(Username, Values, Mappings),
      registry_loop(NewMappings);

    % Spawn a process that forward the request to mysql to insert a listing
    {insert, ListingUsername, BoxID, Timestamp, Operation, Sender, SocketListenerPID} ->
      spawn(fun() -> handle_mysql(
        ListingUsername,
        BoxID,
        Timestamp,
        Operation,
        SocketListenerPID,
        Mappings
      ) end),
      registry_loop(Mappings);

    % Spawn a process that forward the request to mysql to delete a listing
    {delete, ListingUsername, ListingID, Timestamp, Operation, Sender, SocketListenerPID} ->
      spawn(fun() -> handle_mysql(
        ListingUsername,
        ListingID,
        Timestamp,
        Operation,
        SocketListenerPID,
        Mappings
      ) end),
      registry_loop(Mappings);

    % Spawn a process that forward the request to mysql to update a listing
    {updateListing, ListingID, Trader} ->
      % To forward the update of the listing to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid} ->
              Pid ! {forward_update, ListingID, Trader};
            _ ->
              % Handle invalid or unexpected data
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      ),
      registry_loop(Mappings);

    % Unregister a user
    {unregister, Username} ->
      io:format("[Listing Registry] -> removing user ~p from registry ~n", [Username]),
      NewMappings = maps:remove(Username, Mappings),
      registry_loop(NewMappings)

  end.


%% Method than handle Erlang-MySQL communication
handle_mysql(ListingUsername, ID, Timestamp, Operation, SocketListenerPID, Mappings) ->
  DBPid = whereis(database_connection),

  %% Check operation
  case Operation of
    %% Send "insert" request
    <<"insert">> ->
      DBPid ! {insert_listing, ListingUsername, ID, Timestamp, self()};

    %% Send "delete" request
    <<"delete">> ->
      DBPid ! {delete_listing, ID, Timestamp, self()}
  end,

  %% Handle mysql reply
  receive
    %% Mysql reply to "insert" request
    {insert_ok, MessageKeys, MessageValues} ->
      % To forward the listing to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid} ->
              Pid ! {forward_insert, Operation, ID, MessageKeys, MessageValues};
            _ ->
              % Handle invalid or unexpected data
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      );

    %% Mysql reply to "delete" request
    {delete_ok, ListingID}->
      % To forward the listing to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid} ->
              Pid ! {forward_delete, Operation, ID, ListingID};
            _ ->
              % Handle invalid or unexpected data
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      ),

    %% Message from the Erlang node that handle the offers
    {ok, OfferNode} = application:get_env(offer_node),
      ConnectedNodes = nodes(),
      if ConnectedNodes == [] ->
        net_kernel:connect_node(OfferNode);
        true -> ok
      end,
      {offer_registry,OfferNode} ! {listing_deleteUpdate, ListingID};

{sql_error, Reason} ->
      SocketListenerPID ! {sql_error, Reason}
  end.
