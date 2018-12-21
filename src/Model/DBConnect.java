package Model;

import java.sql.*;

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

    public void registerUser(String userName, String passw, String priv){


        try {
            PreparedStatement query = con.prepareStatement("INSERT INTO users (user_name, password , privilege) VALUES (?, ?, ?)");
            query.setString(1, userName);
            query.setString(2, passw);
            query.setString(3, priv);
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean loginUser(String userName, String passw){


        try {
            PreparedStatement query = con.prepareStatement("SELECT * FROM users WHERE user_name = ? AND password = ?");
            query.setString(1, userName);
            query.setString(2, passw);

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
