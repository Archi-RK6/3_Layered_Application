package Data;
import java.sql.*;

public class MassageKeeper {
    public void savingMessage(String content) {
        String url = "jdbc:postgresql://localhost:5432/hw1";
        String user = "postgres";
        String password = "ansergart1964";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection established.");
            String query = "INSERT INTO messages(content) VALUES(?)";  // Используем плейсхолдер ?
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, content);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Message saved.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
