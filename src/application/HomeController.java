/*
 * Name: Viswa Bhargavi
 * UTSA ID: vab753
 * Course: CS 3443.001 - Application Programming
 * Professor: Heena Rathore
 * Team Project: Ninten-Do
 * HomeController class
 */

package home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class HomeController {
	@FXML private Button task, stats, goals, logout, quit;
	@FXML private Label welcome, name, xp, xpPoints, bossHP, hpPoints, taskList, totalT;
	@FXML private TextField user, numT;
	@FXML private ProgressBar xpBar, hpBar;
	@FXML private ListView<String> taskListView;
	@FXML private Pane title, topScr, bottomScr;
	@FXML private AnchorPane homeScr;
}
