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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author 2276884
 */
public class ChooseSceneryController {
    @FXML
    Button btnDefault;
    

    @FXML
    void initialize() {
        btnDefault.setOnAction((event) -> {
            try {
                Stage primaryStage = (Stage)btnDefault.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/defaultAnimationPane.fxml"));
                loader.setController(new DefaultController());
                Pane root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(ChooseSceneryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
