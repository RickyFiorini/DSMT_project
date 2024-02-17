<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>
<%@ page import="it.unipi.dsmt.app.dtos.OfferDTO" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Listing</title>
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp"/>

    <%-- Get current username --%>
    <% String currentUser = AccessController.getUsername(request); %>

    <%-- Get listing info from request and show them aside --%>
    <% ListingDTO listing = (ListingDTO) request.getAttribute("listing"); %>
    <div class="left listing-info">
        <img src="<%=listing.getImageURL()%>" class="img-listing" alt="Image placeholder">
        <h1>
            <%=listing.getPokemonName()%>
        </h1>
        <h2>
            <%=listing.getPokemonType()%>
        </h2>
        <h3>
            <%=listing.getUsername()%>
        </h3>
        <%-- If the current user is not the owner of the listing, he can make an offer --%>
        <% if (!currentUser.equals(listing.getUsername())) { %>
        <%-- TODO MOSTRARE IL BOX PER FARE UNA OFFER
               (NEL SERVLET LA VOGLIO COME "POST" REQUEST) --%>
        <button type="button" onclick="showBox()"> MAKE AN OFFER </button>
        <% } %>
    </div>

    <%-- Get list of offers from request and show them at the center --%>
    <div class="center offers-wrapper">
        <% for(OfferDTO offer : (List<OfferDTO>)request.getAttribute("offerList")){ %>
        <div class="card offer-card" id="<%=offer.getOfferID()%>">
            <img src="<%=offer.getImageURL()%>" class="img-listing" alt="Image placeholder">
            <h1>
                <%=offer.getPokemonName()%>
            </h1>
            <h2>
                <%=offer.getPokemonType()%>
            </h2>
            <h3>
                <%=offer.getTrader()%>
            </h3>
            <%-- If the current user is the owner of the listing, he can accept an offer --%>
            <% if (currentUser.equals(listing.getUsername())) { %>
            <%-- TODO ACCETTARE UNA OFFER, EFFETTUARE IL TRADE
                  E NOTIFICARE TUTTI COLORO CHE HANNO PARTECIPATO --%>
            <%-- TODO IL BUTTON DOVREBBE AVERE TYPE=SUBMIT PER FARE UNA "POST" REQUEST --%>
            <button type="button" onclick="acceptOffer()"> TRADE </button>
            <% } %>
        </div>
        <% } %>
    </div>

</div>

</body>
</html>
