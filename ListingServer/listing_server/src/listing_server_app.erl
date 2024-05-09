%%%-------------------------------------------------------------------
%% @doc listing_server public API
%% @end
%%%-------------------------------------------------------------------

-module(listing_server_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    io:format("[Listing Server App] -> starting supervisor ~n"),
    listing_server_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
