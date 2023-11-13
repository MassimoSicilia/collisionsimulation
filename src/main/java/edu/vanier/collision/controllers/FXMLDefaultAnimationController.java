/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.collision.animation.defaultAnimation;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.model.Simulation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Hassimo
 */
public class FXMLDefaultAnimationController extends Simulation{
    List<Projectile> circles = new ArrayList<>();
    
    // buttons
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
    @FXML
    Button btnSave;

    //layouts
    @FXML
    Pane animationPane;
    @FXML
    Pane bottomPane;
    @FXML
    HBox HBoxTop;
    @FXML
    Pane PaneContainer;

    @FXML
    TextField txtObjectCount;
    int circleCounter = 0;
    
    private boolean elasticity = true;

    @FXML
    public void initialize() {
        enablePlayBtn();
        for(int i = 0;i < circles.size();i++){
            animationPane.getChildren().add(circles.get(i).getCircle());
        }
        
        btnAdd.setOnAction((event) -> {
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            Projectile addedCircle = new Projectile(10, Math.random()* 10, Math.random() * 10, 20, 40, Color.color(Math.random(), Math.random(), Math.random()), 10);
            circles.add(addedCircle);
            animationPane.getChildren().add(addedCircle.getCircle());
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

        btnHide.setOnAction((event) -> {
            if (btnHide.getText().equals("Hide")) {
                btnHide.setText("Show");
                for (Node node : PaneContainer.getChildren()) {
                    if (node != btnHide) {
                        node.setVisible(false);
                    }
                }

            } else {
                btnHide.setText("Hide");
                for (Node node : PaneContainer.getChildren()) {
                    if (node != btnHide) {
                        node.setVisible(true);
                    }
                }
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
        
        btnSave.setOnAction((event) -> {
            Simulation simulation = new Simulation(circles, elasticity);
            FileChooser fileSaver = new FileChooser();
            fileSaver.setTitle("Save Simulation");
            fileSaver.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
            fileSaver.setInitialFileName("simulation");
            File file = fileSaver.showSaveDialog(btnSave.getScene().getWindow());
            if(file != null){
                try {
                    SimulationController.save(simulation, file);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
