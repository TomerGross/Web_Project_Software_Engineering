<%@ page import="Controller.DBConnect" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <img src="Extra/LOGO.png">
    </div>
    <br>
    <div class="mydh">
        <a href="AdminDetails.jsp">My Details</a>
        <a href="AdminShowUsers.jsp">Active Users</a>
        <a href="AdminOperations.jsp">Operations</a>


        <form action="OutServlet" method="post">

            <div class="sign_out_btn">
                <input type="submit" value="Sign out">
            </div>

        </form>
    </div>

</div>
<div class="title">Meeting participants:</div>

<%
    DBConnect dbconnectoin = DBConnect.getInstance();
    String curr_userName = (String) session.getAttribute("userName");
    if(dbconnectoin.getPrivilege(curr_userName)==null || !dbconnectoin.getPrivilege(curr_userName).equals("admin"))
    {
        response.sendRedirect("Hack.html");
        return;
    }
%>

<form action="ShowUsersInMeetingServlet" method="post">

    <div class="myd fix">
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
