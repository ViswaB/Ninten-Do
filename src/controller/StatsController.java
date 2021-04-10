package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsController {

    @FXML private Label CurrentTasks;
    @FXML private Label UserStats;
    @FXML private Label Points;
    @FXML private ProgressBar p3;
    @FXML private Label WeeklyTasks;
    @FXML private Label Level;
    @FXML private Label MonthlyTasks;
    @FXML private Button Home;
    @FXML private AnchorPane mainAnchor;
    
    @FXML private void toHome(ActionEvent event) throws IOException {
    	mainAnchor = FXMLLoader.load(getClass().getResource("../scene/Home.fxml"));
    	Scene scene = new Scene(mainAnchor);
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    }
}
