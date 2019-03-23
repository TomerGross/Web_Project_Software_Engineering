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
        <a href="WorkerDetails.jsp">My Details</a>
        <a href="WorkerMeetings.html">My Meetings</a>
        <a href="WorkerCreateMeeting.jsp">Create Meeting</a>

    </div>


    <form action="OutServlet" method="post">

        <div class="sign_out_btn">
            <input type="submit" value="Sign out">
        </div>

    </form>

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
    Password: <%=details[5]%>


</div>


</body>
</html>
