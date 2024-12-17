import java.sql.*;
import java.util.Scanner;

public class Login {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/finalsdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "jelou";
    private static final String DB_PASSWORD = "1234";

    public static String handleLogin(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Welcome, " + username + "!");
                        return username;
                    } else {
                        System.out.println("Invalid username or password.");
                        return null;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String loggedInUser = handleLogin(scanner);

        if (loggedInUser != null) {
        } else {
        }
    }
}
