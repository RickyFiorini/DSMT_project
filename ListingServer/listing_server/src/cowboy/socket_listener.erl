-module(socket_listener).

%%API
-export([init/2, websocket_handle/2, websocket_info/2, terminate/3, websocket_init/1]).

% called when cowboy receives a request
init(Req, _State)->
  io:format("[Socket Listener init WS:~p] -> init PRE listing_registry ~n",[self()]),
  % handing the websocket to cowboy_websocket module passing it the request using infinite idle timeout option
  #{username:=CurrentUsername} = cowboy_req:match_qs([{username, nonempty}], Req),
  RegisterPid = whereis(listing_registry),
  InitialState = #{username => CurrentUsername, register_pid => RegisterPid},
  io:format("[Socket Listener init WS:~p] -> init POST listing_registry ~p~n",[self(), InitialState]),
  {cowboy_websocket, Req, InitialState, #{idle_timeout => infinity}}.

% stores the Username to Pid mapping in the registry
websocket_init(State)->
  #{username := CurrentUsername, register_pid := RegisterPid} = State,
  io:format("[Socket Listener websocket_init WS:~p] -> websocket_init PRE send ~p~n",[self(), State]),
  RegisterPid ! {register, CurrentUsername, self()},
  io:format("[Socket Listener websocket_init WS:~p] -> websocket_init POST send ~n",[self()]),
  {ok, State}.

% override of the cowboy_websocket websocket_handle/2 method
% Message from javascript
websocket_handle(_Frame={text, Listing}, State) ->
  io:format("[Socket Listener websocket_handle WS:~p] -> websocket_handle PRE decode ~n",[self()]),
  DecodedListing = jsone:try_decode(Listing),
  Reply = case DecodedListing of
            {ok, ListingMap, _} ->
              io:format("[Listing WS:~p] -> Received frame: ~p~n",[self(), ListingMap]),

              Operation = maps:get(<<"operation">>, ListingMap, undefined),
              io:format("[Listing WS:~p] -> Received operarion: ~p~n",[self(), Operation]),

              %% Check Operation
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
                      % correct format
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
                      % correct format
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
  io:format("[Socket Listener websocket_handle WS:~p] -> websocket_handle POST decode ~n",[self()]),
  Reply.

% called when cowboy receives an Erlang message
% (=> from another Erlang process).
websocket_info(Info, State) ->
  io:format("[Socket Listener websocket_info WS:~p] -> websocket_info ~n",[self()]),
  case Info of
    {forward_insert, Operation, BoxID, ReceivedMessageKeys, ReceivedMessageValues} ->
      %% "Insert" operation

      %% Take the listing attributes and send them in a json
      [[ListingID, Winner, Username, PokemonName, ImageURL]] = ReceivedMessageValues,
      io:format("[Listing WS:~p] -> Received forwarded message: ~p~n",[self(), ReceivedMessageValues]),
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
      io:format("DELETE OPERATION ~n"),
      Json = jsone:encode(#{<<"type">> => <<"listing">>,
        <<"boxID">> => BoxID,
        <<"listingID">> => ListingID,
        <<"operation">> => Operation
      }),
      {reply, {text, Json}, State};

    %% "Update" Operation
    {forward_update, ListingID, Trader} ->
      io:format("UPDATE OPERATION ~n"),
      Json = jsone:encode(#{<<"type">> => <<"listing">>,
        <<"listingID">> => ListingID,
        <<"trader">> => Trader,
        <<"operation">> => <<"update">>
      }),
      {reply, {text, Json}, State};

    {sql_error, CreationTime} ->
      io:format("[Listing WS:~p] -> Received error from SQL~n",[self()]),
      Json = jsone:encode(#{<<"type">> => <<"error">>, <<"messageID">> => CreationTime}),
      {reply, {text, Json}, State};

    _ ->
      {ok, State}
  end.

% called when connection terminate
terminate(Reason, _Req, State) ->
  io:format("[Listing WS:~p] -> Closed websocket connection, Reason: ~p ~n", [self(), Reason]),
  #{username := Username, register_pid := RegisterPid} = State,
  RegisterPid ! {unregister, Username},
  {ok, State}.