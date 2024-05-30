%%%-------------------------------------------------------------------
%% @doc offer_server public API
%% @end
%%%-------------------------------------------------------------------

-module(offer_server_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    io:format("[Offer Server] -> starting supervisor~n"),
    offer_server_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
