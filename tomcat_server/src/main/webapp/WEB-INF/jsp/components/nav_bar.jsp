<link rel="stylesheet" href="css/nav_bar.css?v=1.9">
<script src="js/navbar.js?v=1.34" defer></script>

<script>
  var currentUsername = '<%=request.getParameter("currentUsername")%>';
</script>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/home">Home</a>

  <div class="notification">
    <a href="${pageContext.request.contextPath}/notification"><img src="icons/bell.png" alt="" width="30px"
        height="30px"></a>
    <label>0</label>
  </div>

  <!-- TODO IMPLEMENTARE DROPDOWN MENU CON PROFILE E LOGOUT -->
  <div class="profile-dropdown">
    <button class="drop-profile">Profile</button>
    <div class="drop-profile-content">
      <a href="${pageContext.request.contextPath}/profile?profileSection=box"> Profile </a>
      <a onclick='handleLogout(event, "${pageContext.request.contextPath}/logout")'> Logout </a>
    </div>
  </div>
</nav>