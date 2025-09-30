// 代码生成时间: 2025-10-01 03:02:22
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableScheduling
@Service
public class TestSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(TestSchedulerService.class);
    
    /**
     * Method to execute scheduled tasks
     */
    @Scheduled(fixedRate = 5000)
    public void executeScheduledTask() {
        try {
            // Task execution logic here
            logger.info("Scheduled task is running...");
            
            // Example of a task: print a message to the console
            System.out.println("Executing scheduled task...");
        } catch (Exception e) {
            // Error handling
            logger.error("Error occurred while executing scheduled task: " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Method to trigger task execution on demand
     * @param taskId the ID of the task to be executed
     */
    public void executeTaskOnDemand(String taskId) {
        try {
            // Task execution logic based on the taskId
            logger.info("Executing task on demand: " + taskId);
            
            // Example of a task: print a message to the console
            System.out.println("Executing task on demand with ID: " + taskId);
        } catch (Exception e) {
            // Error handling
            logger.error("Error occurred while executing task on demand with ID: " + taskId + ": " + e.getMessage(), e);
        }
    }
}
