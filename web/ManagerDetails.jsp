<%@ page import="Controller.DBConnect" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">


    <meta charset="utf-8">
    <title></title>

</head>


<body>

<div class="sidebar">
    <div class="logo">
        <img src="Extra/LOGO.png">
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
