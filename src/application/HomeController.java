/*
 * Name: Viswa Bhargavi
 * UTSA ID: vab753
 * Course: CS 3443.001 - Application Programming
 * Professor: Heena Rathore
 * Team Project: Ninten-Do
 * HomeController class
 */

package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController {
	@FXML private Button task, stats, goals, logout, quit;
	@FXML private Label welcome, name, xp, xpPoints, bossHP, hpPoints, taskList, totalT;
	@FXML private TextField user, numT;
	@FXML private ProgressBar xpBar, hpBar;
	@FXML private ListView<String> taskListView;
	@FXML private Pane title, topScr, bottomScr;
	@FXML private AnchorPane homeScr;
	@FXML private FXMLLoader loader;
	
	@FXML private void toTaskInput(ActionEvent event) throws IOException{
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("TaskInput.fxml"));
		homeScr = loader.load();
		
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML private void toUserStats(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Stats.fxml"));
		homeScr = loader.load();
		
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML private void logoutAndExit(ActionEvent event) throws IOException {
		//implement logout logic in Model class
		System.exit(0);
	}
	
	@FXML private void logout(ActionEvent event) throws IOException {
		/*TODO: implement logic in model class
		 * 		link to go back to login page
		 */
	}
}
