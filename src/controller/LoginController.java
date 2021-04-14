package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

	@FXML private Button Login;
	@FXML private Button quitApp;
	@FXML private Button createNew;
	@FXML private PasswordField password;
	@FXML private PasswordField username;
    @FXML private TextField passWord;
    @FXML private TextField userName;
    @FXML private AnchorPane loginScr;
    @FXML private AnchorPane registerScr;
   
	@FXML private void handleLogin(ActionEvent event) throws IOException {
		//logic to check if input was made in text fields
		/*
		if(model.UserData.getUser(username, password)) {
			registerScr= FXMLLoader.load(getClass().getResource("../scene/homeScr.fxml"));
			Scene scene = new Scene(registerScr);
			scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} 
		else{
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login Error");
			a.setHeaderText("Empty Fields");
			a.setContentText("Please fill out all fields prior to submitting");
			a.showAndWait();
		}
		
		
		*/
		registerScr= FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
		Scene scene = new Scene(registerScr);
		//scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		

	}
	@FXML private void quitToDesktop(ActionEvent event) throws IOException {
		//implement logout logic in Model class
		System.exit(0);
	}	
	
	@FXML private void toRegisterScr(ActionEvent event) throws IOException {
		registerScr= FXMLLoader.load(getClass().getResource("../scene/RegisterScreen.fxml"));
		Scene scene = new Scene(registerScr);
		//scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}
}

