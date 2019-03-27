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
                //security, check if he is legit manager or just played with the pages
                response.sendRedirect("LoginPage.html");
                return;
            }
            String userName = request.getParameter("userName");
            String op = request.getParameter("operation");
            String priv = dbConnect.getPrivilege(userName);


            System.out.println("im: " + sessionUserName + " doing on: " + userName + "  opeation: " + op);

            if(!dbConnect.userInDb(userName)) {
                response.sendRedirect("ManagerOperationsUserNotFound.jsp");
                return;
            }

            if(userName.equals(sessionUserName)) {
                //block the option to use self operations
                response.sendRedirect("ManagerOperationsSelfError.jsp");
                return;
            }


            if(priv.equals("admin")){
                //unauthorized operation on admin/manager(the only option is to show user data)
                response.sendRedirect("ManagerOperationsUnauthorized.jsp");
                return;
            } else if(priv.equals("manager") && !op.equals("3")){
                response.sendRedirect("ManagerOperationsUnauthorized.jsp");
                return;
            }

            System.out.println("now: " + op+ " im: " + priv);

            switch (op) {
                case "1":
                    //fire user
                    if(priv.equals("worker") || priv.equals("candidate")) {
                        dbConnect.removeUser(userName);
                        response.sendRedirect("ManagerOperationsSuccess.jsp");
                        return;
                    }
                    break;

                case "2":
                    //hire someone to job
                    if(priv.equals("candidate")) {
                        dbConnect.setPrivilege(userName, "worker");
                        response.sendRedirect("ManagerOperationsSuccess.jsp");
                        return;
                    }
                    break;
                case "3":
                    //show user details
                    request.getSession().setAttribute("details",userName);
                    response.sendRedirect("ManagerUserDetails.jsp");
                    return;

                default:

                    break;
            }
        }
        catch (Exception e){
        }
        response.sendRedirect("ManagerOperations.jsp");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
