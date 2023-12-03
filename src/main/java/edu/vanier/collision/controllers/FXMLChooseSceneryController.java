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

    @FXML
    Button btnDefault;
    @FXML
    Button btnAsteroids;
    @FXML
    Button btnBack;

    @FXML
    void initialize() {
        btnDefault.setOnAction((event) -> {
            try {
                Stage primaryStage = (Stage) btnDefault.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/defaultAnimationPane.fxml"));
                loader.setController(new FXMLDefaultController());
                SplitPane root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(FXMLChooseSceneryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAsteroids.setOnAction((event) -> {
            try {
                Stage primaryStage = (Stage) btnAsteroids.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/asteroidsAnimationPane.fxml"));
                loader.setController(new FXMLAsteroidsController());
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                Logger.getLogger(FXMLAsteroidsController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        btnBack.setOnAction((event) -> {
            try {
                Stage primaryStage = (Stage) btnBack.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuPane.fxml"));
                loader.setController(new FXMLMainMenuController());
                Pane root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                Logger.getLogger(FXMLMainMenuController.class.getName()).log(Level.SEVERE, null, e);
            }
        });

    }
}
