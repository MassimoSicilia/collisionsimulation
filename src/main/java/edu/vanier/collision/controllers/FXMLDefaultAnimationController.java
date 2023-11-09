/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.animation.defaultAnimation;
import edu.vanier.collision.model.CircleProjectile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Hassimo
 */
public class FXMLDefaultAnimationController {
    
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
    TextField txtObjectCount;
    int circleCounter = 0;
    
    @FXML
    public void initialize() {
        enablePlayBtn();
        btnAdd.setOnAction((event) -> {
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            CircleProjectile addedCircle = new CircleProjectile(10, 1, Math.random() * 10, Math.random() * 10, 20, 40);
            circles.add(addedCircle);
            animationPane.getChildren().add(addedCircle.getShape());
            addCounter();
        });
        btnReturn.setOnAction((event) -> {
            try {
                FXMLLoader returnLoader = new FXMLLoader(getClass().getResource("/fxml/choose_scenery.fxml"));
                returnLoader.setController(new FXMLChooseSceneryController());
                Parent root = returnLoader.load();
                btnReturn.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnPlay.setOnAction((event) -> {
            disablePlayBtn();
            defaultAnimation.setComponents(circles, animationPane);
            defaultAnimation.play();
        });
        btnPause.setOnAction((event) -> {
            defaultAnimation.pauseAnimation();
            enablePlayBtn();
        });
        btnReset.setOnAction((event) -> {
            animationPane.getChildren().remove(1, circles.size() + 1); // the first element is the rectangle border
            circles.removeAll(circles);
            
            // if the animation is playing, pause it
            if (btnPlay.disabledProperty().getValue() == true) {
                defaultAnimation.pauseAnimation();
                enablePlayBtn();
                circleCounter = 0;
                txtObjectCount.setText("Circles: " + circleCounter);
            }
        });
        
        btnRemove.setOnAction((event) -> {
            
            btnRemove.setDisable(false);
            if (animationPane.getChildren().size() == 1) {
                btnRemove.setDisable(true);
            } else {
                animationPane.getChildren().remove(circles.size());
                circles.remove(circles.size() - 1);
                substractCounter();
//                if(circleCounter == 0){
//                    btnRemove.setDisable(true);
//                }else{
//                    
//                }
            }
            
        });
        
        
    }

    public void disablePlayBtn() {
        btnPlay.setDisable(true);
        btnPause.setDisable(false);
        btnReset.setDisable(false);
    }
    
    public void enablePlayBtn() {
        btnPlay.setDisable(false);
        btnPause.setDisable(true);
        if (circles.isEmpty()) {
            btnReset.setDisable(true);
        } else {
            btnReset.setDisable(false);
        }
    }
    
    private void addCounter() {
        circleCounter++;
        txtObjectCount.setText("Circles: " + circleCounter);
    }
    private void substractCounter() {
        circleCounter--;
        txtObjectCount.setText("Circles: " + circleCounter);
    }
}
