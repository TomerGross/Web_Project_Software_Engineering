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
        <a href="WorkerDetails.jsp">My Details</a>
        <a href="WorkerMeetings.html">My Meetings</a>
        <a href="WorkerCreateMeeting.jsp">Create Meeting</a>

    </div>


    <form action="OutServlet" method="post">

        <div class="sign_out_btn">
            <input type="submit" value="Sign out">
        </div>

    </form>

</div>


<form action="MeetingServlet" method="post">


    <div class="myd">

        <%
            DBConnect dbconnectoin = DBConnect.getInstance();
            String curr_userName = (String) session.getAttribute("userName");
            String[] users = null;
            try {
                users = dbconnectoin.getUsersForMeetingWorker(curr_userName);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (users!= null){
                for (String user: users){ %>


                    <input type="checkbox" name="checked" value=<%=user%>><%=user%><br>

        <%      }
            }
        %>      <input type="submit" value="Create">

    </div>



</form>

</body>
</html>
