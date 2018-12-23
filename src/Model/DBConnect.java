package Model;

import java.sql.*;
import java.time.LocalDate;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;


    public DBConnect(){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            st = con.createStatement();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void registerUser(String userName, String firstName, String lastName, String id, String email, String psw, String repeat_psw, String phoneNumber, String gender, java.sql.Date birthday, String priv){


        try {
            PreparedStatement query = con.prepareStatement("INSERT INTO profiles (user_name, first_name, last_name, id, email, password, repeat_password, phone_number, gender, birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            query.setString(1, userName);
            query.setString(2, firstName);
            query.setString(3, lastName);
            query.setString(4, id);
            query.setString(5, email);
            query.setString(6, psw);
            query.setString(7, repeat_psw);
            query.setString(8, phoneNumber);
            query.setString(9, gender);
            query.setDate(10, birthday);

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
            PreparedStatement query = con.prepareStatement("SELECT * FROM profiles WHERE user_name = ? AND password = ?");
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

            System.out.println("DB Records:");
            while (rs.next()){

                System.out.println("\nname: " + rs.getString("user_name"));
                System.out.println("password: " + rs.getString("password"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
