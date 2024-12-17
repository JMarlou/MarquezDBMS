package Activity;

import java.util.List;
import java.util.Scanner;

public class Visualization extends Activity {

    private PromptDAO promptDAO;

    public Visualization() {
        super("Visualization", 0); 
        this.promptDAO = new PromptDAO();  
    }

    @Override
    public void performActivity(Scanner scanner) {
        System.out.print("Enter the duration for Visualization (in minutes): ");
        float userDuration = getValidDuration(scanner); 

        long startTime = System.currentTimeMillis();  
        long endTime = startTime + (long)userDuration * 60 * 1000; 
        
        System.out.println("Starting Visualization for " + userDuration + " minutes...");

        List<String> prompts = promptDAO.getPromptsForMeditation("Visualization");

        int promptIndex = 0;
        while (System.currentTimeMillis() < endTime) {
            long timeRemaining = (endTime - System.currentTimeMillis()) / 1000; 
            System.out.println("Time remaining: " + timeRemaining + " seconds...");

            if (promptIndex < prompts.size()) {
                System.out.println(prompts.get(promptIndex));  
                promptIndex++;
            } else {
                promptIndex = 0; 
            }

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nVisualization complete. Feel free to take a moment to reflect.");
    }
}
