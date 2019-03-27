<%@ page import="Controller.DBConnect" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="styles.css">
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
        <img src="./Extra/LOGO.png">
    </div>
    <br>
    <div class="mydh">
        <a href="ManagerDetails.jsp">My Details</a>
        <a href="ManagerMeetings.jsp">My Meetings</a>
        <a href="ManagerCreateMeeting.jsp">Create Meeting</a>
        <a href="ManagerShowUsers.jsp">Active Users</a>
        <a href="ManagerOperations.jsp">Operations</a>

        <form action="OutServlet" method="post">


            <div class="sign_out_btn">


                <div class="managerbtn">
                    <input type="submit" value="Sign out">
                </div>
            </div>

        </form>
    </div>

</div>

<div class="title">Meeting participants:</div>



<form action="ShowUsersInMeetingServlet" method="post">

    <div class="myd">
        <%
            DBConnect dbConnect = DBConnect.getInstance();
            String curr = (String) session.getAttribute("userName");
            int m_key = (Integer) session.getAttribute("m_key");
            String[] participants = null;

            try {
                participants = dbConnect.getParticipants(m_key);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            for(String part: participants){

        %>
        <%=part%> <br>
        <%
            }


        %>
    </div>
</form>


</body>


</html>
