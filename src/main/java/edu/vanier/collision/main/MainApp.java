package edu.vanier.collision.main;


import edu.vanier.collision.controllers.FXMLAsteroidsController;
import edu.vanier.collision.controllers.FXMLDefaultController;
import edu.vanier.collision.controllers.FXMLMainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * starts animation
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainMenu = new FXMLLoader(getClass().getResource("/fxml/MainMenuPane.fxml"));
        mainMenu.setController(new FXMLMainMenuController());
        Parent root = mainMenu.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    /**
     * stops animation
     */
    @Override
    public void stop() {
        if(FXMLDefaultController.isPlaying())
            FXMLDefaultController.getAnimation().stop();
        if(FXMLAsteroidsController.isPlaying())
            FXMLAsteroidsController.getAnimation().stop();
    }
}
