/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.ui.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author 2276884
 */
public class MainAppController {

    @FXML
    Button btnChoose;

    @FXML
    void initialize() {
        btnChoose.setOnAction(event -> {
            // gets the primary stage from the chooseScenery button
            Stage primaryStage = (Stage) btnChoose.getScene().getWindow();
            FXMLLoader chooseScenery = new FXMLLoader(getClass().getResource("/fxml/choose_scenery.fxml"));
            chooseScenery.setController(new ChooseSceneryController());
            try {
                Scene chooseSceneryScene = new Scene(chooseScenery.load());
                switchScenes(primaryStage, chooseSceneryScene);
            } catch (IOException ex) {
                Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void switchScenes(Stage stage, Scene scene){
        stage.setScene(scene);
    }

}
