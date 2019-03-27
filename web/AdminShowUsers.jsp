<%@ page import="java.util.Vector" %>
<%@ page import="Controller.DBConnect" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Tomer
  Date: 22/03/2019
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles.css">

</head>
<body>
<%
    DBConnect dbconnectoin = DBConnect.getInstance();
    String curr_userName = (String) session.getAttribute("userName");
    if(dbconnectoin.getPrivilege(curr_userName)==null || !dbconnectoin.getPrivilege(curr_userName).equals("admin"))
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

<%!String[][] list;%>
<%!int num;%>

<%
    DBConnect dbConnect = DBConnect.getInstance();
    String curr = (String) session.getAttribute("userName");
    list = dbConnect.getDataAdmin(curr);
    num = list.length;

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
