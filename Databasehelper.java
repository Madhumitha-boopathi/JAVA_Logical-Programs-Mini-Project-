package project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Databasehelper {
    private static final String URL = "jdbc:postgresql://localhost:5432/stock";  
    private static final String USER = "postgres";  
    private static final String PASSWORD = "priya";  
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If a user is found, return true (login success)
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // In case of an error, return false
        }
    }
}