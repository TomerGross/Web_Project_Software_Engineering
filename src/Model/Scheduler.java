package Model;
import Controller.DBConnect;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private static Scheduler single_instance = null;

    public static Scheduler getInstance() {
        //singleton
        if (single_instance == null)
            single_instance = new Scheduler();

        return single_instance;
    }

    boolean free(String userName, Date date)  {

        //return if user is free to meet on the given date
        try{
            DBConnect dbConnect = DBConnect.getInstance();

            Connection con = dbConnect.getConnection();
            String query = "SELECT * FROM users_meetings, meetings WHERE meetings.m_key = users_meetings.m_key AND meetings.day=? AND users_meetings.user_name = ?";
            PreparedStatement p_query = con.prepareStatement(query);

            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");

            String currentTime = sdf.format(date);
            p_query.setString(1,currentTime);
            p_query.setString(2,userName);

            ResultSet rs = p_query.executeQuery();
            boolean bool = rs.next();
            System.out.println("*******************************" + bool);
            return !bool;
        }
        catch (SQLException e){}
            return false;
        }

    public void createMeetingWithManager(String userName) throws SQLException {

        //set meeting automatically with the manager that have the less meetings (in closest date he is free)

        DBConnect dbConnect = DBConnect.getInstance();
        String query = "SELECT user_name FROM `privileges` WHERE privilege = \"manager\"";
        Connection con = dbConnect.getConnection();

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



        setMeeting(toArray(list));


    }

    private Vector<String> toVector(String[] arr){

        Vector<String> vec = new Vector<>();
        for(String iter: arr){
            vec.add(iter);
        }
        return vec;
    }

    private String[] toArray(Vector<String> vec){

        String[] arr = new String[vec.size()];
        for(int i=0; i< vec.size(); i++){
            arr[i] = vec.get(i);
        }
        return arr;
    }

    public boolean setMeeting(String[] userNames) throws SQLException {

        //calculate the date of the meeting that all the users are free to meet, set the meeting

        Vector<String> temp;
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
            flag=true;
        }


        temp = toVector(userNames);
        Meeting meeting =new Meeting(date, temp);

        DBConnect dbConnection =DBConnect.getInstance();
        dbConnection.createMeeting(meeting);
        return true;
    }
}
