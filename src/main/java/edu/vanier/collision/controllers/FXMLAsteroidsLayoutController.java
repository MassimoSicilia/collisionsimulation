/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.animation.DefaultAnimation;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.model.Simulation;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;

/**
 *
 * @author Hassimo
 */
public class FXMLAsteroidsLayoutController extends FXMLDefaultAnimationController {

    //make this custom and make constructor
    static ImagePattern asteroidImage = new ImagePattern(new Image("/images/Asteroid.png"));

    @FXML
    public void initialize() {
        super.initialize();

        // Hide the Arrow checkbox
        checkArrow.setVisible(false);
        // Hide the Color picker
        colorPicker.setVisible(false);
        // Select "Elastic" in the comboBoxElasticity
        comboBoxElasticity.getSelectionModel().select("Elastic");
        // Make the comboBoxElasticity non-editable
        comboBoxElasticity.setDisable(true);

        animationPane.setBackground(new Background(new BackgroundImage(new Image("/images/starfield_alpha.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        btnPlay.setOnAction((event) -> {
            btnRemove.setDisable(false);
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            if (!playing) {
                for (int i = 0; i < (int) sldBallsCount.getValue(); i++) {
                    // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                    double random_Mass_Radius = (2 + Math.random()) * 10; // All projectiles will have size between 20 and 30 pixels.
                    double minWidth = 30;
                    double maxWidth = animationPane.getWidth() - 2 * minWidth;
                    double minHeight = minWidth;
                    double maxHeight = animationPane.getHeight() - 2 * minHeight;
                    Projectile addedCircle = new Projectile(random_Mass_Radius, Math.random() * 10, Math.random() * 10, maxWidth * Math.random() + minWidth, maxHeight * Math.random() + minHeight, asteroidImage, random_Mass_Radius);
                    circles.add(addedCircle);
                    animationPane.getChildren().add(addedCircle.getCircle());
                }
            }

            disablePlayBtn();
            DefaultAnimation.setComponents(circles, animationPane);
            DefaultAnimation.play();
            playing = true;
        });
        sldBallsCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblBallsCount.setText(newValue.intValue() + " Asteroids");
        });

        btnSave.setOnAction((event) -> {
            Simulation simulation = new Simulation(circles, true, false, true);
            FileChooser fileSaver = new FileChooser();
            fileSaver.setTitle("Save Simulation");
            fileSaver.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));
            fileSaver.setInitialFileName("asteroid");
            File file = fileSaver.showSaveDialog(btnSave.getScene().getWindow());
            if (file != null) {
                try {
                    SimulationController.save(simulation, file);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
