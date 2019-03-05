package Model;

import com.sun.xml.internal.bind.v2.TODO;

import java.sql.*;
import java.util.Date;
import java.time.LocalDate;

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



    public void getData(){
        try {

            String query = "SELECT * FROM USERS";
            rs = st.executeQuery(query);

            out.println("DB Records:");
            while (rs.next()){

                out.println("\nname: " + rs.getString("user_name"));
                out.println("password: " + rs.getString("password"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
