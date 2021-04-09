package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    	taskInputAnchor = FXMLLoader.load(getClass().getResource("Stats.fxml"));
    	Scene scene = new Scene(taskInputAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }

}