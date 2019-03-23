package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static java.lang.System.out;

public class MeetingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        DBConnect dbConnect = DBConnect.getInstance();
        System.out.println("------------------> hello");

        String[] toMeet = request.getParameterValues("checked");
        for (String user: toMeet) {
            System.out.println("------------------>" + user);
        }

        try {
            dbConnect.createMeetingWorker(toMeet, (String) request.getSession().getAttribute("userName"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("WorkerCreateMeeting.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
