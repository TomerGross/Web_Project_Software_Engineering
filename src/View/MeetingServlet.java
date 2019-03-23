package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MeetingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        DBConnect dbConnect = DBConnect.getInstance();

        String[] toMeet = request.getParameterValues("checked");
        String us = (String) request.getSession().getAttribute("userName");

        try {
            dbConnect.createMeetingWM(toMeet, us );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dbConnect.getPrivilege(us).equals("worker"))
            response.sendRedirect("WorkerCreateMeeting.jsp");
        else
            response.sendRedirect("ManagerCreateMeeting.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
