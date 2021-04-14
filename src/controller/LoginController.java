package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

	@FXML private Button Login;
	@FXML private Button Quittodesktop;
	@FXML private Button Createnewaccount;
    @FXML private TextField Password;
    @FXML private TextField userName;
    @FXML private AnchorPane loginScr;
    @FXML private AnchorPane registerScr;
   
    public void toHomeScr(ActionEvent event) throws IOException {
    	
    }
	public void toRegisterScr(ActionEvent event) throws IOException {
		registerScr= FXMLLoader.load(getClass().getResource("../view/registerScr.fxml"));
		Scene scene = new Scene(registerScr);
		scene.getStylesheets().add(getClass().getResource("../view/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}
}

