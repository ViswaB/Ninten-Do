package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application DialogController.java
 * is created in order to set the  functions and calls needed to perform the Dialog of the application.
 * 
 *
 */
public class DialogController {

	@FXML private Label errorMessage;
	@FXML private DialogPane errorDialog;
	
	public void setUp(String header, String message) {
		errorDialog.setHeaderText(header);
		errorMessage.setText(message);
	}
	//call setup to string header and message
}
