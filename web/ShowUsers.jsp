<%@ page import="java.util.Vector" %>
<%@ page import="Controller.DBConnect" %><%--
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
</head>
<body>


<%!Vector<Vector<String>> list;%>
<% list= new Vector<>();%>
<%
    DBConnect dbConnect = DBConnect.getInstance();
    list = dbConnect.getData();
%>


<table border="2">
    <tr>
        <td>User Name</td>
        <td>Password</td>
        <td>Privilege</td>

    </tr>
    <%
        for (Vector<String> user: list)
        {
            %>
            <tr><td><%=user.get(0)%></td></tr>
            <tr><td><%=user.get(1)%></td></tr>
            <tr><td><%=user.get(2)%></td></tr>

            <%
        }
    %>
</table>


</body>
</html>
