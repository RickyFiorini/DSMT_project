<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>
<%@ page import="it.unipi.dsmt.app.dtos.OfferDTO" %>
<%@ page import="it.unipi.dsmt.app.dtos.BoxDTO" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Objects" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Listing</title>
    <script src="js/listing.js" type="text/javascript"></script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/listing.css?v=1.10" />
    <script>
        var currentUsername = "<%=AccessController.getUsername(request)%>";
    </script>
    <script src="js/offer.js" defer> </script>
    <script src="js/trade.js" defer> </script>

</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp">
        <jsp:param name="currentUsername" value="<%=AccessController.getUsername(request)%>" />
    </jsp:include>
<%-- Get current username and his box --%>
    <%
        String currentUser = AccessController.getUsername(request);
        List<BoxDTO> boxList = (List<BoxDTO>)request.getAttribute("boxList");
        Gson gson = new Gson();
        String jsonBoxList = gson.toJson(boxList);
    %>

<%-- Get listing info from request and show them aside --%>
    <% ListingDTO listing = (ListingDTO) request.getAttribute("listing"); %>
    <script type="text/javascript">
        var listingID = <%= listing.getID() %>;
        console.log(listingID);
    </script>
    <div id="backgroundBlock"></div>
    <div class="content-wrapper">
        <div class="left listing-info">
            <img src="<%=listing.getImageURL()%>">
            <div class="user-info">

                <div class="info">
                    <h1>Username:</h1>
                    <label id="ListingOwner">
                        <%=listing.getUsername()%>
                    </label>
                </div>
                <div class="info">
                    <h1>Pokemon:</h1>
                    <label>
                        <%=listing.getPokemonName()%>
                    </label>
                </div>
                <div class="info">
                    <h1>Type:</h1>
                    <label>
                        <%=listing.getPrimaryType()%>
                    </label>
                </div>
                <div class="info">
                    <h1>Stats:</h1>
                    <label>
                        Atk: <%=listing.getAttack()%> Def: <%=listing.getDefense()%>
                    </label>
                </div>
                <div class="info">
                    <h2>Winner:</h2>
                    <label id="winner">
                        <% if (listing.getWinner() == null) { %>
                        None
                        <% } %>
                    </label>
                </div>
            </div>
            <%-- If the current user is not the owner of the listing, he can make an offer --%>
            <% if (!currentUser.equals(listing.getUsername()) && (listing.getWinner() == null)) { %>
            <%-- Show the user box, so he can select the pokemon to offer --%>
            <button class="listing-button" type="button" onclick="showBox('box-popup')"> MAKE AN OFFER </button>
            <% } %>
        </div>

        <%-- Get list of offers from request and show them at the center --%>
        <div class="center section-wrapper">
            <% for(OfferDTO offer : (List<OfferDTO>)request.getAttribute("offerList")){ %>
            <div class="card offer-card" id="Offer_<%=offer.getOfferID()%>">
                <img src="<%=offer.getImageURL()%>" class="img-box" alt="Image placeholder">
                <h1>
                    <%-- Pokemon Name --%>
                    <%=offer.getPokemonName()%>
                </h1>
                <h2>
                    <%-- Pokemon type --%>
                    <%=offer.getPrimaryType()%>
                </h2>
                <h3>
                    Trader: <%=offer.getTrader()%>
                </h3>
                <h4>
                    Atk: <%=offer.getAttack()%> Def: <%=offer.getDefense()%>
                </h4>

                <% if (currentUser.equals(offer.getTrader())) { %>
                   <button class="listing-button" id="deleteButton_<%=offer.getOfferID()%>" onclick="Delete('<%= offer.getOfferID() %>','<%= offer.getBoxID() %>')">Delete</button>

                <% } %>
                <%-- If the current user is the owner of the listing, he can accept an offer --%>
                <% if (currentUser.equals(listing.getUsername()) && (listing.getWinner() == null)) { %>

                <button class="listing-button" id="tradeButton_<%=offer.getOfferID()%>" onclick="Trade('<%= offer.getOfferID() %>','<%= offer.getBoxID() %>','<%= offer.getTrader() %>')">Trade</button>
                <% } %>
                <% if (Objects.equals(listing.getWinner(), offer.getTrader())) { %>
                    <div>WINNER!</div>
                <% } %>
            </div>
            <% } %>
        </div>


    <%-- If the current user is not the owner of the listing, he can make an offer --%>
        <% if (!currentUser.equals(listing.getUsername())) { %>
            <!-- This popup shows the user box, but initially it is hidden (display=none) -->
            <div class="popup" id="box-popup">
                <button id="closePopup" class="listing-button" onclick="hideBox('box-popup')"> Close </button>
                <% for(BoxDTO box : (List<BoxDTO>)request.getAttribute("boxList")) { %>
                <div class="card popup-box-card" id="<%=box.getBoxID()%>">
                    <img src="<%=box.getImageURL()%>" class="img-box" alt="Image placeholder"
                         onclick="showPokemonDetails('<%=box.getBoxID()%>')">
                    <h1>
                        <%-- Pokemon Name --%>
                        <%=box.getPokemonName()%>
                    </h1>
                    <h2>
                        <%-- Pokemon type --%>
                        <%=box.getPrimaryType()%>
                    </h2>
                    <h4>
                        Atk: <%=box.getAttack()%> Def: <%=box.getDefense()%>
                    </h4>
                    <h3 id="listedStatus<%=box.getBoxID()%>">
                        Listed: <%=box.isListed()%>
                    </h3>

                    <% if (!box.isListed()) { %>
                    <button class="listing-button" id="selectButton_<%=box.getBoxID()%>" type="submit" onclick="handleSend('<%=box.getBoxID()%>')"> Select </button>
                    <% } %>
                </div>
                <% } %>
            </div>
        <% } %>

    </div>


</div>

</body>
</html>
