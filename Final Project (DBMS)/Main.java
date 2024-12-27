import java.util.Scanner;
import java.util.List;
import Activity.Activity;
import Activity.BodyScanMeditation;
import Activity.BoxBreathing;
import Activity.PhysicalExercise;
import Activity.Visualization;
import Activity.PromptDAO;
import java.sql.*;

public class Main {

   private static String loggedInUser = null;
   private static AchievementManager achievementManager = new AchievementManager();

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       PromptDAO promptDAO = new PromptDAO();
       System.out.println("Welcome to Mindful Reflections!");

       while (true) {
           if (loggedInUser == null) {
               System.out.println("\nPlease choose an option:");
               System.out.println("1. Login");
               System.out.println("2. Database Manager");
               System.out.println("0. Exit");
           } else {
               System.out.println("\nHello, " + loggedInUser + "!");
               System.out.println("1. Perform Activities");
               System.out.println("2. View Achievements");
               System.out.println("3. Log out");
               System.out.println("0. Exit");
           }

           System.out.print("Enter your choice: ");
           int choice = scanner.nextInt();
           scanner.nextLine();

           switch (choice) {
               case 1:
                   if (loggedInUser == null) {
                       loggedInUser = Login.handleLogin(scanner);
                   } else {
                       performActivities(scanner, promptDAO);
                   }
                   break;
               case 2:
                   if (loggedInUser == null) {
                       manageDatabase(scanner);
                   } else {
                       viewAchievements();
                   }
                   break;
               case 3:
                   if (loggedInUser != null) {
                       loggedInUser = null;
                       System.out.println("You have logged out successfully.");
                   } else {
                       System.out.println("You are not logged in.");
                   }
                   break;
               case 0:
                   System.out.println("Goodbye!");
                   scanner.close();
                   return;
               default:
                   System.out.println("Invalid choice, please try again.");
           }
       }
   }

   private static void viewAchievements() {
       System.out.println("\nYour Achievements:");
       achievementManager.checkForAchievements();
   }

   private static void performActivities(Scanner scanner, PromptDAO promptDAO) {
       Activity[] activities = {
           new BodyScanMeditation(),
           new BoxBreathing(),
           new PhysicalExercise(),
           new Visualization()
       };

       while (true) {
           System.out.println("\nChoose an activity to perform:");
           for (int i = 0; i < activities.length; i++) {
               System.out.println((i + 1) + ". " + activities[i].getName());
           }
           System.out.println("0. Back to Main Menu");
           System.out.print("Enter choice: ");
          
           int choice = scanner.nextInt();
           scanner.nextLine(); 
  
           if (choice == 0) {
               System.out.println("Returning to Main Menu...");
               break; 
           }
  
           if (choice >= 1 && choice <= activities.length) {
               Activity activity = activities[choice - 1];
               activity.displayInfo();
               activity.waitForEnter(scanner);

               if (activity instanceof BodyScanMeditation || activity instanceof Visualization) {
                   String meditationType = activity.getName();
                   List <String> prompts = promptDAO.getPromptsForMeditation(meditationType);
                   if (!prompts.isEmpty()) {
                       System.out.println("Available Prompts for " + meditationType + ":");
                       for (String prompt : prompts) {
                           System.out.println(prompt);
                       }
                   } else {
                       System.out.println("No prompts available for this activity.");
                   }
               }

               activity.performActivity(scanner);
               achievementManager.incrementActivityCount(); 
               System.out.println("You have completed " + achievementManager.getActivitiesCompleted() + " activities so far.");

               System.out.println("Press Enter when done!");
               scanner.nextLine();  
           } else {
               System.out.println("Invalid choice. Try again.");
           }
       }
   }

   private static void manageDatabase(Scanner scanner) {
       System.out.println("\nWelcome to the Database Manager!");
       String URL = "jdbc:mysql://localhost:3306/finalsdb?useSSL=false&serverTimezone=UTC";
       String USER = "jelou";
       String PASSWORD = "1234";

       try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
           while (true) {
               System.out.println("\nDatabase Manager Menu:");
               System.out.println("1. Create User");
               System.out.println("2. Read Users");
               System.out.println("3. Update User");
               System.out.println("4. Delete User");
               System.out.println("5. Manage Bodyscan Prompts");
               System.out.println("6. Manage Visualization Prompts");
               System.out.println("0. Back to Main Menu");

               System.out.print("Enter your choice: ");
               int choice = scanner.nextInt();
               scanner.nextLine();

               switch (choice) {
                   case 1:
                       DatabaseManager.createUser(connection, scanner);
                       break;
                   case 2:
                       DatabaseManager.readUsers(connection);
                       break;
                   case 3:
                       DatabaseManager.updateUser(connection, scanner);
                       break;
                   case 4:
                       DatabaseManager.deleteUser(connection, scanner);
                       break;
                   case 5:
                       DatabaseManager.managePrompts(connection, scanner, "body_scan_prompts");
                       break;
                   case 6:
                       DatabaseManager.managePrompts(connection, scanner, "visualization_prompts");
                       break;
                   case 0:
                       return;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
}
