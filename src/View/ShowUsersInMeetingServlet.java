package View;

import Controller.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowUsersInMeetingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBConnect dbConnect = DBConnect.getInstance();
        int m_key = Integer.parseInt(request.getParameter("m_key"));
        session.setAttribute("m_key", m_key);

        String curr_userName = (String) session.getAttribute("userName");
        String curr_priv = dbConnect.getPrivilege(curr_userName);

        switch (curr_priv) {
            case "admin":
                response.sendRedirect("AdminShowUsersInMeeting.jsp");
                break;
            case "manager":
                response.sendRedirect("ManagerShowUsersInMeeting.jsp");
                break;
            case "worker":
                response.sendRedirect("WorkerShowUsersInMeeting.jsp");
                break;
            default:
                response.sendRedirect("CandidateShowUsersInMeeting.jsp");
                break;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
