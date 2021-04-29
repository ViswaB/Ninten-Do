package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserData;

/**
 * Controller for Register scene
 * Allows users to create new accounts
 *
 */
public class RegisterController {

	@FXML private Button Login;
	@FXML private Button quitApp2;
	@FXML private Button createNewButton;
    @FXML private Text newPass;
    @FXML private Text newUser;
    @FXML private Text firstN;
    @FXML private Text lastN;    
    @FXML private AnchorPane loginScr;
    @FXML private AnchorPane registerScr;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField newUsername;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField confirmPassword;
   
    /**
     * Handles logic for registering new user
     * Error checks many edge cases
     * 
     * @param event
     * @throws IOException
     */
	@FXML private void createNewAccount(ActionEvent event) throws IOException {
	
		String firstName1, lastName1, userName1, passWord1, confirmPassword1;
		int userID = 0;
		
		firstName1 = firstName.getLength() != 0 ? firstName.getText(): null;
		lastName1 = lastName.getLength() != 0 ? lastName.getText(): null;
		userName1 = newUsername.getLength() != 0 ? newUsername.getText(): null;
		passWord1 = newPassword.getLength() != 0 ? newPassword.getText(): null;
		confirmPassword1 = confirmPassword.getLength() != 0 ? confirmPassword.getText(): null;
		
		// Makes sure all fields are filled out before processing
		if(firstName1 != null && lastName1 != null && userName1 != null && passWord1  != null && confirmPassword1  != null) {
			
			// Makes sure passwords match
			if(passWord1.equals(confirmPassword1)) {
				
				// Gets next available User ID from application file
				Scanner scan = new Scanner(new File("resources/data/uids.txt"));
				userID = scan.nextInt();
				scan.close();
				userID++;
				
				// Writes next available User ID for next registration
				FileWriter fwrite = new FileWriter("resources/data/uids.txt");
				fwrite.write(Integer.toString(userID));
				fwrite.close();
				
				// Creates new User instance based on registration information
				// Checks if adding a user succeeded, if so, sends user to Home scene
				User user = new User(firstName1, lastName1, userName1, passWord1, userID);
				if(UserData.getInstance().addUser(user)) {
					Alert userCreated = new Alert(AlertType.NONE);
					userCreated.setAlertType(AlertType.CONFIRMATION);
					userCreated.setTitle("User added");
					userCreated.setHeaderText("User has been created!");
					userCreated.setContentText("Success! Your account has been created, sending you to the home screen!");
					userCreated.showAndWait();
					model.TaskData.getInstance().setFilename(model.UserData.getInstance().retrieveUser().getUserID());
					model.TaskData.getInstance().loadTasks();
					loginScr = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
					Scene scene = new Scene(loginScr);
					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
					window.setScene(scene);
					window.show();
				}
				else {
					
					// Adding user fails if User instance already
					// exists in the application database (file)
					// Notifies user if already exists
					Alert failedtoCreate = new Alert(AlertType.NONE);
					failedtoCreate.setAlertType(AlertType.ERROR);
					failedtoCreate.setTitle("ERROR");
					failedtoCreate.setHeaderText("Unable to create user!");
					failedtoCreate.setContentText("User already exists, please use unique credentials!");
					failedtoCreate.showAndWait();
					firstName.clear();
					lastName.clear();
					newUsername.clear();
					newPassword.clear();
					confirmPassword.clear();
				}
			}
			else {
				
				// If passwords don't match, alert user
				Alert passwordMismatch = new Alert(AlertType.NONE);
				passwordMismatch.setAlertType(AlertType.ERROR);
				passwordMismatch.setTitle("ERROR");
				passwordMismatch.setHeaderText("Unable to create user!");
				passwordMismatch.setContentText("Your passwords do not match, please check again!");
				passwordMismatch.showAndWait();
				newPassword.clear();
				confirmPassword.clear();
			}
		}
		else {
			// If any fields are left empty, alert user
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Login Error");
			a.setHeaderText("Empty Fields");
			a.setContentText("Please fill out all fields prior to submitting");
			a.showAndWait();
		}
	}
	
	/**
	 * Sends user back to login screen
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void toLoginScr(ActionEvent event) throws IOException {
		loginScr = FXMLLoader.load(getClass().getResource("../scene/LoginScreen.fxml"));
		Scene scene = new Scene(loginScr);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Quits application
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void quittoDesktop2(ActionEvent event) throws IOException {
		System.exit(0);
	}
}
