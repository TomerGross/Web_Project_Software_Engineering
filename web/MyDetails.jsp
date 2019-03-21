<%@ page import="Controller.DBConnect" %><%--
  Created by IntelliJ IDEA.
  User: Tomer
  Date: 21/03/2019
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Details</title>
</head>
<body>


<%
    DBConnect dbconnectoin = DBConnect.getInstance();
    String curr_userName = (String) session.getAttribute("userName");
    String[] details = dbconnectoin.getDetails(curr_userName);
%>  User Name: <%=details[0]%> <br>
    First Name: <%=details[1]%> <br>
    Last Name: <%=details[2]%> <br>
    ID: <%=details[3]%> <br>
    Email: <%=details[4]%> <br>
    Password: <%=details[5]%> <br>


</body>
</html>
