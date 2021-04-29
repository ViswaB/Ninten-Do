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
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * Controller for the main program
 * allowing for navigation to different
 * components of the application
 *
 */
public class HomeController {
	@FXML private Button manageTasksBtn, statsBtn, logoutBtn, quitBtn;
	@FXML private Button viewTaskButton, markCompleteButton, removeTaskButton, removeAllButton;
	@FXML private Label welcomeLbl, levelLbl, xpLbl, bossHpLbl, taskListLbl, totalTasksLbl;
	@FXML private Label userLevel, xpPointsLbl, hpPointsLbl;
	@FXML private TextField nameUser, numT;
	@FXML private ProgressBar xpBar, hpBar;
	@FXML private ListView<TaskItem> taskListView;
	@FXML private Pane title, topScr, bottomScr;
	@FXML private AnchorPane homeScr;
	@FXML private FXMLLoader loader;

	private User currentUser;
	
	/**
	 * Loads the TaskInput scene
	 * sends the user to TaskInput
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void toTaskInput(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/TaskInput.fxml"));
		homeScr = loader.load(); 
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Loads the UserStats scene
	 * sending the user to review their stats
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void toUserStats(ActionEvent event) throws IOException {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/Stats.fxml"));
		homeScr = loader.load();
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Logs out of the application
	 * Saves all data before login out
	 * Calls Platform.exit() instead of System.exit()
	 * Doing so allows the JavaFX process to call stop() function in Main.java
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void logoutAndExit(ActionEvent event) throws IOException {
		UserData.getInstance().updateUser();
		TaskData.getInstance().saveTasks();
		Platform.exit();
	}

	/**
	 * Logs out the current user and send back to login page
	 * Saves user specific information for next login
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void logout(ActionEvent event) throws IOException {
		TaskData.getInstance().saveTasks();
		UserData.getInstance().updateUser();
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../scene/LoginScreen.fxml"));
		homeScr = loader.load();
		//change scene to login screen/home screen
		Scene scene = new Scene(homeScr);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Gets the currently selected task in the ListView
	 * and passes it to TaskDetailController to populate
	 * the required fields with required information about
	 * the selected task
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void toTaskDetails(ActionEvent event) throws IOException {
		TaskItem task = taskListView.getSelectionModel().getSelectedItem();
		if (task != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/TaskDetails.fxml"));
			
			TaskDetailController controller = new TaskDetailController(task);
			loader.setController(controller);
			
			Scene scene = new Scene(loader.load());
			Stage stage = new Stage();
			stage.setTitle("Task Details");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initStyle(StageStyle.UTILITY);
			homeScr.setDisable(true);
			stage.showAndWait();
			homeScr.setDisable(false);
		}
	}

	/**
	 * Enabled buttons once the user selects a task
	 * from the ListView
	 */
	@FXML
	private void enableTaskButtons() {
		viewTaskButton.setDisable(false);
		markCompleteButton.setDisable(false);
		removeTaskButton.setDisable(false);
	}

	/**
	 * Removes all tasks associated with the user
	 * 
	 * @param event
	 */
	@FXML
	private void clearTasks(ActionEvent event) {
		TaskData.getInstance().clearTasks();
		currentUser.clearTasks();
	}

	/**
	 * Removes selected task from task list
	 * and from user's completed task list
	 * Doing so allows for proper recalculation
	 * of boss HP
	 * 
	 * @param event
	 */
	@FXML
	private void removeTask(ActionEvent event) {
		TaskItem task = taskListView.getSelectionModel().getSelectedItem();
		if(task != null) {
			TaskData.getInstance().removeTask(task);
			currentUser.removeTask(task);
		}
	}

	/**
	 * Marks selected task complete
	 * Upon marking a task as complete
	 * the user gets awarded XP based
	 * on internal task rank
	 * 
	 * @param event
	 */
	@FXML
	private void markComplete(ActionEvent event) {
		TaskItem task = taskListView.getSelectionModel().getSelectedItem();
		if(task != null && !task.getCompleted()) {
			TaskData.getInstance().markComplete(task);
			UserData.getInstance().retrieveUser().setCompletedTask(task);
			setXPprogress();
			setHPprogress();
		}
	}

	/**
	 * Called before the scene gets displayed
	 * Java compiler follows specific path
	 * -> Call to Class Constructor
	 * -> Initializes all @FXML annotated variables and function
	 * -> Calls initialize() function if exists
	 * 
	 * Gets the user that logged into the application
	 * Used to set up ListView loaded with user specific tasks
	 * Calculates User's boss HP and User's XP
	 * Recalculates boss HP and User XP every time Home scene is loaded
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void initialize() throws ClassNotFoundException, IOException {
		currentUser = UserData.getInstance().retrieveUser();
		nameUser.setText(currentUser.toString());
		taskListView.setItems(TaskData.getInstance().getTasks());
		taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		/**
		 * CellFactory is used to customize ListView entries
		 * In this case, the Task text color is changed
		 * depending on the internal task rank 
		 */
		taskListView.setCellFactory(new Callback<ListView<TaskItem>, ListCell<TaskItem>>() {
			@Override
			public ListCell<TaskItem> call(ListView<TaskItem> param) {
				ListCell<TaskItem> cell = new ListCell<TaskItem>() {
					@Override
					protected void updateItem(TaskItem item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(item.getShortDesc());
							int itemRank = item.getRank();
							if (itemRank == 500) {
								setTextFill(Color.DARKRED);
							} else if (itemRank >= 200 && itemRank <= 280) {
								setTextFill(Color.DARKGREEN);
							} else if (itemRank >= 300 && itemRank <= 380) {
								setTextFill(Color.ORANGE);
							} else if (itemRank >= 400 && itemRank <= 480) {
								setTextFill(Color.ORANGERED);
							} else {
								setTextFill(Color.BLACK);
							}
						}
					}
				};
				return cell;
			}
		});
 
		model.UserData.getInstance().retrieveUser().setBossHpStats(TaskData.getInstance().getTasks());
		setXPprogress();
		setHPprogress();

		disableTaskButtons();
	}

	/**
	 * Calculates boss HP and damage done to boss
	 * Damage is determined by marking tasks as complete
	 */
	private void setHPprogress() {
		if (currentUser.getMaxBossHp() != -1) {
			int currBossHp = currentUser.getMaxBossHp() - currentUser.getBossDmg();
			int maxBossHp = currentUser.getMaxBossHp();
			double hpProgress = (double) currBossHp / maxBossHp;
			if(hpProgress > 0) {
				hpBar.setProgress(hpProgress);
				hpPointsLbl.setText(Integer.toString(currBossHp) + "/" + Integer.toString(maxBossHp));
			}
		} else {
			hpBar.setProgress(0);
			hpPointsLbl.setText("Defeated!");
		}
	}

	/**
	 * Calculates user XP stats
	 * XP is awarded upon task completion
	 */
	private void setXPprogress() {
		userLevel.setText(Integer.toString(currentUser.getUserLvl()));
		if (currentUser.getUserLvl() != -1) {
			int userXp = currentUser.getUserXp();
			int maxXp = currentUser.getNextLvlXp();
			double xpProgress = (double) userXp / maxXp;
			xpBar.setProgress(xpProgress);
			xpPointsLbl.setText(Integer.toString(userXp) + "/" + Integer.toString(maxXp));
		} else {
			xpBar.setProgress(1);
			userLevel.setText("MAX");
		}
	}

	/**
	 * Disables View Task, Mark Complete, and Remove Task
	 * buttons upon loading the Home scene
	 */
	private void disableTaskButtons() {
		viewTaskButton.setDisable(true);
		markCompleteButton.setDisable(true);
		removeTaskButton.setDisable(true);
	}
}
