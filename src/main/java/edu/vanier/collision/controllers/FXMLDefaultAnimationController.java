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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    boolean playing;
    private EventHandler<MouseEvent> clickHandler;
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
    @FXML
    Slider sldBallsCount;
    @FXML
    Label lblBallsCount;

    @FXML
    CheckBox checkArrow;
    @FXML
    ColorPicker colorPicker;

    private boolean elasticity = true;
    static AudioClip bouncingAudio = DefaultAnimation.bouncingAudio;

    ChangeListener<? super Number> paneResizeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue.doubleValue() < oldValue.doubleValue()) {
                for (Projectile currentProjectile : circles) {
                    Circle currentCircle = currentProjectile.getCircle();
                    if (currentCircle.getCenterX() > newValue.doubleValue() - currentCircle.getRadius()) {
                        currentCircle.setCenterX(newValue.doubleValue() - currentCircle.getRadius());
                    }
                }
            }
        }
    };
    
    EventHandler<ActionEvent> checkArrowEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            updateArrowVisibility(checkArrow.isSelected());
        }
    };    

    @FXML
    public void initialize() {
        enablePlayBtn();
        Divider divider = root.getDividers().get(0);
        animationPane.widthProperty().addListener(paneResizeListener);
        checkArrow.setOnAction(checkArrowEvent);

        // Add all projectiles to the pane.
        addAllProjectiles();

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
            if (!playing) {
                for (int i = 0; i < (int) sldBallsCount.getValue(); i++) {
                    // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                    double random_Mass_Radius = (0.75 + Math.random()) * 10; // All projectiles will have size between 7.5 and 17.5 pixels.
                    double minWidth = 17.5;
                    double maxWidth = animationPane.getWidth() - 2 * minWidth;
                    double minHeight = minWidth;
                    double maxHeight = animationPane.getHeight() - 2 * minHeight;
                    Projectile addedCircle = new Projectile(random_Mass_Radius, Math.random() * 10, Math.random() * 10, maxWidth * Math.random() + minWidth, maxHeight * Math.random() + minHeight, Color.color(Math.random(), Math.random(), Math.random()), random_Mass_Radius);
                    circles.add(addedCircle);
                    animationPane.getChildren().addAll(addedCircle.getCircle(), addedCircle.getDirectionArrow());
                }
            }
            disablePlayBtn();
            DefaultAnimation.setComponents(circles, animationPane);
            DefaultAnimation.play();
            playing = true;
        });

        btnPause.setOnAction((event) -> {
            if (playing) {
                DefaultAnimation.stop();
                btnPause.setText("Resume");
                playing = false;

                // Add mouse click event handler for color selection when simulation is paused
                for (Projectile projectile : circles) {
                    addMouseClickHandler(projectile);
                }
            } else {
                DefaultAnimation.play();
                btnPause.setText("Pause");
                playing = true;

                // Remove mouse click event handler when simulation is resumed
                for (Projectile projectile : circles) {
                    removeMouseClickHandler(projectile);
                }
            }
        });

        // Reset the simulation.
        btnReset.setOnAction((event) -> {
            // Stop the animation if it's playing.
            if (playing) {
                DefaultAnimation.stop();
                playing = false;
            } else {
                btnPause.setText("Pause");
            }

            // Reset the speed of each projectile.
            for (Projectile projectile : circles) {
                projectile.resetSpeed();
            }

            // Remove all projectiles and direction arrows from the pane.
            animationPane.getChildren().removeAll(
                    circles.stream().flatMap(projectile -> Stream.of(projectile.getCircle(), projectile.getDirectionArrow())).toArray(Node[]::new)
            );

            // Clear the list of projectiles.
            circles.clear();

            // Set the checkbox to checked after resetting
            checkArrow.setSelected(true);
            updateArrowVisibility(true);

            enablePlayBtn();
        });

        // Hide the controls.
        btnHide.setOnAction((event) -> {
            setDividerHidden(divider, root.getWidth());
        });

        // Remove Projectile from simulation.
        btnRemove.setOnAction((event) -> {
            btnRemove.setDisable(false);
            // Reset speeds of the last ball
            Projectile removedProjectile = circles.remove(circles.size() - 1);

            // Remove the last projectile's circle and arrow from the animationPane
            animationPane.getChildren().removeAll(removedProjectile.getCircle(), removedProjectile.getDirectionArrow());

            // Update simulation components
            DefaultAnimation.setComponents(circles, animationPane);
            if (animationPane.getChildren().isEmpty()) {
                btnRemove.setDisable(true);
                // Highlight the reset button
                btnReset.requestFocus();
            }

        });

        // Save the projectile to JSON file.
        btnSave.setOnAction((event) -> {
            Simulation simulation = new Simulation(circles, DefaultAnimation.isElasticity(), true, false);
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

        sldBallsCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblBallsCount.setText(newValue.intValue() + " Balls");
        });

    }

    public void disablePlayBtn() {
        btnPlay.setDisable(true);
        btnPause.setDisable(false);
        btnReset.setDisable(false);
        sldBallsCount.setDisable(true);
    }

    public void enablePlayBtn() {
        btnPlay.setDisable(false);
        btnPause.setDisable(true);
        sldBallsCount.setDisable(false);
        if (circles.isEmpty()) {
            btnReset.setDisable(true);
            btnRemove.setDisable(true);
        } else {
            btnReset.setDisable(false);
            btnRemove.setDisable(false);
        }
    }

    public void setDividerOriginal(Divider divider, double originalPosition, ChangeListener hiddenDividerListener) {
        divider.positionProperty().removeListener(hiddenDividerListener);
        divider.setPosition(originalPosition);
    }

    public void setDividerHidden(Divider divider, double hiddenPosition) {
        divider.setPosition(hiddenPosition);
    }

    // Update the visibility of arrows based on the checkbox state
    private void updateArrowVisibility(boolean showArrows) {
        for (Projectile projectile : circles) {
            projectile.getDirectionArrow().setVisible(showArrows);
        }
    }

    private void addMouseClickHandler(Projectile projectile) {
        clickHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Open the color picker dialog
                colorPicker.setValue((Color) projectile.getCircle().getFill());

                // Update the color of the ball based on the selected color
                colorPicker.show();
                colorPicker.setOnAction(colorEvent -> {
                    projectile.getCircle().setFill(colorPicker.getValue());
                    colorPicker.hide();
                });
            }
        };

        projectile.getCircle().addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
    }

    private void removeMouseClickHandler(Projectile projectile) {
        if (clickHandler != null) {
            projectile.getCircle().removeEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
        }
    }
    private void addAllProjectiles(){
        for (Projectile projectile : circles) {
            animationPane.getChildren().addAll(projectile.getCircle(), projectile.getDirectionArrow());
            // Handle color picking for each ball
            addMouseClickHandler(projectile);
        }
    }
}
