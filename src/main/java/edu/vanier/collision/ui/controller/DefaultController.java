/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.ui.controller;

import edu.vanier.collision.animation.defaultAnimation;
import edu.vanier.collision.model.CircleProjectile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Hassimo
 */
public class DefaultController {

    List<CircleProjectile> circles = new ArrayList<>();
    @FXML
    Button btnAdd;
    @FXML
    Button btnRemove;
    @FXML
    Button btnPlay;
    @FXML
    Button btnPause;
    @FXML
    Button btnReset;
    @FXML
    Button btnMute;
    @FXML
    Button btnHide;
    @FXML
    Button btnReturn;

    //layouts
    @FXML
    Pane animationPane;
    @FXML
    Pane bottomPane;
    @FXML
    HBox HBoxTop;

    @FXML
    public void initialize() {
        btnAdd.setOnAction((event) -> {
            CircleProjectile addedCircle = new CircleProjectile(10, 1, Math.random() * 10, Math.random() * 10, 20, 40);
            circles.add(addedCircle);
            animationPane.getChildren().add(addedCircle.getShape());
        });
        btnReturn.setOnAction((event) -> {
            try {
                FXMLLoader returnLoader = new FXMLLoader(getClass().getResource("/fxml/choose_scenery.fxml"));
                returnLoader.setController(new ChooseSceneryController());
                Parent root = returnLoader.load();
                btnReturn.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(DefaultController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnPlay.setOnAction((event) -> {
            defaultAnimation.setComponents(circles, animationPane);
            defaultAnimation.play();
        });
    }

}
