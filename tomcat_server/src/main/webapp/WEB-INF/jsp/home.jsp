<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unipi.dsmt.app.dtos.ListingDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.app.utils.AccessController" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>PokeTrade - Home</title>
</head>

<body>
<div class="site-wrap">
    <jsp:include page="/WEB-INF/jsp/components/nav_bar.jsp">
    <jsp:param name="currentUsername" value="<%=AccessController.getUsername(request)%>" />
    </jsp:include>

    <%-- TODO PER LA SEARCH-BAR, AGGIUNGERE LE LISTINGS COME DATALIST
          E PASSARLE ALLA SEARCH BAR (REFERENCE home_old.jsp) --%>
    <div class="top-bar-home">
        <form action="search" method="get" class="site-block-top-search">
            <input name="keyword" type="text" class="form-listing" placeholder="Search">
        </form>
        <%-- Toggle past listings --%>
        <label class="switch">
            <input type="checkbox">
            <span class="switch-past-listings"></span>
        </label>
        <div class="dropdown-preference">
            <button type="button" class="dropdown"
                    id="dropdownMenuTypes" data-toggle="dropdown">Types
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuTypes">
                <a class="dropdown-item" href="#">Grass</a>
                <a class="dropdown-item" href="#">Fire</a>
                <a class="dropdown-item" href="#">Water</a>
            </div>

        </div>
    </div>
    <div class="listings-wrapper">
        <% for(ListingDTO listing : (List<ListingDTO>)request.getAttribute("openListingList")){ %>
        <div class="card listing-card" id="<%=listing.getListingID()%>">
            <a href="${pageContext.request.contextPath}/listing?listingID=<%=listing.getListingID()%>>">
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
            </a>
        </div>
        <% } %>
    </div>

</div>

</body>
</html>