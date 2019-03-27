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
