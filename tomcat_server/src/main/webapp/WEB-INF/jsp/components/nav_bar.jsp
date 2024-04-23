<link rel="stylesheet" href="css/nav_bar.css?v=1.9">
<script src="js/navbar.js?v=1.34" defer></script>

<nav class="navbar">
  <a href="${pageContext.request.contextPath}/home" onclick="closeWebsocket()">Home</a>
  <a href="${pageContext.request.contextPath}/profile?profileSection=box" onclick="closeWebsocket()">Profile</a>
  <a href = "#" onclick='handleLogout(event, "${pageContext.request.contextPath}/logout"), closeWebsocket()'>Logout</a>
</nav>
