-module(socket_listener).

%%API
-export([init/2, websocket_handle/2, websocket_info/2, terminate/3, websocket_init/1]).

% Called when cowboy receives a request
init(Req, _State)->
  % handing the websocket to cowboy_websocket module passing it the request using infinite idle timeout option
  #{username:=CurrentUsername} = cowboy_req:match_qs([{username, nonempty}], Req),
  RegisterPid = whereis(listing_registry),
  InitialState = #{username => CurrentUsername, register_pid => RegisterPid},
  {cowboy_websocket, Req, InitialState, #{idle_timeout => infinity}}.

% Stores the Username to Pid mapping in the registry
websocket_init(State)->
  #{username := CurrentUsername, register_pid := RegisterPid} = State,
  RegisterPid ! {register, CurrentUsername, self()},
  {ok, State}.

% Override of the cowboy_websocket websocket_handle/2 method
% Message from javascript
websocket_handle(_Frame={text, Listing}, State) ->
  DecodedListing = jsone:try_decode(Listing),
  Reply = case DecodedListing of
            {ok, ListingMap, _} ->

              %% Check Operation
              Operation = maps:get(<<"operation">>, ListingMap, undefined),
              case Operation of
                <<"insert">> ->
                  % request unpack
                  Username = maps:get(<<"username">>, ListingMap, undefined),
                  BoxID = maps:get(<<"boxID">>, ListingMap, undefined),
                  Timestamp = maps:get(<<"timestamp">>, ListingMap, undefined),

                  % Limit case handling
                  if
                    BoxID == undefined orelse Operation == undefined ->
                      JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"messageID">> => BoxID}),
                      {reply, {text, JsonResponse}, State};
                    true ->
                      % Correct format
                      #{username := Sender, register_pid := RegisterPid} = State,

                      % Forward listing to the registry, that will broadcast to the other users and send a request to mysql
                      RegisterPid ! {insert, Username, BoxID, Timestamp, Operation, Sender, self()},
                      {ok, State}
                  end;

                <<"delete">> ->
                  Username = maps:get(<<"username">>, ListingMap, undefined),
                  ListingID = maps:get(<<"listingID">>, ListingMap, undefined),
                  Timestamp = maps:get(<<"timestamp">>, ListingMap, undefined),

                  % Limit case handling
                  if
                    ListingID == undefined orelse Operation == undefined ->
                      JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"messageID">> => ListingID}),
                      {reply, {text, JsonResponse}, State};
                    true ->
                      % Correct format
                      #{username := Sender, register_pid := RegisterPid} = State,

                      % Forward listing to the registry, that will broadcast to the other users and send a request to mysql
                      RegisterPid ! {delete, Username, ListingID, Timestamp, Operation, Sender, self()},
                      {ok, State}
                  end
              end;

            % Default
            _ ->
              JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"reason">> => <<"invalid payload">>}),
              {reply, {text, JsonResponse}, State}
          end,
  Reply.

% Called when cowboy receives an Erlang message from another Erlang process
websocket_info(Info, State) ->
  case Info of
    %% "Insert" operation
    {forward_insert, Operation, BoxID, ReceivedMessageKeys, ReceivedMessageValues} ->
      %% Take the listing attributes and send them in a json
      [[ListingID, Winner, Username, PokemonName, ImageURL]] = ReceivedMessageValues,
      Json = jsone:encode(#{<<"type">> => <<"listing">>,
        <<"listingID">> => ListingID,
        <<"winner">> => Winner,
        <<"username">> => Username,
        <<"pokemonName">> => PokemonName,
        <<"imageURL">> => ImageURL,
        <<"operation">> => Operation,
        <<"boxID">> => BoxID
      }),
      {reply, {text, Json}, State};

    %% "Delete" Operation
    {forward_delete, Operation, BoxID, ListingID} ->
      Json = jsone:encode(#{<<"type">> => <<"listing">>,
        <<"boxID">> => BoxID,
        <<"listingID">> => ListingID,
        <<"operation">> => Operation
      }),
      {reply, {text, Json}, State};

    %% "Update" Operation
    {forward_update, ListingID, Trader} ->
      Json = jsone:encode(#{<<"type">> => <<"listing">>,
        <<"listingID">> => ListingID,
        <<"trader">> => Trader,
        <<"operation">> => <<"update">>
      }),
      {reply, {text, Json}, State};

    %% Error from MySQL
    {sql_error, CreationTime} ->
      Json = jsone:encode(#{<<"type">> => <<"error">>, <<"messageID">> => CreationTime}),
      {reply, {text, Json}, State};

    %% Default
    _ ->
      {ok, State}
  end.

% Called when connection terminates
terminate(Reason, _Req, State) ->
  io:format("[Listing WS:~p] -> Closed websocket connection, Reason: ~p ~n", [self(), Reason]),
  #{username := Username, register_pid := RegisterPid} = State,
  RegisterPid ! {unregister, Username},
  {ok, State}.