<%@ page import="Controller.DBConnect" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<%
    DBConnect dbconnectoin = DBConnect.getInstance();
    String curr_username = (String) session.getAttribute("userName");
    if(dbconnectoin.getPrivilege(curr_username)==null || !dbconnectoin.getPrivilege(curr_username).equals("manager"))
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

<%
    String curr_userName = (String) session.getAttribute("details");
%>

<div class="title">
    <%=curr_userName%>'s Details:
</div>



<div class="myd">

    <%

        String[] details = dbconnectoin.getDetails(curr_userName);
    %> <br> User Name: <%=details[0]%> <br><br>
    First Name: <%=details[1]%> <br><br>
    Last Name: <%=details[2]%> <br><br>
    ID: <%=details[3]%> <br><br>
    Email: <%=details[4]%> <br><br>
    Privilege: <%=details[6]%>


</div>


</body>
</html>
