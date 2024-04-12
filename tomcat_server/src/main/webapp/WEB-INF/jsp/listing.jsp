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
    <script src="js/listing.js" type="text/javascript"></script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/listing.css?v=1.10" />
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

    <div id="backgroundBlock"></div>
    <div class="content-wrapper">

        <div class="left listing-info">
            <img src="<%=listing.getImageURL()%>">
            <div class="user-info">

                <div class="info">
                    <h1>Username:</h1>
                    <label>
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
            </div>



            <%-- If the current user is not the owner of the listing, he can make an offer --%>
            <% if (!currentUser.equals(listing.getUsername())) { %>
            <%-- Show the user box, so he can select the pokemon to offer --%>
            <button type="button" onclick="showBox('box-popup')"> MAKE AN OFFER </button>
            <% } %>

        </div>


        <%-- Get list of offers from request and show them at the center --%>
        <div class="center section-wrapper">
            <% for(OfferDTO offer : (List<OfferDTO>)request.getAttribute("offerList")){ %>
            <div class="card offer-card" id="<%=offer.getOfferID()%>">
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


    <%-- If the current user is not the owner of the listing, he can make an offer --%>
        <% if (!currentUser.equals(listing.getUsername())) { %>
            <!-- This popup shows the user box, but initially it is hidden (display=none) -->
            <div class="popup" id="box-popup">
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
                    <h3>
                        Listed: <%=box.isListed()%>
                    </h3>

                    <form method="post"
                          action="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>&boxID=<%=box.getBoxID()%>">
                        <button type="submit"> Select </button>
                    </form>
                </div>
                <% } %>
                <a href="#" onclick="hideBox('box-popup')">Close</a>
            </div>
        <% } %>

    </div>


</div>

</body>
</html>
