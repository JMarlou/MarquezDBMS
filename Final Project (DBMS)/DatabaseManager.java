import java.sql.*;
import java.util.List;
import java.util.Scanner;
import Activity.PromptDAO;

public class DatabaseManager {

    public static void createUser(Connection connection, Scanner scanner) {
        System.out.print("Enter name: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    public static void readUsers(Connection connection) {
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nUsers:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("username") + ", Password: " + rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
    }

    public static void updateUser(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public static void deleteUser(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public static void viewMeditationPrompts(PromptDAO promptDAO, Scanner scanner) {
        System.out.print("Enter meditation type (Body Scan Meditation / Visualization): ");
        String meditationType = scanner.nextLine();

        List<String> prompts = promptDAO.getPromptsForMeditation(meditationType);
        if (prompts.isEmpty()) {
            System.out.println("No prompts available for the specified meditation type.");
        } else {
            System.out.println("\nPrompts for " + meditationType + ":");
            for (String prompt : prompts) {
                System.out.println(prompt);
            }
        }
    }

    public static void managePrompts(Connection connection, Scanner scanner, String tableName) {
        while (true) {
            System.out.println("\nManage Prompts for " + tableName + ":");
            System.out.println("1. Add Prompt");
            System.out.println("2. View Prompts");
            System.out.println("3. Update Prompt");
            System.out.println("4. Delete Prompt");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPrompt(connection, scanner, tableName);
                    break;
                case 2:
                    viewPrompts(connection, tableName);
                    break;
                case 3:
                    updatePrompt(connection, scanner, tableName);
                    break;
                case 4:
                    deletePrompt(connection, scanner, tableName);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addPrompt(Connection connection, Scanner scanner, String tableName) {
        System.out.print("Enter prompt text: ");
        String prompt = scanner.nextLine();

        String sql = "INSERT INTO " + tableName + " (prompt_text) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, prompt);
            stmt.executeUpdate();
            System.out.println("Prompt added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding prompt: " + e.getMessage());
        }
    }

    private static void viewPrompts(Connection connection, String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nPrompts in " + tableName + ":");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Prompt: " + rs.getString("prompt_text"));
            }
        } catch (SQLException e) {
            System.err.println("Error viewing prompts: " + e.getMessage());
        }
    }

    private static void updatePrompt(Connection connection, Scanner scanner, String tableName) {
        System.out.print("Enter prompt ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new prompt text: ");
        String prompt = scanner.nextLine();

        String sql = "UPDATE " + tableName + " SET prompt_text = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, prompt);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prompt updated successfully.");
            } else {
                System.out.println("No prompt found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating prompt: " + e.getMessage());
        }
    }

    private static void deletePrompt(Connection connection, Scanner scanner, String tableName) {
        System.out.print("Enter prompt ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prompt deleted successfully.");
            } else {
                System.out.println("No prompt found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting prompt: " + e.getMessage());
        }
    }
}
