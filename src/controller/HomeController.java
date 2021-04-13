package controller;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	
	private String fileName;
	private String uid;
	private int userXp, userHp;	// stores points of user
	// passed in from the login controller
	public void getUid(String uid) {
		this.uid = uid;
	}
	// not needed if file name is already known
	// temporarily set to where info is passed in from login controller
	public void getfileName(String fileName) {
		this.fileName=fileName;
	}
	
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
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/Login.fxml"));
		homeScr = loader.load();
		
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
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
	
	@FXML private void clearTasks(ActionEvent event) {
		TaskData.getInstance().clearTasks();
	}
	
	@FXML private void removeTask(ActionEvent event) {
		TaskData.getInstance().removeTask(taskListView.getSelectionModel().getSelectedItem());
	}
	
	@FXML private void markComplete(ActionEvent event) {
		TaskData.getInstance().markComplete(taskListView.getSelectionModel().getSelectedItem());
	}
	
	/*
	// logic for setting hp points based on task completion
	@FXML private void setHPprogress() {
		userHp = ;	//user points from boss hp
		hpBar.setProgress(userHp/300);
		hpPoints.setText(Integer.toString(x) + "/300");
	}
	
	// logic for setting xp points based on task completion
	@FXML private void setXPprogress() {
		userXp = ; // user points from completing tasks
		xpBar.setProgress(userXp/500);
		xpPoints.setText(Integer.toString(x) + "/500");
	} 
	*/
	
	/* to read json file and get the corresponding info of logged in user
	 * called from the initialize function 
	 */
	@SuppressWarnings("unchecked")
	public void readFile(String fileName, String uid) throws IOException, ClassNotFoundException {
		Path filePath = FileSystems.getDefault().getPath(fileName);

		//for reading objects from file
		ObjectInputStream inputFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(filePath)));

		//you can iterate through the file by using a while loop surrounded with a try{}catch{}
		//for the catch clause, catch EOFException and terminate the while loop
		boolean eof = false;
		while(!eof){
		    try{
		        Object obj = (Object) inputFile.readObject();
		        //object processing or storing for future application use
		        HashMap<String, String> info = (HashMap<String, String>) obj;
		        if(info.get("userId").equals(uid)) {
		        	String firstName = info.get("firstname");
			        String lastName = info.get("lastName");
			        user.setText(firstName + " " + lastName);
		        }
		        
		        
		    }catch (EOFException e){
		        eof = true;
		    }
		}
	}
	public void initialize() throws ClassNotFoundException, IOException {
		readFile(fileName, uid);
		taskListView.setItems(TaskData.getInstance().getTasks());
		taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		taskListView.setCellFactory(new Callback<ListView<TaskItem>, ListCell<TaskItem>>(){
			@Override
			public ListCell<TaskItem> call(ListView<TaskItem> param){
				ListCell<TaskItem> cell = new ListCell<TaskItem>() {
					@Override
					protected void updateItem(TaskItem item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setText(null);
						}else {
							setText(item.getShortDesc());
							int itemRank = item.getRank();
							if(itemRank == 500) {
								setTextFill(Color.DARKRED);
							}else if(itemRank >= 200 && itemRank <= 280) {
								setTextFill(Color.DARKGREEN);
							}else if(itemRank >= 300 && itemRank <= 380) {
								setTextFill(Color.ORANGE);
							}else if(itemRank >= 400 && itemRank <= 480) {
								setTextFill(Color.ORANGERED);
							}else {
								setTextFill(Color.BLACK);
							}
						}
					}
				};
				return cell;
			}
		});
		
		disableTaskButtons();
	}
	
	
	
	private void disableTaskButtons() {
		viewTaskButton.setDisable(true);
		markCompleteButton.setDisable(true);
		removeTaskButton.setDisable(true);
		
	}
}
