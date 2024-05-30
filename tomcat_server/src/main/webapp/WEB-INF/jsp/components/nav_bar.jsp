<link rel="stylesheet" href="css/nav_bar.css?v=1.9">
<script src="js/navbar.js?v=1.34" defer></script>

<%--<script>--%>
<%--  var currentUsername = '<%=request.getParameter("currentUsername")%>';--%>
<%--</script>--%>



<nav class="navbar">
  <a href="${pageContext.request.contextPath}/home">Home</a>
  <a href="${pageContext.request.contextPath}/profile?profileSection=box">Profile</a>
  <a href = "#" onclick='handleLogout(event, "${pageContext.request.contextPath}/logout")'>Logout</a>
</nav>
