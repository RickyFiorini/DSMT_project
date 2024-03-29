<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>
<%@ page import="it.unipi.dsmt.app.dtos.OfferDTO" %>
<%@ page import="it.unipi.dsmt.app.dtos.BoxDTO" %>
<%@ page import="com.google.gson.Gson" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Listing</title>
    <script src="listing.js" type="text/javascript"></script>
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp"/>

    <%-- Get current username and his box --%>
    <%
        String currentUser = AccessController.getUsername(request);
        List<BoxDTO> boxList = (List<BoxDTO>)request.getAttribute("boxList");
        Gson gson = new Gson();
        String jsonBoxList = gson.toJson(boxList);
    %>


    <%-- Get listing info from request and show them aside --%>
    <% ListingDTO listing = (ListingDTO) request.getAttribute("listing"); %>
    <div class="left listing-info">
        <h1>
            Winner: <%=listing.getWinner()%>
        </h1>
        <h2>
            Timestamp: <%=listing.getTimestamp()%>
        </h2>
        <h3>
            Username: <%=listing.getUsername()%>
        </h3>
        <h3>
            Listing ID: <%=listing.getID()%>
        </h3>

        <%-- If the current user is not the owner of the listing, he can make an offer --%>
        <% if (!currentUser.equals(listing.getUsername())) { %>
        <%-- Show the user box, so he can select the pokemon to offer --%>
        <button type="button" onclick="showBox('box-popup')"> MAKE AN OFFER </button>
        <% } %>

    </div>

    <%-- If the current user is not the owner of the listing, he can make an offer --%>
    <% if (!currentUser.equals(listing.getUsername())) { %>
        <!-- This popup shows the user box, but initially it is hidden (display=none) -->
        <div class="popup" id="box-popup">
            MY BOX
            <% for(BoxDTO box : (List<BoxDTO>)request.getAttribute("boxList")) { %>
            <div class="popup-box-card" id="<%=box.getBoxID()%>">
                <h1>
                    Pokemon ID: <%=box.getPokemonID()%>
                </h1>
                <h2>
                    Box ID: <%=box.getBoxID()%>
                </h2>
                <h2>
                    Listed: <%=box.isListed()%>
                </h2>
                <form method="post"
                      action="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>&boxID=<%=box.getBoxID()%>">
                    <button type="submit"> Select </button>
                </form>
            </div>
            <% } %>
            <a href="#" onclick="hideBox('box-popup')">Close</a>
        </div>
    <% } %>

    <%-- Get list of offers from request and show them at the center --%>
    <div class="center offers-wrapper">
        <% for(OfferDTO offer : (List<OfferDTO>)request.getAttribute("offerList")){ %>
        <div class="card offer-card" id="<%=offer.getOfferID()%>">
            <h1>
                Offer ID: <%=offer.getOfferID()%>
            </h1>
            <h2>
                Pokemon ID: <%=offer.getPokemonID()%>
            </h2>
            <h3>
                Trader: <%=offer.getTrader()%>
            </h3>
            <h3>
                Timestamp: <%=offer.getTimestamp()%>
            </h3>
            <%-- If the current user is the owner of the listing, he can accept an offer --%>
            <% if (currentUser.equals(listing.getUsername())) { %>
            <%-- TODO ACCETTARE UNA OFFER, EFFETTUARE IL TRADE
                  E NOTIFICARE TUTTI COLORO CHE HANNO PARTECIPATO
                  E VENGO PORTATO AL MIO BOX --%>
            <%-- <button type="button" onclick="acceptOffer()"> TRADE </button> --%>
            <form method="post"
                  action="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>&offerID=<%=offer.getOfferID()%>">
                <button type="submit"> TRADE </button>
            </form>
            <% } %>
        </div>
        <% } %>
    </div>


</div>

</body>
</html>
