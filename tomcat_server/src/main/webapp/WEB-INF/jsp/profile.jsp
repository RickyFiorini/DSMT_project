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
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp"/>

    <%-- Get current username --%>
    <% String currentUser = AccessController.getUsername(request); %>

    <%-- Get user info from request and show them aside --%>
    <% UserProfileDTO user = (UserProfileDTO) request.getAttribute("userInfo"); %>
    <div class="left profile-info">
        <div class="user-info">
            <div class="info">
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
                <form id="box-section"
                      action="${pageContext.request.contextPath}/profile" method="get">
                    <input type="hidden" name="profileSection" value="box">
                    <button type="submit"> Box </button>
                </form>
            </div>
            <div class="section">
                <form id="listings-section"
                      action="${pageContext.request.contextPath}/profile" method="get">
                    <input type="hidden" name="profileSection" value="Listings">
                    <button type="submit"> Listings </button>
                </form>
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
                <%-- TODO IMPLEMENTARE showPokemonDetails(boxID) CON JS --%>
                <img src="<%=box.getImageURL()%>" class="img-box" alt="Image placeholder"
                     onclick="showPokemonDetails('<%=box.getBoxID()%>')">
                <h1>
                    <%=box.getPokemonName()%>
                </h1>
                <h2>
                    <%=box.getPrimaryType()%>
                </h2>
                    <h2>
                        <%=box.getSecondaryType()%>
                    </h2>
            </div>
            <% } %>
        <% } %>

        <%-- If the user selected the listings section, show his listings --%>
        <% if (profileSection.equals("Listings")) { %>
            <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("listingList")) { %>
            <div class="card listing-card" id="<%=listing.getID()%>">
                    <img src="<%=listing.getImageURL()%>" class="img-listing" alt="Image placeholder">
                    <h1>
                        <%=listing.getPokemonName()%>
                    </h1>
                    <h2>
                        <%=listing.getPrimaryType()%>
                    </h2>
                    <h2>
                        <%=listing.getSecondaryType()%>
                    </h2>
                    <h2>
                        <img src="<%= listing.getImageURL() %>">
                    </h2>
                    <h2>
                        <%=listing.getAttack()%>
                    </h2>
                    <h2>
                        <%=listing.getDefense()%>
                    </h2>
                    <h3>
                        <%=listing.getUsername()%>
                    </h3>
                </a>
                <button onclick='deleteListing("<%=listing.getID()%>","${pageContext.request.contextPath}/listing", "<%=user.getUsername()%>")'>DELETE</button>
            </div>
            <% } %>
        <% } %>
    </div>

    <!-- Show the details of the selected pokemon on the right side -->
    <!-- TODO IMPLEMENTARE SECTION CON I DETAILS DEL POKEMON:
          QUANDO FACCIO CLICK SUL POKEMON, MOSTRO A DESTRA I DETAILS E IL BUTTON "NEW LISTING" -->
    <div class="right pokemon-wrapper" id="">
        <img src="" id="pokemon-details-img" class="img-box-big" alt="Image placeholder">
        <h1 id="pokemon-details-name"></h1>
        <h2 id="pokemon-details-type"></h2>

        <!-- TODO GESTIONE NEW LISTING (E SE IL POKEMON HA LISTED=TRUE, DEVO MOSTRARE IL BUTTON)
                SETTARE DINAMICAMENTE L'ID DEL BUTTON CON IL BOXID -->
        <form id="new-listing" method="post"
              action="${pageContext.request.contextPath}/profile?profileSection=listing">
            <button id="new-listing-button" type="submit" name="boxID" value="" hidden="hidden"> New Listing </button>
        </form>
    </div>

</div>

</body>
</html>
