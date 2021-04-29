package controller;

import java.io.IOException;
import java.time.LocalDate;

import model.TaskData;
import model.TaskItem;
import model.User;
import model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Controller for TaskInput scene
 * Allows user to add tasks to be completed
 *
 */
public class TaskInputController {

    @FXML private ProgressBar xpProgress;
    @FXML private RadioButton diamondRadio;
    @FXML private Button addButton;
    @FXML private Label expLabel;
    @FXML private Label hpLabel;
    @FXML private Button homeButton;
    @FXML private TextArea taskDesc;
    @FXML private RadioButton bronzeRadio;
    @FXML private AnchorPane taskInputAnchor;
    @FXML private ToggleGroup difficultyPicker;
    @FXML private DatePicker taskDate;
    @FXML private TextField taskName;
    @FXML private RadioButton goldRadio;
    @FXML private RadioButton silverRadio;
    @FXML private ProgressBar hpProgess;
    @FXML private Label userLevel;
    
    private User currentUser;
    
    /**
     * Sends the user back to Home scene
     * 
     * @param event
     * @throws IOException
     */
    @FXML private void toHome(ActionEvent event) throws IOException{
    	//changes scene to the home scene
    	taskInputAnchor = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
    	Scene scene = new Scene(taskInputAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }
    
    /**
     * Processes given task information
     * Creates TaskItem instance for storing
     * 
     * @param event
     */
    @FXML private void processItem(ActionEvent event) {
    	if(checkFields()) {
    		String taskTitle = taskName.getText();
    		String taskDescription = taskDesc.getText();
    		LocalDate taskDeadline = taskDate.getValue() != null ? taskDate.getValue() : LocalDate.now();
    		TaskData.getInstance().addTask(new TaskItem(taskTitle, taskDescription, taskDeadline));
    		UserData.getInstance().retrieveUser().setMaxBossHp(0);
    		
    		// Shows user Success message
    		// If check fails, still adds task, but doesn't notify
    		if(!showDialog("Process Success", "Successfully added Task")) {
    			return;
    		}
    		taskName.clear();
    		taskDesc.clear();
    		taskDate.setValue(null);
    	}else {
    		// if fields are empty (date field is the exception)
    		// displays error message
    		if(!showDialog("Error processing data", "Fill out 'Task Name' and 'Task Description' and try again.")) {
    			return;
    		}
    	}
    }
    
    /**
     * Checks to see if Task Name and Task Description are empty
     * Returns true if both fields are filled out, false if either is empty
     * Doesn't check Date field, if user doesn't select date, uses current date
     * 
     * @return
     */
    private boolean checkFields() {
    	return (taskName.getLength() != 0 ? true : false) && (taskDesc.getLength() != 0 ? true : false);
    }
    
    /**
     * Helper function to show dialog
     * 
     * @param header
     * @param content
     * @return
     */
    private boolean showDialog(String header, String content) {
    	//function used to call string for the header and task content also changes scenes to 'ErrorDialog'.
    	Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(taskInputAnchor.getScene().getWindow());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/ErrorDialog.fxml"));
		
		try {
			dialog.getDialogPane().setContent(loader.load());
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		DialogController controller = loader.getController();
		controller.setUp(header, content);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.showAndWait();
		return true;
    }
    
    /**
	 * Called before the scene gets displayed
	 * Java compiler follows specific path
	 * -> Call to Class Constructor
	 * -> Initializes all @FXML annotated variables and function
	 * -> Calls initialize() function if exists
	 * 
	 * Retrieves current logged in user
	 * Sets boss HP and User XP progress bars
	 */
    public void initialize() {
    	currentUser = model.UserData.getInstance().retrieveUser();
    	setHPprogress();
    	setXPprogress();
    }
    
	/**
	 * Calculates boss HP and damage done to boss
	 * Damage is determined by marking tasks as complete
	 */
	private void setHPprogress() {
		if (currentUser.getMaxBossHp() != -1) {
			int currBossHp = currentUser.getMaxBossHp() - currentUser.getBossDmg();
			int maxBossHp = currentUser.getMaxBossHp();
			double hpProgressPts = (double) currBossHp / maxBossHp;
			if(hpProgressPts > 0) {
				hpProgess.setProgress(hpProgressPts);
				hpLabel.setText(Integer.toString(currBossHp) + "/" + Integer.toString(maxBossHp));
			}
		} else {
			hpProgess.setProgress(0);
			hpLabel.setText("Defeated!");
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
			double xpProgresss = (double) userXp / maxXp;
			xpProgress.setProgress(xpProgresss);
			expLabel.setText(Integer.toString(userXp) + "/" + Integer.toString(maxXp));
		} else {
			xpProgress.setProgress(1);
			expLabel.setText("MAX");

		}
	}
}