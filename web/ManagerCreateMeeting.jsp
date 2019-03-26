<%@ page import="Controller.DBConnect" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

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
        <a href="ManagerOperations.html">Operations</a>



        <form action="OutServlet" method="post">

            <div class="sign_out_btn_2">
                <input type="submit" value="Sign out">
            </div>

        </form>
    </div>

</div>


<form action="MeetingServlet" method="post">


    <div class="myd">

        <%
            DBConnect dbconnection = DBConnect.getInstance();
            String curr_userName = (String) session.getAttribute("userName");
            String[] users = null;
            try {
                users = dbconnection.getUsersForMeetingManager(curr_userName);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (users!= null){
                for (String user: users){ %>


                    <label><input type="checkbox" name="checked" value=<%=user%>><%=user%></label>
        <%      }
        }
        %>      <br><input type="submit" value="Create">

    </div>



</form>

</body>
</html>
