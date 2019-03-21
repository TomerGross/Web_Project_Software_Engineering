package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class SchedulerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PrintWriter out = response.getWriter();
        try {

            HttpSession session = request.getSession();


            DBConnect dbconnectoin = DBConnect.getInstance();
            response.setContentType("text/html;charset=UTF-8");

            String option = request.getParameter("option");

            if (option.equals("v")){




            } else if (option.equals("d")){

                response.sendRedirect("MyDetails.jsp");



            }


        }
        catch(Exception e){
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }







}




