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
            String userName = request.getParameter("userName");
            String op = request.getParameter("operation");
            String priv;

            if(userName.equals(sessionUserName)) {
                response.sendRedirect("ManagerOperations.html");
                return;
            }
            priv = dbConnect.getPrivilege(userName);
            switch (op) {
                case "1":
                    if(priv.equals("worker") || priv.equals("candidate"))
                        dbConnect.removeUser(userName);
                    break;
                case "2":
                    if(priv.equals("candidate"))
                        dbConnect.setPrivilege(userName, "worker");
                    break;
                case "3":
                    request.getSession().setAttribute("details",userName);
                    response.sendRedirect("ManagerUserDetails.jsp");
                    return;
                default:
                    break;
            }
        }
        catch (Exception e){
        }
        finally {
            response.sendRedirect("AdminOperations.html");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
