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

        <%-- Toggle past listings --%>
        <input type="checkbox" id="toggle-past-listings">
        <label for="toggle-past-listings">Past Listings</label>

    </div>
    <div class="center-board">
        <div class="listings-wrapper">
            <%-- Check past listings --%>
            <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("listingList")){ %>
            <% String listingStatus = "listing-card"; %>
            <% if (listing.getWinner() != null) {
                listingStatus += "-past";
            } %>

            <%-- Check past listings --%>
            <div class="card <%=listingStatus%>" id="<%=listing.getID()%>"
                    <% if (listing.getWinner() != null) { %>
                 style="display: none;"
                    <% } %>
            >
                <%-- Past listing are not interactable --%>
                <a
                    <% if (listing.getWinner() == null) { %>
                        onclick="closeWebsocket()"
                        href="${pageContext.request.contextPath}/listing?listingID=<%=listing.getID()%>"
                    <% } %>
                >
                    <img src="<%=listing.getImageURL()%>" class="img-box" alt="icons/placeholder_pokemon.png">
                    <h1>
                        <%=listing.getPokemonName()%>
                    </h1>
                    <h2>
                        <%=listing.getUsername()%>
                    </h2>
                    <h3>
                        Winner: <% if (listing.getWinner() != null) { %>
                                    <%=listing.getWinner()%>
                                <% } else { %>
                                    None
                                <% } %>
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