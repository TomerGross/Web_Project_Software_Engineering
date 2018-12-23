package View;

import Model.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        DBConnect dbconnection = new DBConnect();
        PrintWriter out = response.getWriter();
        String userName = request.getParameter("userName").toString();
        String password = request.getParameter("password").toString();

        if(dbconnection.loginUser(userName)){
            response.sendRedirect("LoginSucceed.html");
        } else {
            response.sendRedirect("LoginError.html");

        }



        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
