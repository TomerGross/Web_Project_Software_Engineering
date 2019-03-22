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
            String userName = request.getParameter("userName");
            String op = request.getParameter("operation");
            String priv;

            if(userName.equals(sessionUserName)) {
                response.sendRedirect("AdminOperations.html");
                return;
            }

            switch (op) {
                case "1":
                    dbConnect.removeUser(userName);
                    break;
                case "2":
                    priv = dbConnect.getPrivilege(userName);
                    if(priv.equals("candidate"))
                        dbConnect.setPrivilege(userName, "worker");
                    else if(priv.equals("worker"))
                        dbConnect.setPrivilege(userName, "manager");
                    break;
                case "3":
                    priv = dbConnect.getPrivilege(userName);
                    if(priv.equals("candidate") || priv.equals("worker"))
                        dbConnect.removeUser(userName);
                    else if(priv.equals("manager"))
                        dbConnect.setPrivilege(userName, "worker");
                    else
                        dbConnect.setPrivilege(userName, "manager");
                    break;
                case "4":
                    String[] list = dbConnect.getDetails(userName);
                    request.getSession().setAttribute("details",list);
                    break;
                default:
                    break;
            }
            response.sendRedirect("AdminOperations.html");
        }
        catch (Exception e){
            response.sendRedirect("AdminOperations.html");

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
