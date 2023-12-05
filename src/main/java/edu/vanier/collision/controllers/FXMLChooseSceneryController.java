/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author 2276884
 */
public class FXMLChooseSceneryController {

    // FXML UI'S
    @FXML
    Button btnDefault;
    @FXML
    Button btnAsteroids;
    @FXML
    Button btnBack;

    /**
     * Initialize method for the Choose Scenery scene, can either choose default
     * animation, asteroid animation, or go back to the main menu.
     */
    @FXML
    void initialize() {
        btnDefault.setOnAction((event) -> {
            switchScene(new FXMLDefaultController(), "/fxml/defaultAnimationPane.fxml");
        });
        btnAsteroids.setOnAction((event) -> {
            switchScene(new FXMLAsteroidsController(), "/fxml/asteroidsAnimationPane.fxml");
        });
        btnBack.setOnAction((event) -> {
            switchScene(new FXMLMainMenuController(), "/fxml/MainMenuPane.fxml");
        });
    }

    /**
     * Switches scenes depending on the FXMLController object passed through.
     * @param controller The FXMLController object.
     * @param resourcePath The resource path of the FXML document.
     */
    public void switchScene(Object controller, String resourcePath) {
        try {
            Stage primaryStage = (Stage) btnDefault.getScene().getWindow();
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
