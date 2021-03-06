package Controller;
import Model.Meeting;
import Model.Scheduler;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import static java.lang.System.out;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private static DBConnect single_instance = null;

    public static DBConnect getInstance() {
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

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/phpmyadmin?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");


            String query = "CREATE DATABASE IF NOT EXISTS project_db";
            st = c.createStatement();
            st.executeUpdate(query);

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            st = con.createStatement();

            backToDefault();

            query = "CREATE TABLE IF NOT EXISTS `project_db`. `PROFILES` ( user_name VARCHAR(20) , first_name VARCHAR(20) NOT NULL DEFAULT '', last_name VARCHAR(20) NOT NULL DEFAULT '', id VARCHAR(9) NOT NULL DEFAULT '', email VARCHAR(40) NOT NULL DEFAULT '', psw VARCHAR(20), PRIMARY KEY (user_name))";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`. `privileges` ( user_name VARCHAR(20), privilege ENUM('candidate','worker','manager','admin'), FOREIGN KEY fk_un(user_name) REFERENCES profiles(user_name) ON UPDATE CASCADE ON DELETE NO ACTION )";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`.`meetings` ( `m_key` INT(5) NOT NULL AUTO_INCREMENT , `day` DATE NOT NULL , PRIMARY KEY (`m_key`))";
            st.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS `project_db`.`users_meetings` ( `m_key` INT(5) NOT NULL , `user_name` VARCHAR(9) NOT NULL, FOREIGN KEY (m_key) REFERENCES meetings(m_key))";
            st.executeUpdate(query);

            createAdmin();
            createManagers();

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


    public  void backToDefault() throws SQLException {


        String query = "DROP TABLE IF EXISTS `privileges`";
        st.executeUpdate(query);
        query = "DROP TABLE IF EXISTS `profiles`";
        st.executeUpdate(query);
        query = "DROP TABLE IF EXISTS `users_meetings`";
        st.executeUpdate(query);
        query = "DROP TABLE IF EXISTS `meetings`";
        st.executeUpdate(query);


    }
    public void createAdmin() throws SQLException {

        //create a default administrator
        PreparedStatement pquery = con.prepareStatement("INSERT INTO profiles (user_name, psw) VALUES (?, ?)");
        pquery.setString(1, "admin");
        pquery.setString(2, "1234");
        pquery.executeUpdate();


        pquery = con.prepareStatement("INSERT INTO privileges (user_name, privilege) VALUES (?, ?)");
        pquery.setString(1, "admin");
        pquery.setString(2, "admin");
        pquery.executeUpdate();


    }

    public void createManagers() throws SQLException {

        //create a default managers

        String[] managers = {"igor", "gil", "bar"};
        PreparedStatement pquery;

        for (String manager: managers){
            pquery = con.prepareStatement("INSERT INTO profiles (user_name, psw) VALUES (?, ?)");
            pquery.setString(1, manager);
            pquery.setString(2, "1234");
            pquery.executeUpdate();

            pquery = con.prepareStatement("INSERT INTO privileges (user_name, privilege) VALUES (?, ?)");
            pquery.setString(1, manager);
            pquery.setString(2, "manager");
            pquery.executeUpdate();

        }



    }

    public boolean userInDb(String userName) throws SQLException {

        //return if user is exists
        PreparedStatement pquery = con.prepareStatement("SELECT * FROM profiles WHERE profiles.user_name= ?");
        pquery.setString(1, userName);
        rs = pquery.executeQuery();
        return rs.next();

    }

    public void userIsNotArriving(String userName, int toCancel) throws SQLException {

        //in case that user canceled meeting
        PreparedStatement query = con.prepareStatement("DELETE FROM users_meetings WHERE users_meetings.user_name = ? AND users_meetings.m_key = ?");
        query.setString(1, userName);
        query.setInt(2, toCancel);
        query.executeUpdate();

        refreshMeetings();

    }

    public void registerUser(String userName, String firstName, String lastName, String id, String email, String psw, String priv){

        //register user in db
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

        //return if user is already sign up, if he is he can enter with his details
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

        //return the user privilege
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
        return null;
    }

    public String[] getDetails(String user_name){

        //extract the user details from db
        String[] details = new String[7];


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
                details[6] =  getPrivilege(details[0]);


            }
            return details;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return details;


    }

    public String[][] getDataAdmin(String curr){

        //return a list of details about all the users in the system
        try {

            int count=0;

            PreparedStatement query = con.prepareStatement("SELECT * FROM profiles,privileges WHERE profiles.user_name = privileges.user_name AND profiles.user_name != ?");
            query.setString(1, curr);
            rs = query.executeQuery();

            while (rs.next()){

                count++;
            }

            String[][] list = new String[count][3];
            count=0;
            rs = query.executeQuery();

            while (rs.next()){

                list[count][0] = rs.getString("user_name");
                list[count][1] = rs.getString("psw");
                list[count][2] = rs.getString("privilege");
                count++;
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int getNumOfUsersMWC() throws SQLException {

        //return the num of users in the system that they are not admins
        int count=0;
        PreparedStatement query =con.prepareStatement("SELECT * FROM profiles,privileges WHERE profiles.user_name = privileges.user_name AND privileges.privilege != ?");
        query.setString(1,"admin");
        rs = query.executeQuery();

        while (rs.next()) count++;
        return count;
    }

    public String[][] getDataManager(String curr){

        //return a list of details about all the users in the system without password because its a manager
        //and a manager shouldn't see other manager's passwords

        try {
            int count=0;

            PreparedStatement query =con.prepareStatement("SELECT * FROM profiles,privileges WHERE profiles.user_name = privileges.user_name AND privileges.privilege != ? AND profiles.user_name != ?");
            query.setString(1,"admin");
            query.setString(2, curr);
            rs = query.executeQuery();

            while (rs.next()){
                count++;
            }

            String[][] list = new String[count][4];
            count=0;
            rs = query.executeQuery();

            while (rs.next()){

                list[count][0] = rs.getString("user_name");
                list[count][1] = rs.getString("first_name");
                list[count][2] = rs.getString("last_name");
                list[count][3] = rs.getString("privilege");
                count++;
            }


            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumOfMeetings(String userName) {

        //return num of meetings of given user

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

    private int getMaxKey(ResultSet rs) throws SQLException {

        //return the sn of the last meeting that created

        int max = 0;

        while (rs.next()){
            if(rs.getInt("m_key") > max){
                max = rs.getInt("m_key");
            }

        }
        return max;
    }

    public boolean createMeetingWM(String[] toMeet, String self) throws SQLException {

        //create meeting between user and a list of users

        Scheduler sc = Scheduler.getInstance();
        int len;

        if(isMeetingValidWorker(toMeet) && getPrivilege(self).equals("worker")) return false;

        if (toMeet==null)
            len=0;
        else
            len=toMeet.length;

        String[] fullU = new String[len+1];

        for (int i=0; i< len ; i++){
            fullU[i] = toMeet[i];
        }
        fullU[len] = self;
        sc.setMeeting(fullU);
        return true;

    }

    public void createMeeting(Meeting meeting) throws SQLException {

        //actually execute the query for set the meeting in db

        PreparedStatement query = con.prepareStatement("INSERT INTO meetings (day) VALUES (?)");
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");

        String currentTime = sdf.format(meeting.getDate());
        query.setString(1, currentTime);

        query.executeUpdate();


        query = con.prepareStatement("SELECT * FROM meetings WHERE meetings.day = ?");

        query.setString(1, currentTime);


        rs = query.executeQuery();


        int key = getMaxKey(rs);



        for(String userName : meeting.getUsers()) {
            query = con.prepareStatement("INSERT INTO users_meetings (m_key, user_name) VALUES (?, ?)");
            query.setInt(1, key);
            query.setString(2,  userName);
            query.executeUpdate();

        }

    }

    public void removeUser(String userName) throws SQLException{

        //remove user from db in case he was fired
        PreparedStatement query = con.prepareStatement("DELETE FROM privileges WHERE user_name = ?");
        query.setString(1, userName);
        query.executeUpdate();

        query = con.prepareStatement("DELETE FROM users_meetings WHERE user_name = ?");
        query.setString(1, userName);
        query.executeUpdate();

        query = con.prepareStatement("DELETE FROM profiles WHERE user_name = ?");
        query.setString(1, userName);
        query.executeUpdate();

        refreshMeetings();
    }

    private void refreshMeetings() throws SQLException {

        //check validation of all the meetings in the system

        String query = "SELECT * FROM meetings";
        rs = st.executeQuery(query);
        Vector<Integer> vec = new Vector<>();
        int i=0;
        while(rs.next()){
           vec.add(rs.getInt("m_key"));
        }
        for(int key:vec){
            refreshMeeting(key);
        }
    }

    private void refreshMeeting(int m_key) throws SQLException {

        //check validation of given meeting

        int num = getNumOfMeetingUsers(m_key);
        if(num<2){
            PreparedStatement query = con.prepareStatement("DELETE FROM users_meetings WHERE m_key = ?");
            query.setInt(1, m_key);
            query.executeUpdate();

            query = con.prepareStatement("DELETE FROM meetings WHERE m_key = ?");
            query.setInt(1, m_key);
            query.executeUpdate();
        }
    }

    private int getNumOfMeetingUsers(int m_key) throws SQLException {

        //get a meeting sn, return the number of participants

        PreparedStatement query = con.prepareStatement("SELECT * FROM users_meetings WHERE m_key = ?");
        query.setInt(1, m_key);
        rs = query.executeQuery();
        int count=0;
        while(rs.next()) count++;
        return count;
    }

    public void setPrivilege(String userName, String priv) throws SQLException {

        //change privilege of user to other given privilege

        PreparedStatement query = con.prepareStatement("UPDATE privileges SET privilege = ? WHERE user_name = ?");
        query.setString(1, priv);
        query.setString(2, userName);
        query.executeUpdate();
    }

    public String[] getUsersForMeetingWorker(String userName) throws SQLException {

        //return all the users a worker can meet

        PreparedStatement query = con.prepareStatement("SELECT * FROM profiles,privileges WHERE profiles.user_name != ? AND privileges.privilege != ? AND privileges.privilege != ? AND profiles.user_name = privileges.user_name" );
        query.setString(1, userName);
        query.setString(2, "admin");
        query.setString(3, "candidate");
        rs = query.executeQuery();
        int count = 0;
        while(rs.next()) count++;

        rs = query.executeQuery();
        if(count==0) return null;
        String[] list = new String[count];
        count=0;
        while(rs.next()){
            list[count]=rs.getString("user_name");
            count++;
        }
        return list;
    }

    private boolean isMeetingValidWorker(String[] list){

        //limit worker to create a meeting with one manager

        int count=0;

        int len;
        if (list==null)
            len=0;
        else
            len=list.length;

        for(int i=0; i< len; i++)
            if(getPrivilege(list[i]).equals("manager")) count++;
        return !(count<2);
    }

    public String[] getUsersForMeetingManager(String curr_userName) throws SQLException {

        //return list of users a manager can meet

        int num = getNumOfUsersMWC()-1, count=0;
        String[] list = new String[num];

        PreparedStatement query = con.prepareStatement("SELECT * FROM profiles,privileges WHERE profiles.user_name != ? AND privileges.privilege != ? AND profiles.user_name = privileges.user_name" );
        query.setString(1,curr_userName);
        query.setString(2,"admin");

        rs = query.executeQuery();

        while (rs.next()){
            list[count] = rs.getString("user_name");
            count++;
        }
        return list;
    }

    public String[] getParticipants(int m_key) throws SQLException {

        //return a list of the participants of a given meeting

        String[] participants;
        int len =0;
        PreparedStatement pquery = con.prepareStatement("SELECT user_name FROM users_meetings WHERE users_meetings.m_key = ?");
        pquery.setInt(1, m_key);
        rs = pquery.executeQuery();

        while (rs.next()) len++;

        participants = new String[len];

        rs = pquery.executeQuery();

        len = 0;

        while (rs.next()){
            participants[len] = rs.getString("user_name");
            len++;
        }

        return participants;

    }

    public String getDateOfMeetingInString(int m_k) throws SQLException {

        //return the date of the given meeting take place

        PreparedStatement pquery = con.prepareStatement("SELECT day FROM meetings WHERE meetings.m_key = ?");
        pquery.setInt(1, m_k);

        rs = pquery.executeQuery();
        if(!rs.next()) return "error";

        rs = pquery.executeQuery();

        Date date = null;
        if(rs.next())
            date = rs.getDate("day");

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String text = df.format(date);

        return text;

    }

    public int[] getAllMeetings(String userName) throws SQLException {

        //return all meetings of given user

        int len = 0;
        int[] m_keys;

        PreparedStatement pquery = con.prepareStatement("SELECT m_key FROM users_meetings WHERE users_meetings.user_name = ?");
        pquery.setString(1, userName);

        rs = pquery.executeQuery();
        while (rs.next()) len++;


        if (len==0){
            return null;
        }

        m_keys = new int[len];
        rs = pquery.executeQuery();


        for (int i=0; i< len ; i++){
            rs.next();
            m_keys[i] = rs.getInt("m_key");
        }

        return m_keys;

    }


}

