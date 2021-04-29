package application;

/**
 * The following project Ninten-Do is an application that allows users 
 * to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user.
 * 
 *  @author Filiberto Rios (Team Lead)
 *  @author Alex Bush
 *  @author Lucas Overbey
 *  @author Viswa Bhargavi
 *  @author Megan Garza
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.LevelsData;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * Main application class for the project
 * Sets up the Main scene to be used throughout application
 */
public class Main extends Application {
	
	/**
	 * Sets the login screen as the first page displayed
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../scene/LoginScreen.fxml"));
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * Calls Singleton classes to populate required data models
	 * Called before the main application process runs allowing 
	 * for data to be loaded for immediate access within application
	 */
	@Override
	public void init() throws IOException {
		model.UserData.getInstance().loadUsers();
		LevelsData.getInstance().loadLevels();
	}
	
	/**
	 * Called when application ends normally
	 * Used to save User data for next run of
	 * the application
	 */
	@Override
	public void stop() throws IOException{
		model.UserData.getInstance().saveUsers();
	}
	
	//calls to stop linked to model
}
