package Controller;

import Model.Meeting;

import java.sql.*;
import java.util.Vector;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static DBConnect single_instance = null;

    public static DBConnect getInstance()
    {
        if (single_instance == null)
            single_instance = new DBConnect();

        return single_instance;
    }

    public Connection getConnection(){
        return this.con;
    }

    private DBConnect(){

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            String query = "CREATE DATABASE IF NOT EXISTS project_db";
            st = con.createStatement();
            st.executeUpdate(query);

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");



            query = "CREATE TABLE IF NOT EXISTS `project_db`. `PROFILES` ( user_name VARCHAR(20), first_name VARCHAR(20), last_name VARCHAR(20), id VARCHAR(9), email VARCHAR(40), psw VARCHAR(20), PRIMARY KEY (user_name))";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`. `privileges` ( user_name VARCHAR(20), privilege ENUM('candidate','worker','manager','admin'), FOREIGN KEY fk_un(user_name) REFERENCES profiles(user_name) ON UPDATE CASCADE ON DELETE NO ACTION )";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`.`meetings` ( `m_key` INT(5) NOT NULL AUTO_INCREMENT , `day` DATE NOT NULL , PRIMARY KEY (`m_key`))";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`.`users_meetings` ( `m_key` INT(5) NOT NULL , `user_name` VARCHAR(9) NOT NULL, FOREIGN KEY (m_key) REFERENCES meetings(m_key))";
            st.executeUpdate(query);



            out.println("con: " + con);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    public void registerUser(String userName, String firstName, String lastName, String id, String email, String psw, String priv){


        try {
            PreparedStatement query = con.prepareStatement("INSERT INTO profiles (user_name, first_name, last_name, id, email, psw) VALUES (?, ?, ?, ?, ?, ?)");
            query.setString(1, userName);
            query.setString(2, firstName);
            query.setString(3, lastName);
            query.setString(4, id);
            query.setString(5, email);
            query.setString(6, psw);


            query.executeUpdate();

            PreparedStatement second_query = con.prepareStatement("INSERT INTO privileges (user_name, privilege) VALUES (?, ?)");
            second_query.setString(1, userName);
            second_query.setString(2, priv);

            second_query.executeUpdate();






        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean loginUser(String userName, String password){


        try {
            PreparedStatement query = con.prepareStatement("SELECT * FROM profiles WHERE user_name = ? AND psw = ?");
            query.setString(1, userName);
            query.setString(2, password);

            rs = query.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public String getPrivilege(String userName){
        try {
            PreparedStatement query = con.prepareStatement("SELECT * FROM privileges WHERE user_name = ?");
            query.setString(1, userName);

            rs = query.executeQuery();
            if (rs.next()){
                return rs.getString("privilege");

            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "candidate"; //default for error

    }

    public String[] getDetails(String user_name){

        String[] details = new String[6];


        try {
            PreparedStatement query = con.prepareStatement("SELECT * FROM profiles WHERE user_name = ?");
            query.setString(1, user_name);

            rs = query.executeQuery();
            if (rs.next()){
                details[0] =  rs.getString("user_name");
                details[1] =  rs.getString("first_name");
                details[2] =  rs.getString("last_name");
                details[3] =  rs.getString("id");
                details[4] =  rs.getString("email");
                details[5] =  rs.getString("psw");


            }
            return details;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return details;


    }


    public Vector<Vector<String>> getData(){
        try {


            Vector<Vector<String>> list = new Vector<>();
            Vector<String> temp = new Vector<>();

            String query = "SELECT * FROM profiles,privileges WHERE profiles.user_name = privileges.user_name";
            rs = st.executeQuery(query);

            while (rs.next()){

                temp.add(rs.getString("user_name"));
                temp.add(rs.getString("psw"));
                temp.add(rs.getString("privilege"));

                list.add(temp);
                temp.clear();
            }

            return list;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public int getNumOfMeetings(String userName) {
        try {
            PreparedStatement query = con.prepareStatement("SELECT * FROM users_meetings WHERE user_name = ?");
            query.setString(1, userName);
            rs = query.executeQuery();
            int count=0;
            while(rs.next()) count++;
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getMaxKey(ResultSet rs) throws SQLException {
        int max = 0;

        while (rs.next()){
            if(rs.getInt("m_key") > max){
                max = rs.getInt("m_key");
            }

        }
        return max;
    }

    public void createMeeting(Meeting meeting) throws SQLException {
        PreparedStatement query = con.prepareStatement("INSERT INTO meetings (day) VALUES (?)");
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");

        String currentTime = sdf.format(meeting.getDate());
        query.setString(1, currentTime);

        rs = query.executeQuery();


        int key = getMaxKey(rs);



        for(String userName : meeting.getUsers()) {
            query = con.prepareStatement("INSERT INTO users_meetings (m_key, user_name) VALUES (?, ?)");
            query.setInt(1, key);
            query.setString(2,  userName);
            query.executeQuery();

        }

    }
}
