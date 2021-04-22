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
   
	@FXML private void handleLogin(ActionEvent event) throws IOException {
		String tempUsername = usernameField.getLength() != 0 ? usernameField.getText(): null;
		String tempPassword = passwordField.getLength() != 0 ? passwordField.getText(): null;
		if(tempUsername != null && tempPassword != null){
			if(model.UserData.getInstance().getUser(tempUsername, tempPassword)) {
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
				a.setHeaderText("User not found!");
				a.setContentText("Please enter correct username and password credentials!");
				a.showAndWait();
				usernameField.clear();
				passwordField.clear();
			}
		} 
		else{
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login Error");
			a.setHeaderText("Empty Fields");
			a.setContentText("Please fill out all fields prior to submitting");
			a.showAndWait();
		}
	}
	@FXML private void quitToDesktop(ActionEvent event) throws IOException {
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

