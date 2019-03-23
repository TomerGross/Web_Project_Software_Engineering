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
                response.sendRedirect("LoginPage.html");
                return;
            }
            String userName = request.getParameter("userName");
            String op = request.getParameter("operation");
            String priv = dbConnect.getPrivilege(userName);

            if(userName.equals(sessionUserName)) {
                response.sendRedirect("AdminOperations.html");
                return;
            }

            switch (op) {
                case "1":
                    dbConnect.removeUser(userName);
                    break;
                case "2":
                    if(priv.equals("candidate"))
                        dbConnect.setPrivilege(userName, "worker");
                    else if(priv.equals("worker"))
                        dbConnect.setPrivilege(userName, "manager");
                    break;
                case "3":
                    if(priv.equals("candidate") || priv.equals("worker"))
                        dbConnect.removeUser(userName);
                    else if(priv.equals("manager"))
                        dbConnect.setPrivilege(userName, "worker");
                    else
                        dbConnect.setPrivilege(userName, "manager");
                    break;
                case "4":
                    if(priv!=null){
                            request.getSession().setAttribute("details",userName);
                            response.sendRedirect("AdminUserDetails.jsp");
                        }
                    return;
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
