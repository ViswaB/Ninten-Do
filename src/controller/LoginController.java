package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.IOException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application LoginController.java
 * is created in order to set the logic, scenes, functions and calls needed to allow users to log into the Ninten-Do application.
 * 
 *
 */
public class LoginController {

	@FXML private Button Login;
	@FXML private Button quitApp;
	@FXML private Button createNew;
	@FXML private PasswordField passwordField;
	@FXML private TextField usernameField;
    @FXML private Text passWord;
    @FXML private Text userName;
    @FXML private AnchorPane loginScr;
    @FXML private AnchorPane registerScr;
    //set the private variable used for logging in
   
	@FXML private void handleLogin(ActionEvent event) throws IOException {
		//function used to create the user name, password, store it and set the scene 
		String tempUsername = usernameField.getLength() != 0 ? usernameField.getText(): null;
		String tempPassword = passwordField.getLength() != 0 ? passwordField.getText(): null;
		if(tempUsername != null && tempPassword != null){
			if(model.UserData.getInstance().getUser(tempUsername, tempPassword)) {
				model.TaskData.getInstance().setFilename(model.UserData.getInstance().retrieveUser().getUserID());
				model.TaskData.getInstance().loadTasks();
				registerScr= FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
				Scene scene = new Scene(registerScr);
				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
				window.setScene(scene);
				window.show();
			}
			else{
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Login Error");
				a.setHeaderText("User not found!");
				a.setContentText("Please enter correct username and password credentials!");
				a.showAndWait();
				usernameField.clear();
				passwordField.clear();
			}
			//checks to make sure user name and password exists
		} 
		else{
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login Error");
			a.setHeaderText("Empty Fields");
			a.setContentText("Please fill out all fields prior to submitting");
			a.showAndWait();
		}
		//verifies users fills out all necessary information needed
	}
	@FXML private void quitToDesktop(ActionEvent event) throws IOException {
		System.exit(0);
	}	
	//function created to be able to quit the application
	
	@FXML private void toRegisterScr(ActionEvent event) throws IOException {
		registerScr= FXMLLoader.load(getClass().getResource("../scene/RegisterScreen.fxml"));
		Scene scene = new Scene(registerScr);
		//scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}
	//registration screen function
}

