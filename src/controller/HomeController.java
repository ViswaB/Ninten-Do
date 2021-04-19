package controller;

import java.io.IOException;

import model.TaskItem;
import model.User;
import model.UserData;
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
	@FXML private Button manageTasksBtn, statsBtn, logoutBtn, quitBtn;
	@FXML private Button viewTaskButton, markCompleteButton, removeTaskButton, removeAllButton;
	@FXML private Label welcomeLbl, levelLbl,xpLbl, bossHpLbl, taskListLbl, totalTasksLbl;
	@FXML private Label userLevel, xpPointsLbl, hpPointsLbl;
	@FXML private TextField nameUser, numT;
	@FXML private ProgressBar xpBar, hpBar;
	@FXML private ListView<TaskItem> taskListView;
	@FXML private Pane title, topScr, bottomScr;
	@FXML private AnchorPane homeScr;
	@FXML private FXMLLoader loader;
	
	// instances to retrieve user data
	User currentUser;
	
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
		//UserData.getInstance().updateUser();
		Platform.exit();
	}
	
	@FXML private void logout(ActionEvent event) throws IOException {
		UserData.getInstance().updateUser();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/LoginScreen.fxml"));
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
		TaskItem task = taskListView.getSelectionModel().getSelectedItem();
		TaskData.getInstance().markComplete(task);
		//UserData.getInstance().retrieveUser().setCompletedTask(task);
	}
	
	public void initialize() throws ClassNotFoundException, IOException {
		/* comment off when user data available */
		//currentUser = UserData.getInstance().retrieveUser();
		//nameUser.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
		
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
		
		/* comment off when users data are added */
		//setXPprogress();
		//setHPprogress();
		
		disableTaskButtons();
	}
	
	/* Comment off two methods when users data available

	// logic for setting hp points based on user data
	private void setHPprogress() {
		if(currentUser.getMaxBossHp() != - 1) {
			int currBossHp = currentUser.getMaxBossHp() - currentUser.getBossDmg();
			int maxBossHp = currentUser.getMaxBossHp();
			double hpProgress = (double) currBossHp/maxBossHp;
			hpBar.setProgress(hpProgress);
			hpPointsLbl.setText(Integer.toString(currBossHp) + "/" + Integer.toString(maxBossHp));
		}
		else {
			hpBar.setProgress(0);
			hpPointsLbl.setText("Defeated!");
		}
	}
	
	// logic for setting xp points based on user data
	private void setXPprogress() {
		if(currentUser.getUserLvl() != - 1) {
			int userXp = currentUser.getUserXp();
			int maxXp = currentUser.getNextLvlXp();
			double xpProgress = (double) userXp/maxXp;
			xpBar.setProgress(xpProgress);
			xpPointsLbl.setText(Integer.toString(userXp) + "/" + Integer.toString(maxXp));
		}
		else {
			xpBar.setProgress(1);
			userLevel.setText(currentUser.getUserLvl());
		}
	} 
	*/
	
	private void disableTaskButtons() {
		viewTaskButton.setDisable(true);
		markCompleteButton.setDisable(true);
		removeTaskButton.setDisable(true);
		
	}
}
