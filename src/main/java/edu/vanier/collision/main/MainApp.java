package edu.vanier.collision.main;

import edu.vanier.collision.animation.DefaultAnimation;
import edu.vanier.collision.controllers.FXMLMainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/fxml/main_menu.fxml"));
        mainMenu.setController(new FXMLMainMenuController());
        Parent root = mainMenu.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);

        primaryStage.show();
    }

    @Override
    public void stop() {
        if(DefaultAnimation.isAnimationPlaying())
            DefaultAnimation.stop();
    }
}
