<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>
<%@ page import="it.unipi.dsmt.app.dtos.OfferDTO" %>
<%@ page import="it.unipi.dsmt.app.dtos.UserProfileDTO" %>
<%@ page import="it.unipi.dsmt.app.dtos.BoxDTO" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Profile</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/profile.css?v=1.10" />
    <!--script src="js/searchbar.js?v=1.9" defer> </script-->
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp"/>

    <%-- Get current username --%>
    <% String currentUser = AccessController.getUsername(request); %>

    <%-- Get user info from request and show them aside --%>
    <% UserProfileDTO user = (UserProfileDTO) request.getAttribute("userInfo"); %>

    <div id="backgroundBlock"></div>
    <div class="content-wrapper">
        <div class="left profile-info">
            <div class="user-info">
                <div class="info">
                    <img src="icons/placeholder_user.png">
                    <h1>Username</h1>
                    <label>
                        <%= user.getUsername() %>
                    </label>
                </div>
                <div class="info">
                    <h1>Name</h1>
                    <label>
                        <%= user.getName() %>
                    </label>
                </div>
                <div class="info">
                    <h1>Surname</h1>
                    <label>
                        <%= user.getSurname() %>
                    </label>
                </div>
            </div>
            <div class="user-sections">
                <div class="section">
                    <a id="box-section"
                       href="${pageContext.request.contextPath}/profile?profileSection=box">
                        <button type="submit"> Box </button>
                    </a>
                </div>
                <div class="section">
                    <a id="listings-section"
                       href="${pageContext.request.contextPath}/profile?profileSection=listings">
                        <button type="submit"> Listings </button>
                    </a>
                </div>
            </div>
        </div>

        <%-- Check the selected profile section --%>
        <% String profileSection = request.getParameter("profileSection"); %>

        <%-- Get the box from request and show it at the center --%>
        <div class="center section-wrapper">
            <%-- If the user selected the box section, show his box --%>
            <% if (profileSection.equals("box")) { %>
            <% for(BoxDTO box : (List<BoxDTO>)request.getAttribute("boxList")) { %>
            <div class="card box-card" id="<%=box.getBoxID()%>">
                <%-- TODO IMPLEMENTARE showPokemonDetails(boxID) CON JS
                      E DETTAGLI DEL POKEMON IN BoxDTO --%>
                <img src="<%--=box.getImageUrl()--%>" class="img-box" alt="Image placeholder"
                     onclick="showPokemonDetails('<%=box.getBoxID()%>')">
                <h1>
                    Pokemon Name: <%--=box.getPokemonName()--%>
                </h1>
                <h2>
                    Pokemon Type: <%--=box.getPokemonType()--%>
                </h2>
                <h3>
                    Box ID: <%=box.getBoxID()%>
                </h3>
                <h3>
                    Pokemon ID: <%=box.getPokemonID()%>
                </h3>

                <!-- TODO IMPLEMENTARE FUNZIONE PER LA CREAZIONE DI UNA NUOVA LISTING -->
                <form method="post"
                      action="${pageContext.request.contextPath}/profile?profileSection=listings&boxID=<%=box.getBoxID()%>">
                    <button type="submit"> CREATE LISTING </button>
                </form>
                <%-- If the pokemon is not listed already --%>
                <% if (!box.isListed()) { %>
                <%-- Show the button that allows the creation of a new listing --%>
                <button type="button" onclick="createListing()"> CREATE LISTING </button>
                <% } %>
            </div>
            <% } %>
            <% } %>

            <%-- If the user selected the listings section, show his listings --%>
            <% if (profileSection.equals("listings")) { %>
            <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("listingList")) { %>
            <div class="card listing-card" id="<%=listing.getID()%>">
                <a href="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>">
                    <img src="<--%=listing.getImageURL()--%>" class="img-listing" alt="Image placeholder">
                    <h1>
                        Pokemon ID: <%=listing.getPokemonID()%>
                    </h1>
                    <h2>
                        Listing ID: <%=listing.getID()%>
                    </h2>
                    <h3>
                        Timestamp: <%=listing.getTimestamp()%>
                    </h3>
                </a>
                <button onclick='deleteListing("<%=listing.getID()%>","${pageContext.request.contextPath}/listing?listingID=" + <%=listing.getID()%>, "<%=user.getUsername()%>")'>DELETE</button>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>

</div>

</body>
</html>
