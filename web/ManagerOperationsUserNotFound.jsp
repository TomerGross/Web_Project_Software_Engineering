<%@ page import="Controller.DBConnect" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <link rel="stylesheet" href="styles2.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

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
        <img src="./Extra/LOGO.png">
    </div>
    <br>
    <div class="mydh">
        <a href="ManagerDetails.jsp">My Details</a>
        <a href="ManagerMeetings.jsp">My Meetings</a>
        <a href="ManagerCreateMeeting.jsp">Create Meeting</a>
        <a href="ManagerShowUsers.jsp">Active Users</a>
        <a href="ManagerOperations.jsp">Operations</a>



        <form action="OutServlet" method="post">


            <div class="sign_out_btn">


                <div class="managerbtn">
                    <input type="submit" value="Sign out">
                </div>
            </div>

        </form>
    </div>

</div>

<form class="operations" action="ManagerOperationServlet" method="post">


    <input type="text" placeholder="User Name" name="userName">
    <button type="submit"><i class="fa fa-user"></i></button>

    <div class="myd_msg error" >
        user not found!
    </div> <br>

    <div class="radio-group fix">
        <input type="radio" id="option-one" name="operation" value="1">
        <label for="option-one">Fire</label>
        <input type="radio" id="option-two" name="operation" value="2">
        <label for="option-two">Promote</label>
        <input type="radio" id="option-four" name="operation" value="3">
        <label for="option-four">User info</label>
    </div>



</form>

</body>
</html>
