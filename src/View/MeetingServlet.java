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
        String priv = dbConnect.getPrivilege(us);
        boolean valid;

        if (toMeet == null && priv.equals("worker")){
            response.sendRedirect("WorkerCreateMeetingError.jsp");
            return;
        } else if(toMeet == null && priv.equals("manager")){
            response.sendRedirect("ManagerCreateMeetingError.jsp");
            return;
        }

        try {
            valid = dbConnect.createMeetingWM(toMeet, us );
            if(!valid && priv.equals("worker")){
                response.sendRedirect("WorkerCreateMeetingError.jsp");
                return;
            } else if (!valid && priv.equals("manager")){
                response.sendRedirect("ManagerCreateMeetingError.jsp");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (priv.equals("worker"))
            response.sendRedirect("WorkerCreateMeetingSuccess.jsp");
        else
            response.sendRedirect("ManagerCreateMeetingSuccess.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
