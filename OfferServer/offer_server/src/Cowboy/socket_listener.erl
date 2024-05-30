-module(socket_listener).

%%API
-export([init/2, websocket_handle/2, websocket_info/2, terminate/3, websocket_init/1]).

% called when cowboy receives a request
init(Req, _State)->
  io:format("[OFFER WS:~p] -> Received frame: ~p~n",[self(),Req]),
  % handing the websocket to cowboy_websocket module passing it the request using infinite idle timeout option
  #{username:=Username,listingID:=ListingID} = cowboy_req:match_qs([{listingID, nonempty},{username, nonempty}], Req),
  RegisterPid = whereis(offer_registry),
  InitialState = #{register_pid => RegisterPid, username => Username,listingID => ListingID},
  {cowboy_websocket, Req, InitialState, #{idle_timeout => infinity}}.


websocket_init(State)->
  io:format("[OFFER WS:~p] -> STATE: ~p~n",[self(),State]),
  #{register_pid := RegisterPid,username:=Username,listingID := ListingID} = State,
  RegisterPid ! {register,Username,ListingID,self()},
  {ok, State}.

% override of the cowboy_websocket websocket_handle/2 method
websocket_handle(_Frame={text, Message}, State) ->
  DecodedOffer = jsone:try_decode(Message),
  Reply = case DecodedOffer of
    {ok, OfferMap, _} ->
      io:format("[OFFER WS:~p] -> Received mapping: ~p~n",[self(), OfferMap]),

      % body unpack
      BoxID = maps:get(<<"BoxID">>, OfferMap, undefined),
      Instant = maps:get(<<"timestamp">>, OfferMap, undefined),
      OfferID=  maps:get(<<"OfferID">>, OfferMap, undefined),
      Operation= maps:get(<<"operation">>, OfferMap, undefined),

      % limit case handling
      case Operation of
        <<"delete">> ->
          if OfferID == undefined orelse BoxID == undefined ->
            JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"message">> => "NOT VALID PARAMETER"}),
            {reply, {text, JsonResponse}, State};
           true ->
             #{username:=Username,register_pid := RegisterPid} = State,
             RegisterPid ! {delete,Username,OfferID,BoxID,self()}, %delete offer request
             io:format("[OFFER WS:~p] -> DELETE Offer Request send: ~p~n",[self(),State]),
            {ok, State}
          end;
        <<"insert">>  ->
          if BoxID == undefined orelse Instant == undefined ->
            JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"message">> => "NOT VALID PARAMETER"}),
            {reply, {text, JsonResponse}, State};
          true ->
          #{username:=Username,listingID:= ListingID,register_pid := RegisterPid} = State,
          RegisterPid ! {forward,Username,ListingID,BoxID, Instant, self()},
          io:format("[OFFER WS:~p] -> ADD Offer Request: ~p~n",[self(),State]),
          {ok, State}
          end;
        <<"trade">>  ->
          if BoxID == undefined orelse OfferID == undefined ->
            JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"message">> => "NOT VALID PARAMETER"}),
            {reply, {text, JsonResponse}, State};
          true ->
              #{username:=Username,listingID:= ListingID,register_pid := RegisterPid} = State,
              RegisterPid ! {trade,Username,ListingID,BoxID,OfferID, self()},
              io:format("[OFFER WS:~p] -> Trade Request: ~p~n",[self(),State]),
              {ok, State}
          end;
        _ ->
           JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"reason">> => <<"invalid operation">>}),
           {reply, {text, JsonResponse}, State}
         end;
    _ ->
      JsonResponse = jsone:encode(#{<<"type">> => <<"error">>, <<"reason">> => <<"invalid payload">>}),
      {reply, {text, JsonResponse}, State}
  end,
  Reply.

% called when cowboy receives an Erlang message  
% (=> from another Erlang process).
websocket_info(Info, State) ->
  io:format("[Socket Listener websocket_info WS:~p] ~n",[self()]),
  case Info of
    {forwarded_offer,insert,ReceivedOfferValues,ReceivedOfferKeys,BoxID} ->
      io:format("[Socket Listener websocket_info WS:~p] -> ReceivedOfferValues and Keys for Box ~p ~p ~p~n",[self(),ReceivedOfferKeys,ReceivedOfferValues,BoxID]),
      [[OfferID,Trader,PokemonName,ImageURL,PrimaryType,Attack,Defense]] = ReceivedOfferValues,
      Json = jsone:encode(#{<<"type">> => <<"offer">>,
        <<"OfferID">> => OfferID,
        <<"ImageUrl">> => ImageURL,
        <<"PokemonName">> => PokemonName,
        <<"Trader">> => Trader,
        <<"PrimaryType">> => PrimaryType,
        <<"Attack">> => Attack,
        <<"Defense">> => Defense,
        <<"BoxID">> => BoxID
       }),
       {reply, {text, Json}, State};

    {forwarded_offer,delete,OfferID,BoxID,Trader} ->
      io:format("[Offer WS:~p] -> Received Delete Request: ~p ~p ~n ",[self(), OfferID,BoxID]),
      Json = jsone:encode(#{<<"type">> => <<"delete">>,
        <<"OfferID">> => OfferID,
        <<"Trader">> => Trader,
        <<"BoxID">> => BoxID
      }),
      {reply, {text, Json}, State};

    {forwarded_offer,trade,OfferID,Trader,Username} ->
      io:format("[Offer WS:~p] -> Received Trade Request: ~p ~p  ~p  ~n ",[self(), OfferID,Trader,Username]),
      Json = jsone:encode(#{<<"type">> => <<"trade">>,
        <<"OfferID">> => OfferID,
        <<"Trader">> => Trader,
        <<"UserListing">> => Username
      }),
      {reply, {text, Json}, State};
    _ ->
      {ok, State}
  end.

% called when connection terminate
terminate(Reason, _Req, State) ->
  io:format("[Offer WS:~p] -> Closed websocket connection, Reason: ~p ~n", [self(), Reason]),
  #{username:= Username,register_pid := RegisterPid} = State,
  RegisterPid ! {unregister,Username},
  {ok, State}.
