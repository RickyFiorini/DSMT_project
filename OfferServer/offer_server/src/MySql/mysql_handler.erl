-module(mysql_handler).

% APIs
-export([start_db/0]).

run_loop(Conn) ->
  receive
    {save_offer,Trader, ListingID,BoxId,CreationTime, Caller} ->
      io:format("[Offer MySQL] -> Saving tuple (~p, ~p, ~p, ~p)~n",[ListingID,Trader,BoxId,CreationTime]),
      save_offer(Conn,Trader, ListingID,BoxId,CreationTime, Caller);
    {trade,Username,ListingID,BoxId,Caller} ->
      io:format("[Offer MySQL] -> TRADE tuple (~p, ~p, ~p, ~p)~n",[ListingID,Username,BoxId,Username]),
      trade(Conn,Username,ListingID,BoxId, Caller);
    {delete_offer,OfferID,BoxId, Caller} ->
      io:format("[Offer MySQL] -> Deleting Offer  ~p~n",[OfferID]),
      delete_offer(Conn,OfferID,BoxId, Caller),
      Caller ! {ok, <<"saved">>};
    {update_listing,ListingID,Caller} ->
      io:format("[Offer MySQL] -> Delete Listing message  ~p~n",[ListingID]),
      selectListedValueTrade(Conn, ListingID,Caller),
      Caller ! {ok, <<"message">>}
  end,
  run_loop(Conn).


save_offer(Conn,Trader, ListingID,BoxId,CreationTime, Caller) ->
  Statement = "INSERT INTO offer(listingID, trader,boxID,timestamp) VALUES (?, ?, ?, FROM_UNIXTIME(?*0.001))",
  case mysql:prepare(Conn, Statement) of
        {error, Reason} ->
          io:format("[Offer MySQL] -> Failed to save: ~p~n",[Reason]),
          Caller ! {sql_error, CreationTime};
        {ok, StatementID} ->
          Bindings = [ListingID, Trader, BoxId, CreationTime],
          case mysql:execute(Conn, StatementID, Bindings) of
            {error, Reason} ->
              io:format("[offer MySQL] -> Failed to save: ~p~n", [Reason]),
              Caller ! {sql_error, CreationTime};
            Result ->
              io:format("[offer MySQL] -> ~p~n",[Result]),
              boxUpdateStatement(Conn,BoxId,Caller,1), %change listing value of the corresponding Pokémon offer to 1
              getOfferinfo(Conn,BoxId,Caller,CreationTime) % Recover Offer info from BoxID to send them back

          end
  end.




getOfferinfo(Conn, BoxID, Caller,CreationTime) ->
  io:format("[MYSQL HANDLER] -> get offer info from BoxID ~p~n", [BoxID]),
  Statement = "SELECT o.ID,b.username, p.pokemonName, p.imageURL, p.primaryType, p.attack, p.defense " ++
    "FROM offer o " ++
    "JOIN box b ON o.boxID = b.ID " ++
    "JOIN pokemon p ON b.pokemonID = p.ID " ++
    "WHERE b.ID= ? ",
  case mysql:prepare(Conn, Statement) of
    {error, Reason} ->
      io:format("[Offer MySQL] -> Failed to save: ~p~n",[Reason]),
      Caller ! {sql_error, CreationTime};
    {ok, StatementID} ->
      case mysql:execute(Conn,StatementID,[BoxID]) of
        {error, Reason} ->
          io:format("[offer MySQL] -> Failed to save: ~p~n", [Reason]),
          Caller ! {sql_error,CreationTime};
        {ok, OfferKeys, OfferValues} ->
          io:format("[MYSQL HANDLER] -> Select Result KEYS: ~p~n", [OfferKeys]),
          io:format("[MYSQL HANDLER] -> Select Result VALUES: ~p~n", [OfferValues]),
          Caller ! {ok, OfferKeys, OfferValues}
      end
end.

boxUpdateStatement(Conn,BoxId,Caller,ListedValue) ->
  UpdateStatement = "UPDATE box SET listed = ? WHERE ID = ?",
  case mysql:prepare(Conn,  UpdateStatement) of
    {error, Reason} ->
     io:format("[Offer MySQL] -> Failed to UPDATE: ~p~n",[Reason]),
     Caller ! {sql_error, BoxId};
  {ok, StatementID} ->
    case mysql:execute(Conn, StatementID,[ListedValue,BoxId]) of
    {error, Reason} ->
     io:format("[offer MySQL] -> Failed to update: ~p~n", [Reason]),
     Caller ! {sql_error, BoxId};
     Result ->
     io:format("[offer MySQL] -> ~p~n",[Result])
    end
  end.


delete_offer(Conn,OfferID,BoxID,Caller) ->
  DeleteStatement = "DELETE FROM offer WHERE ID = ? ",
  case mysql:prepare(Conn,  DeleteStatement) of
    {error, Reason} ->
      io:format("[Offer MySQL] -> Failed to DELETE: ~p~n",[Reason]),
      Caller ! {sql_error, OfferID};
    {ok, StatementID} ->
    case mysql:execute(Conn, StatementID, [OfferID]) of
       {error, Reason} ->
        io:format("[offer MySQL] -> Failed to update: ~p~n", [Reason]),
        Caller ! {sql_error, OfferID};
       Result ->
       io:format("[offer MySQL] -> DELETE ~p~n",[Result]),
       boxUpdateStatement(Conn,BoxID,Caller,0) %change the listing value of the corresponding Pokémon offer to 0


 end
end.

trade(Conn, Username, ListingID, BoxId,Caller) ->
  TradeStatement = "UPDATE listing SET boxIDwinner = ? WHERE ID = ?",
  case mysql:prepare(Conn,  TradeStatement) of
    {error, Reason} ->
      io:format("[Offer MySQL] -> Failed to TRADE: ~p~n",[Reason]),
      Caller ! {sql_error, BoxId};
    {ok, StatementID} ->
      case mysql:execute(Conn, StatementID,[BoxId,ListingID]) of
        {error, Reason} ->
          io:format("[offer MySQL] -> Failed to trade: ~p~n", [Reason]),
          Caller ! {sql_error, BoxId};
        Result ->
          io:format("[offer MySQL] -> ~p~n",[Result]),
          selectListedValueTrade(Conn,ListingID,Caller),%change listing value of the corresponding Pokémon offer to 0
          updateBoxTradeWinner(Conn,ListingID,BoxId,Username,Caller) %% the listing's creator


end
  end.


selectListedValueTrade(Conn, ListingID, Caller) ->
  io:format("[Offer MySQL] -> select listing id: : ~p~n",[ListingID]),
  Statement = "SELECT o.boxID, o.ID " ++
    "FROM offer o " ++
    "WHERE o.listingID = ? ",
  case mysql:prepare(Conn, Statement) of
    {error, Reason} ->
      io:format("[Offer MySQL] -> Failed to select boxID: ~p~n",[Reason]),
      Caller ! {sql_error, Caller};
    {ok, StatementID} ->
      case mysql:execute(Conn,StatementID,[ListingID]) of
        {error, Reason} ->
          io:format("[offer MySQL] -> Failed to select boxID: ~p~n", [Reason]),
          Caller ! {sql_error,ListingID};
        {ok, _, OfferValues} ->
          io:format("[MYSQL HANDLER] -> Select Result VALUES: ~p~n", [OfferValues]),
          lists:foreach(
            fun([BoxID, OfferID]) ->
              delete_offer(Conn,OfferID,BoxID,Caller)
            end,
            OfferValues
          )
      end
  end.


updateBoxTradeListing(Conn,Username,BoxId, Caller) ->
  UpdateStatement = "UPDATE box SET username = ? WHERE ID = ?",
  case mysql:prepare(Conn,  UpdateStatement) of
    {error, Reason} ->
      io:format("[Offer MySQL] -> Failed to UPDATE username box: ~p~n",[Reason]),
      Caller ! {sql_error, BoxId};
    {ok, StatementID} ->
      case mysql:execute(Conn, StatementID,[Username,BoxId]) of
        {error, Reason} ->
          io:format("[offer MySQL] -> Failed to update username box: ~p~n", [Reason]),
          Caller ! {sql_error, BoxId};
        Result ->
          io:format("[offer MySQL] -> ~p~n",[Result])
      end
  end.

updateBoxTradeWinner(Conn, ListingID,BoxId,Username,Caller) ->
  SelectStatement = "SELECT l.boxID, b.username " ++
     "FROM listing l " ++
     "JOIN box b ON l.boxIDwinner = b.ID " ++
     "WHERE l.ID= ? ",
  case mysql:prepare(Conn, SelectStatement) of
    {error, Reason} ->
      io:format("[MySQL] -> Failed to prepare SELECT statement: ~p~n", [Reason]),
      Caller ! {sql_error, Reason};
    {ok, SelectStatementID} ->
      case mysql:execute(Conn, SelectStatementID, [ListingID]) of
        {error, Reason} ->
          io:format("[MySQL] -> Failed to execute SELECT statement: ~p~n", [Reason]),
          Caller ! {sql_error, Reason};
        {ok, _, [[BoxIdListing, Trader]]} ->
          updateBoxTradeListing(Conn,Trader,BoxIdListing,Caller),
          updateBoxTradeListing(Conn,Username,BoxId,Caller),
          boxUpdateStatement(Conn,BoxIdListing,Caller,0),
          Caller ! {ok, Trader}
end
  end.

start_db() ->
  {ok, DBConfig} = application:get_env(db_config),
  {ok, Conn} = mysql:start_link(DBConfig),
  run_loop(Conn).

