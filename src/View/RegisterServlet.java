package View;

import Model.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")

public class RegisterServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {

            DBConnect dbconnectoin = new DBConnect();

            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");

            String userName = request.getParameter("userName").toString();
            String firstName = request.getParameter("firstName").toString();
            String lastName = request.getParameter("lastName").toString();
            String id = request.getParameter("id").toString();
            String email = request.getParameter("email").toString();
            String psw = request.getParameter("psw").toString();
            String repeat_psw = request.getParameter("psw-repeat").toString();
            String phoneNumber = request.getParameter("phoneNumber").toString();
            String gender = request.getParameter("gender").toString();
            String date = request.getParameter("birthDay").toString();
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date utilDate = sdf1.parse(date);
            java.sql.Date birthday = new java.sql.Date(utilDate.getTime());

            // validations!
            //if(!id.matches("[0-9]+")) throw new Exception("Invalid ID");

            dbconnectoin.registerUser(userName, firstName, lastName, id, email, psw, repeat_psw, phoneNumber, gender, birthday, "worker");

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
