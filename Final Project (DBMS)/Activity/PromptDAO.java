package Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromptDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/finalsdb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "jelou";
    private static final String DB_PASSWORD = "1234";
    
    public List<String> getPromptsForMeditation(String meditationType) {
        List<String> prompts = new ArrayList<>();
        String tableName = "";

        if ("Body Scan Meditation".equalsIgnoreCase(meditationType)) {
            tableName = "body_scan_prompts";
        } else if ("Visualization".equalsIgnoreCase(meditationType)) {
            tableName = "visualization_prompts";
        }

        if (tableName.isEmpty()) {
            System.out.println("Invalid meditation type: " + meditationType);
            return prompts;
        }


        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT prompt_text FROM " + tableName;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        prompts.add(rs.getString("prompt_text"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prompts;
    }
}
