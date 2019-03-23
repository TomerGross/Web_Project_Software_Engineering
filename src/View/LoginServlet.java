package View;

import Controller.DBConnect;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        DBConnect dbconnection = DBConnect.getInstance();
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName").toString();
        String psw = request.getParameter("psw").toString();
        System.out.println("user: " + userName+ " Password: " + psw);


        HttpSession session=request.getSession();
        session.setAttribute("userName", userName);


        if(dbconnection.loginUser(userName, psw)){

            String priv = dbconnection.getPrivilege(userName);

            switch (priv){

                case "candidate":
                    response.sendRedirect("./Candidate/CandidateMain.html");
                    break;

                case "worker":
                    response.sendRedirect("./Worker/WorkerMain.html");
                    break;

                case "manager":
                    response.sendRedirect("./Manager/ManagerMain.html");
                    break;

                case "admin":
                    response.sendRedirect("./Admin/AdminMain.html");
                    break;

                default:
                    response.sendRedirect("./Candidate/CandidateMain.html");
                    break;


            }


        } else {
            response.sendRedirect("LoginError.html");

        }



        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
