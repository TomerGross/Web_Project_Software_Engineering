package Model;
import Conroller.DBConnect;
import  Model.*;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private static Scheduler single_instance = null;

    public static Scheduler getInstance()
    {
        if (single_instance == null)
            single_instance = new Scheduler();

        return single_instance;
    }


    public boolean free(String userName, Date date)  {

        try{
            DBConnect dbConnect = DBConnect.getInstance();

            Connection con = dbConnect.getConnetion();
            String query = "SELECT * FROM `users_meetings`,`meetings` WHERE meetings.m_key = users_meetings.m_key AND meetings.date=? AND users_meetings.user_name = ?";
            PreparedStatement p_query = con.prepareStatement(query);

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");

            String currentTime = sdf.format(date);
            p_query.setString(1,currentTime);
            p_query.setString(2,userName);

            ResultSet rs = p_query.executeQuery();
            return rs.next();
        }
        catch (SQLException e){}
            return false;
        }

    public void createMeetingWithManager(String userName) throws SQLException {
        DBConnect dbConnect = DBConnect.getInstance();
        String query = "SELECT user_name FROM `privileges` WHERE privilege = \"manager\"";
        Connection con = dbConnect.getConnetion();

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        String manager,minGuy="";
        int min=-1, temp;
        while(rs.next()){
            manager = rs.getString("user_name");
            temp = dbConnect.getNumOfMeetings(manager);
            if(min == -1 || temp<min) {
                min = temp;
                minGuy = manager;
            }
        }

        if(min==-1) return;

        Vector<String> list = new Vector<>();
        list.add(userName);
        list.add(minGuy);

        setMeeting(list);




    }

    public boolean setMeeting(Vector<String> userNames) throws SQLException {

        Date date = Calendar.getInstance().getTime();

        date.setHours(00);
        date.setMinutes(00);
        date.setSeconds(00);

        date = new Date(date.getTime() + TimeUnit.DAYS.toMillis( 1 ));
        boolean flag=true;
        int count = 0;
        while(count<31) {
            for (String name : userNames) {
                if(!free(name, date))
                {
                    flag=false;
                    break;
                }
            }
            if(flag) break;
            date = new Date(date.getTime() + TimeUnit.DAYS.toMillis( 1 ));
            count++;
        }
        Meeting meeting =new Meeting(date,userNames);

        DBConnect dbConnection =DBConnect.getInstance();
        dbConnection.createMeeting(meeting);
        return true;
    }

    public Vector<Integer> getMeetingsKeys(String userName) throws SQLException {
        Vector<Integer> list=new Vector<>();
        DBConnect dbConnect = DBConnect.getInstance();
        Connection con = dbConnect.getConnetion();

        String query = "SELECT * FROM `users_meetings` WHERE users_meetings.user_name = ?";

        PreparedStatement p_query = con.prepareStatement(query);
        p_query.setString(1,userName);

        ResultSet rs = p_query.executeQuery();

        while(rs.next()) list.add(rs.getInt("m_key"));

        return list;
    }


    public Integer getMeetingKeyToday(String userName) throws SQLException {
        Vector<Integer> list = getMeetingsKeys(userName);
        DBConnect dbConnect = DBConnect.getInstance();
        Connection con = dbConnect.getConnetion();
        String currentTime,query;
        ResultSet rs;
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        for(Integer iter : list)
        {
            query = "SELECT * FROM `meetings` WHERE meetings.day = ? AND meetings.m_key = ?";
            PreparedStatement p_query = con.prepareStatement(query);
            currentTime = sdf.format(new Date());
            p_query.setString(1,currentTime);
            p_query.setInt(2,iter);
            rs = p_query.executeQuery();
            if(rs.next()) {
                return iter;
            }
        }
        return -1;
    }
}
