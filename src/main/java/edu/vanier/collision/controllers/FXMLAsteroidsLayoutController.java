/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.animation.DefaultAnimation;
import edu.vanier.collision.model.Projectile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author Hassimo
 */
public class FXMLAsteroidsLayoutController extends FXMLDefaultAnimationController {

    static ImagePattern asteroidImage = new ImagePattern(new Image("/images/Asteroid.png"));
    public Button getBtnPlay() {
        return btnPlay;
    }
    

    @FXML
    public void initialize() {
        super.initialize();
        btnPlay.setOnAction((event) -> {
            btnRemove.setDisable(false);
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            for (int i = 0; i < (int) spObjectCount.getValue(); i++) {
                // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                double random_Mass_Radius = (0.75 + Math.random()) * 10; // All projectiles will have size between 7.5 and 17.5 pixels.
                Projectile addedCircle = new Projectile(random_Mass_Radius, Math.random() * 10, Math.random() * 10, 20, 40, asteroidImage, random_Mass_Radius);
                circles.add(addedCircle);
                animationPane.getChildren().add(addedCircle.getCircle());
            }

            disablePlayBtn();
            DefaultAnimation.setComponents(circles, animationPane);
            DefaultAnimation.play();
        });
    }
}
