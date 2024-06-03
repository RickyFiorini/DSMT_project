
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>
<%@ page import="it.unipi.dsmt.app.dtos.UserProfileDTO" %>
<%@ page import="it.unipi.dsmt.app.dtos.BoxDTO" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Profile</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/profile.css?v=1.10" />
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var currentUsername = "<%=AccessController.getUsername(request)%>";
    </script>
    <script src="js/profile.js" defer></script>
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp"/>

    <%-- Get user info from request and show them aside --%>
    <% UserProfileDTO user = (UserProfileDTO) request.getAttribute("userInfo"); %>

    <div id="backgroundBlock"></div>
    <div class="content-wrapper">
        <div class="left profile-info">
            <img src="icons/placeholder_user.png">
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
                    <a id="box-section" href="${pageContext.request.contextPath}/profile?profileSection=box"
                       onclick="closeWebsocket()">
                        <button type="submit" class="profile-button"> Box </button>
                    </a>
                </div>
                <div class="section">
                    <a id="listings-section" href="${pageContext.request.contextPath}/profile?profileSection=listings"
                       onclick="closeWebsocket()">
                        <button type="submit" class="profile-button"> Listings </button>
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

                <%-- If the pokemon is not listed already --%>
                <% if (!box.isListed()) { %>
                <%-- Show the button that allows the creation of a new listing --%>
                <form id="redirectFormListing_<%=box.getBoxID()%>" hidden="hidden" method="post"
                      action="${pageContext.request.contextPath}/profile?profileSection=listings&boxID=<%=box.getBoxID()%>">
                </form>
                <button type="submit" class="profile-button"
                        onclick='handleNewListing("${pageContext.request.contextPath}/profile","<%=box.getBoxID()%>", "<%=box.getUsername()%>")'> New Listing </button>

                <% } %>
            </div>
            <% } %>
            <% } %>

            <%-- If the user selected the listings section, show his listings --%>
            <% if (profileSection.equals("listings")) { %>
            <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("listingList")) { %>
            <div class="card listing-card" id="<%=listing.getID()%>">
                <a href="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>"
                   onclick="closeWebsocket()">
                    <img src="<%=listing.getImageURL()%>" class="img-listing" alt="Image placeholder">
                    <h1>
                        <%=listing.getPokemonName()%>
                    </h1>
                    <h4>
                        <%=listing.getTimestamp()%>
                    </h4>
                </a>
                <button class="profile-button" onclick='handleDeleteListing("<%=listing.getID()%>","${pageContext.request.contextPath}/profile", "<%=listing.getUsername()%>")'>DELETE</button>
            </div>
            <% } %>
            <% } %>
        </div>
    </div>

</div>

</body>
</html>
