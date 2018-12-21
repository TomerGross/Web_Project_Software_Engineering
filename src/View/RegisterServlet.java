package View;

import Model.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")

public class RegisterServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {

            DBConnect dbconnectoin = new DBConnect();

            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");

            String userName = request.getParameter("userName").toString();
            String password = request.getParameter("psw").toString();

            dbconnectoin.registerUser(userName, password, "worker");

            response.sendRedirect("RegisterSucceed.html");
            out.close();

            //add to DB
        }
        catch(Exception e){
            e.printStackTrace();
            response.sendRedirect("RegisterError.html");
            return;
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
