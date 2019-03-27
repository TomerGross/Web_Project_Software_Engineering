<%@ page import="Controller.DBConnect" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="styles.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">


  <meta charset="utf-8">
  <title></title>

</head>


<body>
<%
  DBConnect dbconnectoin = DBConnect.getInstance();
  String curr_userName = (String) session.getAttribute("userName");
  if(dbconnectoin.getPrivilege(curr_userName)==null || !dbconnectoin.getPrivilege(curr_userName).equals("manager"))
  {
    response.sendRedirect("Hack.html");
    return;
  }
%>
<div class="sidebar">
  <div class="logo">
    <img src="Extra/LOGO.png">
  </div>
  <br>
  <div class="mydh">
    <a href="ManagerDetails.jsp">My Details</a>
    <a href="ManagerMeetings.jsp">My Meetings</a>
    <a href="ManagerCreateMeeting.jsp">Create Meeting</a>
    <a href="ManagerShowUsers.jsp">Active Users</a>
    <a href="ManagerOperations.jsp">Operations</a>



    <form action="OutServlet" method="post">

      <div class="sign_out_btn_2">
        <input type="submit" value="Sign out">
      </div>

    </form>
  </div>




</div>


<div class="title">
  Welcome Manager
</div>




</body>

</html>
