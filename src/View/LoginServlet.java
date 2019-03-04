package View;

import Model.*;
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

        if(dbconnection.loginUser(userName, psw)){
            response.sendRedirect("LoginSucceed.html");
        } else {
            response.sendRedirect("LoginError.html");

        }



        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
