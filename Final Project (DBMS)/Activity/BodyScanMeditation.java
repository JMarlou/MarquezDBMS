package Activity;

import java.util.List;
import java.util.Scanner;

public class BodyScanMeditation extends Activity {

    private PromptDAO promptDAO;

    public BodyScanMeditation() {
        super("Body Scan Meditation", 0);  
        this.promptDAO = new PromptDAO(); 
    }

    @Override
    public void performActivity(Scanner scanner) {
        System.out.print("Enter the duration for Body Scan Meditation (in minutes): ");
        float userDuration = getValidDuration(scanner); 

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (long)(userDuration * 60 * 1000); 
        
        System.out.println("Starting Body Scan Meditation for " + userDuration + " minutes...");

        List<String> prompts = promptDAO.getPromptsForMeditation("Body Scan Meditation");

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

        System.out.println("\nBody Scan Meditation complete. Feel free to take a moment to reflect.");
    }
}
