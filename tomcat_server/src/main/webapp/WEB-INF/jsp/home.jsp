<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/home.css" />
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var currentUsername = "<%=AccessController.getUsername(request)%>";
    </script>
    <script src="js/home.js" defer> </script>
    <script src="js/searchbar.js" defer> </script>
    <title>PokeTrade - Home</title>
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp">
        <jsp:param name="currentUsername" value="<%=AccessController.getUsername(request)%>" />
    </jsp:include>

    <div class="search">
        <input type="text" class="searchTerm" id="searchbar" placeholder="What are you looking for?">
        <button type="submit" class="searchButton">
            <img src="icons/search.png" alt="search icon" onclick="searchListing()">
        </button>
        <!--form action="search" method="get" class="site-block-top-search">
            <input name="keyword" type="text" class="form-listing" placeholder="Search">
        </form-->
        <%-- Toggle past listings --%>
        <input type="checkbox" id="toggle-past-listings">
        <label for="toggle-past-listings">Past Listings</label>


    </div>
    <div class="center-board">
        <div class="listings-wrapper">
            <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("listingList")){ %>
            <% String listingStatus = "listing-card"; %>
            <% if (listing.getWinner() != 0) {
                listingStatus += "-past";
            } %>
            <div class="card <%=listingStatus%>" id="<%=listing.getID()%>"
                    <% if (listing.getWinner() != 0) { %>
                 style="display: none;"
                    <% } %>
            >
                <a onclick="closeWebsocket()" href="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>">
                    <img src="<%=listing.getImageURL()%>" class="img-box" alt="icons/placeholder_pokemon.png">
                    <h1>
                        <%=listing.getPokemonName()%>
                    </h1>
                    <h3>
                        <%=listing.getUsername()%>
                    </h3>
                    <h3>
                        Winner: None
                    </h3>
                    <h4>
                        <%=listing.getTimestamp()%>
                    </h4>
                </a>
            </div>
            <% } %>
        </div>
    </div>

</div>

</body>
</html>