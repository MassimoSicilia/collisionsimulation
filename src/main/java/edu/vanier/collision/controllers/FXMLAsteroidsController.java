/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.animation.Animation;
import static edu.vanier.collision.animation.Animation.setBouncingAudio;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.model.Simulation;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;

/**
 *
 * @author Hassimo
 */
public class FXMLAsteroidsController extends FXMLDefaultController {

    static ImagePattern asteroidImagePattern = new ImagePattern(new Image("/images/Asteroid.png"));

    public FXMLAsteroidsController() {
        setIsDefaultAnimation(false);
        setBouncingAudio(new AudioClip(Animation.class.getResource("/audio/rockHit.wav").toExternalForm()));
    }

    public FXMLAsteroidsController(List<Projectile> projectiles) {
        super(projectiles);
        setIsDefaultAnimation(false);
    }

    @FXML
    public void initialize() {
        setObjectType("Asteroids");
        super.initialize();
        animationPane.setBackground(new Background(new BackgroundImage(new Image("/images/starfield_alpha.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true))));
        btnPlay.setOnAction(setAnimationProperties(20, 2.5, asteroidImagePattern));
        btnSave.setOnAction(setBtnSaveEvent());
    }
}
