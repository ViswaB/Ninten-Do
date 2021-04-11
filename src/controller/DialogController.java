package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class DialogController {

	@FXML private Label errorMessage;
	@FXML private DialogPane errorDialog;
	
	public void setUp(String header, String message) {
		errorDialog.setHeaderText(header);
		errorMessage.setText(message);
	}
}
