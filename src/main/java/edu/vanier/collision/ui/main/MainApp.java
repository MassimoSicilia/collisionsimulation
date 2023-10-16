package edu.vanier.collision.ui.main;

import edu.vanier.collision.ui.controller.MainAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

 
public class MainApp extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/fxml/main_menu.fxml"));
        mainMenu.setController(new MainAppController());
        Parent root = mainMenu.load();
        Scene scene = new Scene (root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
}
