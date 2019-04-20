package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

class BaseDAO {

    private Properties props = new Properties();
    //private Connection con;

    BaseDAO() {
        // https://jdbc.postgresql.org/documentation/80/connect.html
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            props.setProperty("user", "amhkempe");
            props.setProperty("password", "Ab12345");
//            props.setProperty("ssl", "true");
//            props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Contact the developer with a screenshot of this error" + e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    final Connection getConnection() {
        try {
            System.out.println("BaseDAO");
            String url = "jdbc:mysql://databases.aii.avans.nl:3306/amhkempe_db2";
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    boolean prepStmnt(String query) {
        boolean succes = true;
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException sqle) {
            succes = false;
            sqle.printStackTrace();
        }
        return succes;
    }
}