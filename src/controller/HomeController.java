/*
 * Name: Viswa Bhargavi
 * UTSA ID: vab753
 * Course: CS 3443.001 - Application Programming
 * Professor: Heena Rathore
 * Team Project: Ninten-Do
 * HomeController class
 */

package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.TaskItem;
import model.TaskData;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController {
	@FXML private Button task, stats, goals, logout, quit;
	@FXML private Label welcome, name, xp, xpPoints, bossHP, hpPoints, taskList, totalT;
	@FXML private TextField user, numT;
	@FXML private ProgressBar xpBar, hpBar;
	@FXML private ListView<TaskItem> taskListView;
	@FXML private Pane title, topScr, bottomScr;
	@FXML private AnchorPane homeScr;
	@FXML private FXMLLoader loader;
	@FXML private Button viewTaskButton;
	@FXML private Button markCompleteButton;
	@FXML private Button removeTaskButton;
	@FXML private Button removeAllButton;
	
	private List<TaskItem> taskItems;
	
	@FXML private void toTaskInput(ActionEvent event) throws IOException{
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/TaskInput.fxml"));
		homeScr = loader.load();
		
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	@FXML private void toUserStats(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/Stats.fxml"));
		homeScr = loader.load();
		
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML private void logoutAndExit(ActionEvent event) throws IOException {
		//implement logout logic in Model class
		Platform.exit();
	}
	
	@FXML private void logout(ActionEvent event) throws IOException {
		/*TODO: implement logic in model class
		 * 		link to go back to login page
		 */
	}
	
	@FXML private void toTaskDetails(ActionEvent event) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/TaskDetails.fxml"));
//		
//		TaskDetailController controller = new TaskDetailController(taskListView.getSelectionModel().getSelectedItem());
//		loader.setController(controller);
//		
//		Scene scene = new Scene(loader.load());
//		Stage stage = new Stage();
//		stage.setTitle("Task Details");
//		stage.setScene(scene);
//		stage.setResizable(false);
//		stage.initStyle(StageStyle.UTILITY);
//		homeScr.setDisable(true);
//		stage.showAndWait();
//		homeScr.setDisable(false);
	}
	
	@FXML private void enableTaskButtons() {
		viewTaskButton.setDisable(false);
		markCompleteButton.setDisable(false);
		removeTaskButton.setDisable(false);
	}
	
	public void initialize() {
//		TaskItem item1 = new TaskItem("title", "long description of todo item", LocalDate.now());
//		TaskItem item2 = new TaskItem("title1", "long description of todo item1", LocalDate.now());
//		TaskItem item3 = new TaskItem("title2", "long description of todo item2", LocalDate.now());
//		TaskItem item4 = new TaskItem("title3", "long description of todo item3", LocalDate.now());
//		TaskItem item5 = new TaskItem("title4", "long description of todo item4", LocalDate.now());
//		
//		taskItems = new ArrayList<TaskItem>();
//		taskItems.add(item5);
//		taskItems.add(item4);
//		taskItems.add(item3);
//		taskItems.add(item2);
//		taskItems.add(item1);
//		
//		TaskData.getInstance().setTasks(taskItems);
		
		taskListView.getItems().setAll(TaskData.getInstance().getTasks());
		taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		disableTaskButtons();
	}
	
	private void disableTaskButtons() {
		viewTaskButton.setDisable(true);
		markCompleteButton.setDisable(true);
		removeTaskButton.setDisable(true);
		
	}
}
