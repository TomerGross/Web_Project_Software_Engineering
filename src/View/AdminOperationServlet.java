package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    public class AdminOperationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sessionUserName = (String) request.getSession().getAttribute("userName");
            DBConnect dbConnect = DBConnect.getInstance();


            if(!dbConnect.getPrivilege(sessionUserName).equals("admin")){
                //security, check if he is legit admin or just played with the pages
                response.sendRedirect("LoginPage.html");
                return;
            }
            String userName = request.getParameter("userName");
            if(!dbConnect.userInDb(userName)) {
                response.sendRedirect("AdminOperationsUserNotFound.html");
                return;
            }


            String op = request.getParameter("operation");
            String priv = dbConnect.getPrivilege(userName);



            if(userName.equals(sessionUserName)) {
                //block the option to use self operations
                response.sendRedirect("AdminOperationsSelfError.html");
                return;
            }

            if(priv.equals("admin")){
                //admin is untouchable
                response.sendRedirect("AdminOperationsUnauthorized.html");
                return;
            }


            switch (op) {
                case "1":
                    //fire user
                    dbConnect.removeUser(userName);
                    response.sendRedirect("AdminOperationsSuccess.html");
                    return;

                case "2":
                    //promote user
                    if(priv.equals("candidate")) {
                        dbConnect.setPrivilege(userName, "worker");
                        response.sendRedirect("AdminOperationsSuccess.html");
                        return;
                    }
                    else if(priv.equals("worker")) {
                        dbConnect.setPrivilege(userName, "manager");
                        response.sendRedirect("AdminOperationsSuccess.html");
                        return;
                    }
                    break;
                case "3":
                    //demote user
                    if(priv.equals("candidate") || priv.equals("worker")) {
                        dbConnect.removeUser(userName);
                        response.sendRedirect("AdminOperationsSuccess.html");
                        return;
                    }
                    else if(priv.equals("manager")) {
                        dbConnect.setPrivilege(userName, "worker");
                        response.sendRedirect("AdminOperationsSuccess.html");
                        return;
                    }
                    else {
                        dbConnect.setPrivilege(userName, "manager");
                        response.sendRedirect("AdminOperationsSuccess.html");
                        return;
                    }

                case "4":
                    //show user details
                    if(priv!=null){
                            request.getSession().setAttribute("details",userName);
                            response.sendRedirect("AdminUserDetails.jsp");
                            return;
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception e){}
        response.sendRedirect("AdminOperations.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
