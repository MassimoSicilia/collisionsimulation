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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
        animationPane.setBackground(new Background(new BackgroundImage(new Image("/images/starfield_alpha.png"), 
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        btnPlay.setOnAction((event) -> {
            btnRemove.setDisable(false);
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            for (int i = 0; i < (int) spObjectCount.getValue(); i++) {
                // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                double random_Mass_Radius = (2 + Math.random()) * 10; // All projectiles will have size between 20 and 30 pixels.
                Projectile addedCircle = new Projectile(random_Mass_Radius, Math.random() * 5, Math.random() * 5, 50, 50, asteroidImage, random_Mass_Radius);
                circles.add(addedCircle);
                animationPane.getChildren().add(addedCircle.getCircle());
            }

            disablePlayBtn();
            DefaultAnimation.setComponents(circles, animationPane);
            DefaultAnimation.play();
        });
    }
}
