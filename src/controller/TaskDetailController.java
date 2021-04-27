package controller;

import model.TaskItem;
//import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application TaskDetailController.java
 * is created in order to set the logic, scenes, functions and calls needed to allow users to view and update
 * the tasks for the Ninten-Do application.
 * 
 *
 */
public class TaskDetailController {
	@FXML private TextField taskTitle;
	@FXML private TextArea taskDescription;
	@FXML private TextField taskDate;
	@FXML private CheckBox completedCheckbox;
	@FXML private Button homeButton;
	@FXML private AnchorPane mainAnchor;
	
	private TaskItem selectedItem;
	
	public TaskDetailController(TaskItem selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	@FXML private void toHome(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).close();
	}
	
	public void initialize() {
		//initializes the data and implements the logic for the task data also runs for when tasks are completed.
		taskTitle.setText(this.selectedItem.getShortDesc());
		taskTitle.setEditable(false);
		
		taskDescription.setText(this.selectedItem.getlongDesc());
		taskDescription.setEditable(false);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
		
		taskDate.setText(df.format(this.selectedItem.getDeadline()));
		taskDate.setEditable(false);
		
		if(this.selectedItem.getCompleted()) {
			completedCheckbox.setSelected(true);
		}
		completedCheckbox.setDisable(true);
	}

}
