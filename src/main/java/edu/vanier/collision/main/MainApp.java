package edu.vanier.collision.main;

import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.simulation.CollisionBox;
import edu.vanier.collision.simulation.PaneCollisionBox;
import edu.vanier.collision.simulation.Simulation;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainApp extends Application {

    @FXML
    private Pane collisionBox;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Rectangle collisionRectangle;

    @FXML
    private Pane ballPane; // Inject ballPane

    private Simulation simulation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/default_layout.fxml"));
            loader.setController(this); // Set the controller class here
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);

            // Use PaneCollisionBox as the collision box
            CollisionBox paneCollisionBox = new PaneCollisionBox();

            simulation = new Simulation(paneCollisionBox);

            btnAdd.setOnAction(event -> simulation.addProjectile());
            btnRemove.setOnAction(event -> simulation.removeProjectile());

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
