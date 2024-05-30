-module(offer_registry).

-export([start_offer_registry/0]).
-import(mysql_handler, [start_db/0]).

start_offer_registry()->
  Pid = spawn(fun() -> registry_loop(#{}) end),
  io:format("[Offer Registry] -> Starting register at pid ~p~n",[Pid]),
  DBPid = spawn(fun() -> start_db() end),
  io:format("[Offer Registry] -> Starting Database Connection at pid ~p~n",[DBPid]),
  register(database_connection, DBPid),
  register(offer_registry, Pid).

handle_mysql(ListingID, Trader, BoxID,Instant, Caller,Mappings) ->
  DBPid = whereis(database_connection),
  DBPid ! {save_offer, Trader, ListingID, BoxID, Instant, self()},
  receive
    {ok, OfferKeys,OfferValues} ->
      io:format("[Offer Registry] -> Received Mappings ~p ~p~n",[OfferKeys, OfferValues]),
      % To forward the listing to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid}  ->
              io:format("[Offer Registry] -> forwarded_offer insert to ~p with pid ~p~n", [Username, Pid]),
              Pid ! {forwarded_offer, insert, OfferValues, OfferKeys,BoxID};
            _ ->
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      );
 {sql_error, Reason} ->
   Caller ! {sql_error, Reason}
  end.

registry_loop(Mappings) ->
  receive 
    {register,Username,ListingID,Pid} ->
      io:format("[Offer Registry] -> add mapping by ~p with pid ~p~n",[Username, Pid]),
      Values = #{pid => Pid, listingID => ListingID},
      NewMappings = maps:put(Username, Values, Mappings),
      registry_loop(NewMappings);

    {forward,Trader,ListingID,BoxID, Instant,Caller} ->
      io:format("[Offer Registry] -> Forward Offer"),
      spawn(fun() -> handle_mysql(
        ListingID,
        Trader,
        BoxID,
        Instant, 
        Caller,
        Mappings
      ) end),
      registry_loop(Mappings);

    {unregister, Trader} ->
      io:format("[Chat Registry] -> Removing mapping ~p from registry~n", [Trader]),
      NewMappings = maps:remove(Trader, Mappings),
      registry_loop(NewMappings);

    {delete,Trader,OfferID,BoxID,Caller} ->
      io:format("[Offer Registry] -> Delete offer by ~p with pid ~p~n",[Trader, Caller]),
      spawn(fun() -> handle_mysql_delete(
        Trader,
        OfferID,
        BoxID,
        Caller,
        Mappings
      ) end),
      registry_loop(Mappings);

    {trade,Owner,ListingID,BoxID,OfferID,Caller} ->
      io:format("[Offer Registry] -> Trade offer by ~p with pid ~p~n",[Owner, Caller]),
      spawn(fun() -> handle_mysql_trade(
      Owner,
      ListingID,
      BoxID,
      OfferID,
      Caller,
      Mappings
      ) end),
      registry_loop(Mappings)
end.

handle_mysql_delete(Trader,OfferID,BoxID,Caller, Mappings) ->
  DBPid = whereis(database_connection),
  DBPid ! {delete_offer,OfferID,BoxID,self()},
  receive
    {ok, _Message} ->
      % To forward the message to each user of the registry
      maps:fold(
        fun(Username, Values, _) ->
          case Values of
            #{pid := Pid}  ->
              io:format("[Offer Registry] -> forwarded_offer delete to ~p with pid ~p~n", [Username, Pid]),
              Pid ! {forwarded_offer,delete,OfferID,BoxID,Trader};
            _ ->
              error_logger:error_msg("Invalid value for username ~p", [Username])
          end
        end,
        ok,
        Mappings
      );
    {sql_error, Reason} ->
      Caller ! {sql_error, Reason}
  end.


handle_mysql_trade(Owner, ListingID, BoxID,OfferID, Caller, Mappings) ->
  DBPid = whereis(database_connection),
  DBPid ! {trade,Owner,ListingID,BoxID,self()},
  receive
    {ok, Trader} ->
      % To forward the message to each user of the registry
      maps:fold(
        fun(User, Values, _) ->
          case Values of
            #{pid := Pid}  ->
              io:format("[Offer Registry] -> forwarded_offer trade to ~p with pid ~p~n", [User, Pid]),
              Pid ! {forwarded_offer,trade,OfferID,Trader,Owner};
            _ ->
              error_logger:error_msg("Invalid value for username ~p", [User])
          end
        end,
        ok,
        Mappings
      );
    {sql_error, Reason} ->
      Caller ! {sql_error, Reason}
  end.