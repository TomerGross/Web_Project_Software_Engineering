<%@ page import="Controller.DBConnect" %><%--
  Created by IntelliJ IDEA.
  User: Amit
  Date: 21-Mar-19
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="sun.misc.Queue" %>
<%@ page import="java.sql.*" %>


    <%! DBConnect dbConnect = DBConnect.getInstance();%>
    <%! String userName;%>
    <%  userName= (String) session.getAttribute("userName");%>
    <%! Connection con = dbConnect.getConnection();%>
    <%! PreparedStatement query = con.prepareStatement("SELECT * FROM users WHERE user_name != ?");%>
    <% try {
        query.setString(1, userName);
    } catch (SQLException e) {
        e.printStackTrace();
    }
        int count=0;
    Queue<String> list = new Queue<>();%>
    <%!
        ResultSet rs = query.executeQuery();%>
    <% while(rs.next()){
        count++;
        list.enqueue(rs.getString("first_name"));
        list.enqueue(rs.getString("last_name"));
    }
    %>
<form action="SetMeetingServlet">
    <select name="users" size="<%=count%>" multiple>
        <% for(int i=0;i<count;i++){%>
            <option value="<%=i%>"> <%=list.dequeue()+" "+list.dequeue() %></option>
<%}%>
    </select>
    <br><br>
    <input type="submit">
</form>

</body>
</html>
