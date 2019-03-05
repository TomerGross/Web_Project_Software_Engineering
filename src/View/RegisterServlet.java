package View;

import Model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


public class RegisterServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {

            DBConnect dbconnectoin = DBConnect.getInstance();


            response.setContentType("text/html;charset=UTF-8");

            String userName = request.getParameter("userName").toString();
            String firstName = request.getParameter("firstName").toString();
            String lastName = request.getParameter("lastName").toString();
            String id = request.getParameter("id").toString();
            String email = request.getParameter("email").toString();
            String psw = request.getParameter("psw").toString();
            String repeat_psw = request.getParameter("r_psw").toString();

            //TODO: VALIDATE PASSSWORD

            dbconnectoin.registerUser(userName, firstName, lastName, id, email, psw, "candidate");
            response.sendRedirect("RegisterSucceed.html");

            //add to DB
        }
        catch(Exception e){
            e.printStackTrace();
            response.sendRedirect("RegisterError.html");
            return;
        } finally {
            out.close();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
