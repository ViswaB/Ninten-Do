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
 * Controller class for Login scene
 * Implements all logic required for
 * logging in a user
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
   
    /**
     * Checks required fields before login user in
     * Once user is authenticated, sets up necessary files
     * for the User's tasks and stats
     * 
     * @param event
     * @throws IOException
     */
	@FXML private void handleLogin(ActionEvent event) throws IOException {
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
		} 
		else{
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login Error");
			a.setHeaderText("Empty Fields");
			a.setContentText("Please fill out all fields prior to submitting");
			a.showAndWait();
		}
	}
	
	/**
	 * Exits using System.exit() instead of Platform.exit()
	 * Platform.exit() is not required since no data is loaded/modified 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void quitToDesktop(ActionEvent event) throws IOException {
		System.exit(0);
	}	
	
	/**
	 * Sends users to register new accounts
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void toRegisterScr(ActionEvent event) throws IOException {
		registerScr= FXMLLoader.load(getClass().getResource("../scene/RegisterScreen.fxml"));
		Scene scene = new Scene(registerScr);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}
}

