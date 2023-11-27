/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.collision.animation.DefaultAnimation;
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
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Hassimo
 */
public class FXMLDefaultAnimationController extends Simulation {

    List<Projectile> circles = new ArrayList<>();

    // UI Controls
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
    @FXML
    ComboBox comboBoxElasticity;
    @FXML
    Spinner spObjectCount;

    //layouts
    @FXML
    AnchorPane animationPane;
    @FXML
    Pane PaneContainer;

    @FXML
    Slider volumeSlider;
    @FXML
    SplitPane root;
    @FXML
    AnchorPane controlsPane;

    private boolean elasticity = true;
    static AudioClip bouncingAudio = DefaultAnimation.bouncingAudio;

    @FXML
    public void initialize() {
        Divider divider = root.getDividers().get(0);
        animationPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < oldValue.doubleValue()) {
                for (Projectile currentProjectile : circles) {
                    Circle currentCircle = currentProjectile.getCircle();
                    if (currentCircle.getCenterX() > newValue.doubleValue() - currentCircle.getRadius()) {
                        currentCircle.setCenterX(newValue.doubleValue() - currentCircle.getRadius());
                    }
                }
            }
        });
        btnRemove.setDisable(true);

        spObjectCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));

        enablePlayBtn();

        // Add all projectiles to the pane.
        for (int i = 0; i < circles.size(); i++) {
            animationPane.getChildren().add(circles.get(i).getCircle());
        }

        // Elasticity of the simulation.
        comboBoxElasticity.getItems().addAll("Elastic", "Non-Elastic");
        comboBoxElasticity.getSelectionModel().select("Elastic");
        comboBoxElasticity.setOnAction((event) -> {
            if (comboBoxElasticity.getValue() == "Elastic") {
                DefaultAnimation.setElasticity(true);
            } else {
                DefaultAnimation.setElasticity(false);
            }
        });

        // Go returnEvent.
        btnReturn.setOnAction((event) -> {
            if (DefaultAnimation.isAnimationPlaying()) {
                DefaultAnimation.stop();
            }
            try {
                FXMLLoader returnLoader = new FXMLLoader(getClass().getResource("/fxml/choose_scenery.fxml"));
                returnLoader.setController(new FXMLChooseSceneryController());
                Parent root = returnLoader.load();
                btnReturn.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Start the simulation.
        btnPlay.setOnAction((event) -> {
            btnRemove.setDisable(false);
            if (circles.isEmpty()) {
                btnReset.setDisable(false);
            }
            for (int i = 0; i < (int) spObjectCount.getValue(); i++) {
                // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                double random_Mass_Radius = (0.75 + Math.random()) * 10; // All projectiles will have size between 7.5 and 17.5 pixels.
                Projectile addedCircle = new Projectile(random_Mass_Radius, Math.random() * 10, Math.random() * 10, 20, 40, Color.color(Math.random(), Math.random(), Math.random()), random_Mass_Radius);
                circles.add(addedCircle);
                animationPane.getChildren().add(addedCircle.getCircle());
            }

            disablePlayBtn();
            DefaultAnimation.setComponents(circles, animationPane);
            DefaultAnimation.play();
        });

        // Pause the simulation.
        btnPause.setOnAction((event) -> {
            DefaultAnimation.stop();
            enablePlayBtn();
        });

        // Reset the simulation.
        btnReset.setOnAction((event) -> {
            animationPane.getChildren().remove(1, circles.size() + 1); // the first element is the rectangle border
            circles.removeAll(circles);
            // if the animation is playing, stop it
            if (btnPlay.disabledProperty().getValue() == true) {
                DefaultAnimation.stop();
                enablePlayBtn();
            }

        });

        // Hide the controls.
        btnHide.setOnAction((event) -> {
            setDividerHidden(divider, root.getWidth());
        });

        // Remove Projectile from simulation.
        btnRemove.setOnAction((event) -> {
            btnRemove.setDisable(false);
            if (animationPane.getChildren().size() == 1) {
                btnRemove.setDisable(true);
            } else {
                animationPane.getChildren().remove(circles.size());
                circles.remove(circles.size() - 1);
            }
            DefaultAnimation.setComponents(circles, animationPane);
        });

        // Save the projectile to JSON file.
        btnSave.setOnAction((event) -> {
            Simulation simulation = new Simulation(circles, DefaultAnimation.isElasticity());
            FileChooser fileSaver = new FileChooser();
            fileSaver.setTitle("Save Simulation");
            fileSaver.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
            fileSaver.setInitialFileName("simulation");
            File file = fileSaver.showSaveDialog(btnSave.getScene().getWindow());
            if (file != null) {
                try {
                    SimulationController.save(simulation, file);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Mute the simulation.
        btnMute.setOnAction((event) -> {
            if (btnMute.getText().equals("Mute")) {
                btnMute.setText("Unmute");
                volumeSlider.setDisable(true);
                bouncingAudio.setVolume(0.0);
            } else {
                btnMute.setText("Mute");
                bouncingAudio.setVolume(volumeSlider.getValue());
                volumeSlider.setDisable(false);
            }
        });

        // Set volume using the slider.
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setValue(1);
        volumeSlider.valueProperty().addListener((observable) -> {
            bouncingAudio.setVolume(volumeSlider.getValue());
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

    public void setDividerOriginal(Divider divider, double originalPosition, ChangeListener hiddenDividerListener) {
        divider.positionProperty().removeListener(hiddenDividerListener);
        divider.setPosition(originalPosition);
    }

    public void setDividerHidden(Divider divider, double hiddenPosition) {
        divider.setPosition(hiddenPosition);
    }
}
