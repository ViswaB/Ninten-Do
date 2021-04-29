package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

/**
 * Creates dialogs for use throughout application
 * 
 */
public class DialogController {

	@FXML private Label errorMessage;
	@FXML private DialogPane errorDialog;
	
	/**
	 * Sets the dialog text areas based
	 * on passed in information
	 * 
	 * @param header
	 * @param message
	 */
	public void setUp(String header, String message) {
		errorDialog.setHeaderText(header);
		errorMessage.setText(message);
	}
}
