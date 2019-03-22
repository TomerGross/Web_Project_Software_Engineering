package View;

import Controller.DBConnect;
import Model.Scheduler;

import java.io.IOException;
import java.io.PrintWriter;


public class RegisterServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {

            DBConnect dbconnection = DBConnect.getInstance();


            response.setContentType("text/html;charset=UTF-8");

            String userName = request.getParameter("userName").toString();
            String firstName = request.getParameter("firstName").toString();
            String lastName = request.getParameter("lastName").toString();
            String id = request.getParameter("id").toString();
            String email = request.getParameter("email").toString();
            String psw = request.getParameter("psw").toString();


            dbconnection.registerUser(userName, firstName, lastName, id, email, psw, "candidate");

            Scheduler sc = Scheduler.getInstance();
            sc.createMeetingWithManager(userName);



            response.sendRedirect("LoginPage.html");


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
