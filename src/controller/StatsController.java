package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.TaskData;
import model.TaskItem;
import model.User;
import model.UserData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for User Stats
 *
 */
public class StatsController {

    @FXML private Label CurrentTasks;
    @FXML private Label UserStats;
    @FXML private Label Points;
    @FXML private Label WeeklyTasks;
    @FXML private Label Level;
    @FXML private Label MonthlyTasks;
    @FXML private Label levelLabel;
    @FXML private Label pointLabel;
    @FXML private Button Home;
    @FXML private AnchorPane mainAnchor;
    @FXML private ProgressBar weekTask = new ProgressBar();
    @FXML private ProgressBar monthTask = new ProgressBar();
   
    /**
     * Sends user back to Home scene
     * 
     * @param event
     * @throws IOException
     */
    @FXML private void toHome(ActionEvent event) throws IOException {
    	mainAnchor = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
    	Scene scene = new Scene(mainAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }
    
    /**
	 * Called before the scene gets displayed
	 * Java compiler follows specific path
	 * -> Call to Class Constructor
	 * -> Initializes all @FXML annotated variables and function
	 * -> Calls initialize() function if exists
	 * 
	 * Used to initialize necessary stats fields
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
    public void initialize() throws ClassNotFoundException, IOException {
    	ObservableList<TaskItem> list;
    	
    	// Retrieves user specific task list
    	list = TaskData.getInstance().getTasks();
    	
    	
    	double totalWeekTasks = 0;
    	double totalMonthTasks = 0;
    	double completedWeekTasks = 0;
    	double completedMonthTasks = 0;
    	
    	// Iterates through task list to count
    	// completed tasks within a weeks time
    	// and a months time
    	// Counts total week and month tasks
    	for(TaskItem t : list) {
    		long daysLong = ChronoUnit.DAYS.between(LocalDate.now(), t.getDeadline());
    		int days = (int)daysLong;
    		
    		if(days <= 7) {
    			totalWeekTasks++;
    			
    			if(t.getCompleted() == true) {
    				completedWeekTasks++;
    			}
    		}
    		
    		else if(days <= 30) {
    			totalMonthTasks++;
    			
    			if(t.getCompleted() == true) {
    				completedMonthTasks++;
    			}
    		}
    	}
    	
    	// Sets progress bars
    	weekTask.setProgress((completedWeekTasks / totalWeekTasks));
		monthTask.setProgress((completedMonthTasks / totalMonthTasks));
		
		//set level label
		User player = UserData.getInstance().retrieveUser();
		int level = player.getUserLvl();
		levelLabel.setText(Integer.toString(level));
		
		//set point label
		int points = player.getUserXp();
		pointLabel.setText(Integer.toString(points));
    }
    
    
}
