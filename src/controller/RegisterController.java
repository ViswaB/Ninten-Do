package controller;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

	@FXML private Button Login;
	@FXML private Button Quittodesktop;
	@FXML private Button Createnewaccount;
    @FXML private TextField Password;
    @FXML private TextField userName;
    @FXML private AnchorPane loginScr;
    @FXML private AnchorPane registerScr;
   
	
	private void toLoginScr(ActionEvent event) throws IOException {
		loginScr = FXMLLoader.load(getClass().getResource("../model/registerScr.fxml"));// pane you are GOING TO
		Scene scene = new Scene(loginScr);// pane you are GOING TO show
		scene.getStylesheets().add(getClass().getResource("../../resources/css/loginScr.css").toExternalForm());
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// pane you are ON
		window.setScene(scene);
		window.show();
	}
}
