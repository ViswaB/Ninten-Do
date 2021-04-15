package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.IOException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    @FXML private PasswordField firstName;
    @FXML private PasswordField lastName;
    @FXML private PasswordField newUsername;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField confirmPassword;
   
	@FXML private void createNewAccount(ActionEvent event) throws IOException {
		
	}
	@FXML private void toLoginScr(ActionEvent event) throws IOException {
		loginScr = FXMLLoader.load(getClass().getResource("../scene/LoginScreen.fxml"));// pane you are GOING TO
		Scene scene = new Scene(loginScr);// pane you are GOING TO show
		//scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
		window.setScene(scene);
		window.show();
	}
	@FXML private void quittoDesktop2(ActionEvent event) throws IOException {
		//implement logout logic in Model class
		System.exit(0);
	}	
	
}
