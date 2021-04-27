package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.LevelsData;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
/**
 * The following project Ninten-Do is an application that allows users to input tasks they must complete in a game like manner.
 * The goal of the application is to make daily chores fun and engaging for the user. The java application Main.java
 * is created in order to set the logic, scenes, functions and calls needed to set the primary stage of the application.
 * 
 *
 */

public class Main extends Application {
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
	//Login Screen scene for the Application.
	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	
	@Override
	public void init() throws IOException {
		model.UserData.getInstance().loadUsers();
		LevelsData.getInstance().loadLevels();
	}
	/**calls model for userData and LevelsData
*/
	@Override
	public void stop() throws IOException{
		model.UserData.getInstance().saveUsers();
	}
	
	//calls to stop linked to model
}
