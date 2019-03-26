package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManagerOperationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sessionUserName = (String) request.getSession().getAttribute("userName");
            DBConnect dbConnect = DBConnect.getInstance();
            if(!dbConnect.getPrivilege(sessionUserName).equals("manager")){
                response.sendRedirect("LoginPage.html");
                return;
            }
            String userName = request.getParameter("userName");
            String op = request.getParameter("operation");
            String priv = dbConnect.getPrivilege(userName);


            if(!dbConnect.userInDb(userName)) {
                response.sendRedirect("ManagerOperationsUserNotFound.html");
                return;
            }

            if(userName.equals(sessionUserName)) {
                response.sendRedirect("ManagerOperationsSelfError.html");
                return;
            }


            if(priv.equals("admin")){
                response.sendRedirect("ManagerOperationsUnauthorized.html");
                return;
            }


            switch (op) {
                case "1":
                    if(priv.equals("worker") || priv.equals("candidate")) {
                        dbConnect.removeUser(userName);
                        response.sendRedirect("ManagerOperationsSuccess.html");
                        return;
                    }

                case "2":
                    if(priv.equals("candidate")) {
                        dbConnect.setPrivilege(userName, "worker");
                        response.sendRedirect("ManagerOperationsSuccess.html");
                        return;
                    }
                case "3":
                    if(priv.equals("candidate") || priv.equals("worker") || priv.equals("manager"))
                        {
                            request.getSession().setAttribute("details",userName);
                            response.sendRedirect("ManagerUserDetails.jsp");
                            return;
                        }
                default:

                    break;
            }
        }
        catch (Exception e){
        }
        response.sendRedirect("ManagerOperations.html");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
