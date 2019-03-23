<%@ page import="Controller.DBConnect" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles.css">

</head>
<body>

<div class="sidebar">
    <div class="logo">
        <img src="../Extra/LOGO.png">
    </div>
    <br>
    <div class="mydh">
        <a href="ManagerDetails.jsp">My Details</a>
        <a href="ManagerMeetings.html">My Meetings</a>
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

<%!String[][] list;%>
<%!int num;%>

<%
    DBConnect dbConnect = DBConnect.getInstance();
    list = dbConnect.getData();
    try {
        num = dbConnect.getNumOfUsers();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>






<table class="usertable">
    <tr>
        <th>User Name</th>
        <th>Password</th>
        <th>Privilege</th>

    </tr>
    <%
        for (int i=0; i< num; i++ ){

    %>
    <tr>
        <td><%=list[i][0]%></td>
        <td><%=list[i][1]%></td>
        <td><%=list[i][2]%></td>
    </tr>

    <%
        }
    %>
</table>





</body>
</html>
