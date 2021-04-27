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
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application StatsController.java
 * is created in order to set the logic, scenes, functions and calls needed to allow users to view and update
 * the stats for the Ninten-Do application.
 * 
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
   
    @FXML private void toHome(ActionEvent event) throws IOException {
    	//function 'tohome' changes scenes to the home scene
    	mainAnchor = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
    	Scene scene = new Scene(mainAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }
    
    public void initialize() throws ClassNotFoundException, IOException {
    	//initializes the data and implements the logic to set the progress bars for the user.
    	//set progress bars
    	ObservableList<TaskItem> list;
    	
    	list = TaskData.getInstance().getTasks();
    	
    	
    	double totalWeekTasks = 0;
    	double totalMonthTasks = 0;
    	double completedWeekTasks = 0;
    	double completedMonthTasks = 0;
    	
    	
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
