-module(listing_registry).

-export([start_listing_registry/0]).
-import(mysql_handler, [start_db/0]).

%% Start listing registry and database erlang processes
start_listing_registry()->
  Pid = spawn(fun() -> registry_loop(#{}) end),
  io:format("[Listing Registry] -> Starting registry at pid ~p~n",[Pid]),

  %% TODO IMPLEMENTARE DATABASE HANDLER
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

    % Spawn a process that forward the request to mysql
    {forward, BoxID, Timestamp, Operation, Sender, SocketListenerPID} ->
      spawn(fun() -> handle_mysql(
        BoxID,
        Timestamp,
        Operation,
        SocketListenerPID,
        Mappings
      ) end),
      registry_loop(Mappings);

    % Unregister a user
    {unregister, Username} ->
      io:format("[Listing Registry] -> removing user ~p from registry ~n", [Username]),
      NewMappings = maps:remove(Username, Mappings),
      registry_loop(NewMappings)
  end.


%% Method than handle Erlang-MySQL communication
handle_mysql(BoxID, Timestamp, Operation, SocketListenerPID, Mappings) ->
  DBPid = whereis(database_connection),

  %% Check operation
  case Operation of
    %% Send "insert" request
    <<"insert">> ->
      DBPid ! {insert_listing, BoxID, Timestamp, self()};

    %% Send "delete" request
    %% TODO TO IMPLEMENT DELETE LISTING
    <<"delete">> ->
      io:format("REQUESTING DELETE LISTING OPERATION ~n")
  end,

  %% Handle mysql reply
  receive
    %% Mysql reply to "insert" request
    {ok, MessageKeys, MessageValues} ->
      io:format("[Message Handler] -> forwarding message PRE fold ~p~n",[BoxID]),
      % To forward the listing to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid} ->
              Pid ! {forwarded_message, Operation, MessageKeys, MessageValues};
            _ ->
              % Handle invalid or unexpected data
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      );
    {sql_error, Reason} ->
      SocketListenerPID ! {sql_error, Reason}
  end.