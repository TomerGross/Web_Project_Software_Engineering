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
    if(dbconnectoin.getPrivilege(curr_userName)==null || !dbconnectoin.getPrivilege(curr_userName).equals("worker"))
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
        <a href="WorkerDetails.jsp">My Details</a>
        <a href="WorkerMeetings.jsp">My Meetings</a>
        <a href="WorkerCreateMeeting.jsp">Create Meeting</a>


        <form action="OutServlet" method="post">

            <div class="sign_out_btn">
                <input type="submit" value="Sign out">
            </div>

        </form>
    </div>




</div>
<div class="title">Your Meetings</div>


<br>

<form action="ShowUsersInMeetingServlet" method="post">

    <div class="myd fix">
        <%
        DBConnect dbConnect = DBConnect.getInstance();
        String curr = (String) session.getAttribute("userName");
        int[] m_keys = null;
        try {
        m_keys = dbConnect.getAllMeetings(curr);
        } catch (SQLException e) {
        e.printStackTrace();
        }

        String temp = "";


        int len;
        if(m_keys==null)
        len=0;
        else
        len=m_keys.length;

        for (int i=0; i< len; i++){

        try {
        temp = dbConnect.getDateOfMeetingInString(m_keys[i]) ;
        } catch (SQLException e) {
        e.printStackTrace();
        }
        %>
        meeting sn: <%=m_keys[i]%>, date: <%=temp%> <button  type="submit" name ="m_key" value=<%=m_keys[i]%>>details...</button>
        <button  type="submit" name ="cancel_mkey" value=<%=m_keys[i]%>>cancel</button><br><br>

        <%


        }


        %>
    </div>
</form>


</body>


</html>
