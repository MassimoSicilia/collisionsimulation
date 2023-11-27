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
import javafx.scene.Scene;
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

    @FXML
    public void initialize() {
        btnChoose.setOnAction(event -> {
            // gets the primary stage from the chooseScenery button
            Stage primaryStage = (Stage) btnChoose.getScene().getWindow();
            FXMLLoader chooseScenery = new FXMLLoader(getClass().getResource("/fxml/choose_scenery.fxml"));
            chooseScenery.setController(new FXMLChooseSceneryController());
            try {
                Scene chooseSceneryScene = new Scene(chooseScenery.load());
                switchScenes(primaryStage, chooseSceneryScene);
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnLoad.setOnAction(event -> {
            Stage primaryStage = (Stage) btnLoad.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Saved Scenery");
            // will only accept json files
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON File", "*.json"));
            File sceneryToLoad = fileChooser.showOpenDialog((Stage) btnLoad.getScene().getWindow());
            try {
                Simulation simulationToLoad = SimulationController.load(sceneryToLoad);
                FXMLLoader loader = new FXMLLoader();
                if (simulationToLoad.isDefault()) {
                    loader.setLocation(getClass().getResource("/fxml/defaultAnimationPane.fxml"));
                    FXMLDefaultAnimationController controller = new FXMLDefaultAnimationController();
                    controller.circles = simulationToLoad.getProjectiles();
                    loader.setController(controller);
                    switchScenes(primaryStage, new Scene(loader.load()));
                }else{
                    
                }

                // to finish implementation after designing all scenes
            } catch (IOException ex) {
                Logger.getLogger(FXMLMainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnExit.setOnAction(event -> {
            javafx.application.Platform.exit();
        });
    }

    public void switchScenes(Stage stage, Scene scene) {
        stage.setScene(scene);
    }

}
