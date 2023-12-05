/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.model.Simulation;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author 2276884
 */
public class FXMLMainMenuController {

    @FXML
    Button btnChoose;
    @FXML
    Button btnLoad;
    @FXML
    Button btnExit;

    /**
     * Initializes the FXMLMainMenuController and the event handlers.
     */
    @FXML
    public void initialize() {
        btnChoose.setOnAction(event -> {
            switchScene(new FXMLChooseSceneryController(), "/fxml/ChooseSceneryPane.fxml");
        });

        btnLoad.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) btnLoad.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Saved Scenery");
                // will only accept json files
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON File", "*.json"));
                File sceneryToLoad = fileChooser.showOpenDialog((Stage) btnLoad.getScene().getWindow());
                Simulation simulationToLoad = SimulationController.load(sceneryToLoad);
                if (simulationToLoad == null) {
                    Alert error = new Alert(AlertType.ERROR, "The JSON file is invalid.");
                    error.showAndWait();
                }
                if (simulationToLoad.isDefault()) {
                    switchScene(new FXMLDefaultController(simulationToLoad.getProjectiles(), simulationToLoad.getElasticity()), "/fxml/defaultAnimationPane.fxml");
                } else {
                    switchScene(new FXMLAsteroidsController(simulationToLoad.getProjectiles()), "/fxml/asteroidsAnimationPane.fxml");
                }
            } catch (Exception ex) {

            }
        });

        btnExit.setOnAction(event -> {
            javafx.application.Platform.exit();
        });
    }

    ;

    /**
     * Switches scenes depending on the FXMLController object passed through.
     * @param controller The FXMLController object.
     * @param resourcePath The resource path of the FXML document.
     */
    public void switchScene(Object controller, String resourcePath) {
        try {
            Stage primaryStage = (Stage) btnChoose.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDefaultController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
