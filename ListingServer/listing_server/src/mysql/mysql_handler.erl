-module(mysql_handler).

%% API
-export([start_db/0]).

%% Loop to receive listings and execute queries
run_loop(Conn) ->
  receive
    %% Insert Listing operation
    {insert_listing, BoxID, Timestamp, RegistryPID} ->
      insert_listing(Conn, BoxID, Timestamp, RegistryPID)

    %% TODO RECEIVE DELETE LISTING

  end,
  run_loop(Conn).


%% To insert a new listing
insert_listing(Conn, BoxID, Timestamp, RegistryPID) ->
  io:format("[MYSQL HANDLER] -> inserting listing with BoxID ~p~n", [BoxID]),
  InsertStatement = "INSERT INTO listing(boxID, winner, timestamp) VALUES (?, ?, FROM_UNIXTIME(? * 0.001))",

  %% Check query preparation (INSERT)
  case mysql:prepare(Conn, InsertStatement) of

    {error, Reason} ->
      io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
      RegistryPID ! {sql_error, Timestamp};

    {ok, StatementID} ->
      %% Check query execution (INSERT)
      case mysql:execute(Conn, StatementID, [BoxID, null, Timestamp]) of

        {error, Reason} ->
          io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
          RegistryPID ! {sql_error, Timestamp};

        Result ->
          io:format("[MYSQL HANDLER] -> Insert Result: ~p~n", [Result]),

          %% Set the selected pokemon in the box as listed
          set_box_listed(Conn, BoxID, 1, Timestamp, RegistryPID),

          %% Now we have to retrieve the new listing info and send them
          select_listing(Conn, BoxID, Timestamp, RegistryPID)
      end
  end.

%% To update the "listed" attribute in the database
set_box_listed(Conn, BoxID, Listed, Timestamp, RegistryPID) ->
  io:format("[MYSQL HANDLER] -> Update listing with BoxID ~p: setting listed to ~p~n", [BoxID, Listed]),
  SelectStatement = "UPDATE box SET listed = ? WHERE ID = ? ",

  %% Check query preparation (UPDATE)
  case mysql:prepare(Conn, SelectStatement) of

    {error, Reason} ->
      io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
      RegistryPID ! {sql_error, Timestamp};

    {ok, StatementID} ->
      %% Check query execution (UPDATE)
      case mysql:execute(Conn, StatementID, [Listed, BoxID]) of

        {error, Reason} ->
          io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
          RegistryPID ! {sql_error, Timestamp};

        %% Fetch the listing returned from the database and send it back to the registry
        Result ->
          io:format("[MYSQL HANDLER] -> UPDATE Result: ~p~n", [Result])
      end
  end.

%% To retrieve the inserted listing from the database and send it back to the registry
select_listing(Conn, BoxID, Timestamp, RegistryPID) ->
  io:format("[MYSQL HANDLER] -> get listing with BoxID ~p~n", [BoxID]),
  SelectStatement = "SELECT l.ID, l.winner, b.username, p.pokemonName, p.imageURL " ++
    "FROM listing l " ++
    "JOIN box b ON l.boxID = b.ID " ++
    "JOIN pokemon p ON b.pokemonID = p.ID " ++
    "WHERE b.ID= ? ",

  %% Check query preparation (SELECT)
  case mysql:prepare(Conn, SelectStatement) of

    {error, Reason} ->
      io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
      RegistryPID ! {sql_error, Timestamp};

    {ok, StatementID} ->
      %% Check query execution (SELECT)
      case mysql:execute(Conn, StatementID, [BoxID]) of

        {error, Reason} ->
          io:format("[MYSQL HANDLER] -> failed to insert listing: ~p~n", [Reason]),
          RegistryPID ! {sql_error, Timestamp};

        %% Fetch the listing returned from the database and send it back to the registry
        {ok, ListingKeys, ListingValues} ->
          io:format("[MYSQL HANDLER] -> Select Result KEYS: ~p~n", [ListingKeys]),
          io:format("[MYSQL HANDLER] -> Select Result VALUES: ~p~n", [ListingValues]),
          RegistryPID ! {ok, ListingKeys, ListingValues}
      end
  end.


%% To get database configuration and start the connection with mysql
start_db() ->
  {ok, DBConfig} = application:get_env(db_config),
  {ok, Conn} = mysql:start_link(DBConfig),
  io:format("[MYSQL HANDLER] -> starting connection ~n"),
  run_loop(Conn).
