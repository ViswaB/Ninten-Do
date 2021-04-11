package controller;

import java.io.IOException;
import java.time.LocalDate;

import model.TaskData;
import model.TaskItem;

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
    
    @FXML private void toHome(ActionEvent event) throws IOException{
    	taskInputAnchor = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
    	Scene scene = new Scene(taskInputAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML private void processItem(ActionEvent event) {
    	if(checkFields()) {
    		String taskTitle = taskName.getText();
    		String taskDescription = taskDesc.getText();
    		LocalDate taskDeadline = taskDate.getValue() != null ? taskDate.getValue() : LocalDate.now();
    		TaskData.getInstance().addTask(new TaskItem(taskTitle, taskDescription, taskDeadline));
    		if(!showDialog("Process Success", "Successfully added Task")) {
    			return;
    		}
    		taskName.clear();
    		taskDesc.clear();
    		taskDate.setValue(null);
    	}else {
    		if(!showDialog("Error processing data", "Fill out 'Task Name' and 'Task Description' and try again.")) {
    			return;
    		}
    	}
    }
    
    private boolean checkFields() {
    	return (taskName.getLength() != 0 ? true : false) && (taskDesc.getLength() != 0 ? true : false);
    }
    
    private boolean showDialog(String header, String content) {
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
}