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
