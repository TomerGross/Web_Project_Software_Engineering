<%@ page import="Controller.DBConnect" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles.css">
</head>
<body>


<div class="sidebar">
    <div class="logo">
        <img src="../Extra/LOGO.png">
    </div>
    <br>
    <div class="mydh">
        <a href="AdminDetails.jsp">My Details</a>
        <a href="AdminShowUsers.jsp">Active Users</a>
        <a href="AdminOperations.html">Operations</a>


        <form action="OutServlet" method="post">

            <div class="sign_out_btn">
                <input type="submit" value="Sign out">
            </div>

        </form>
    </div>


</div>



<div class="title">
    Your Details Are:
</div>



<div class="myd">

    <%
        DBConnect dbconnectoin = DBConnect.getInstance();
        String curr_userName = (String) session.getAttribute("userName");
        String[] details = dbconnectoin.getDetails(curr_userName);
    %> <br> User Name: <%=details[0]%> <br><br>
    First Name: <%=details[1]%> <br><br>
    Last Name: <%=details[2]%> <br><br>
    ID: <%=details[3]%> <br><br>
    Email: <%=details[4]%> <br><br>
    Password: <%=details[5]%><br><br>
    Privilege: <%=details[6]%>


</div>


</body>
</html>
