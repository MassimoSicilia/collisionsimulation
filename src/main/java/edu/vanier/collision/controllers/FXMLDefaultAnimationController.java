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
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Hassimo
 */
public class FXMLDefaultAnimationController extends Simulation {

    List<Projectile> projectiles = new ArrayList<>();
    private boolean playing;
    private boolean loadedFromFile;
    private EventHandler<MouseEvent> clickHandler;
    private String objectType = "Balls";
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

    static AudioClip bouncingAudio = DefaultAnimation.bouncingAudio;

    @FXML
    public void initialize() {
        Divider divider = root.getDividers().get(0);
        enablePlayBtn();

        if (loadedFromFile) {
            disablePlayBtn();
            enableResume();
            // Set the position of the slider and the ball count relative to the number of projectiles.
            initializeSliderPosition();
            initializeBallCount();
            // Add all projectiles to the pane.
            addAllProjectiles();
            DefaultAnimation.setComponents(projectiles, PaneContainer);
        }

        // Rearranges positions of projectiles of pane is resized.
        animationPane.widthProperty().addListener(paneResizeListener);

        checkArrow.setOnAction(checkArrowEvent);
        // Elasticity of the simulation.
        comboBoxElasticity.getItems().addAll("Elastic", "Non-Elastic");
        comboBoxElasticity.getSelectionModel().select("Elastic");
        comboBoxElasticity.setOnAction(comboBoxElasticityEvent);

        btnReturn.setOnAction(btnReturnEvent);

        btnPlay.setOnAction(setAnimationProperties(7.5, 7.5, null));

        btnPause.setOnAction(btnPauseEvent);

        btnReset.setOnAction(btnResetEvent);

        btnHide.setOnAction((event) -> {
            divider.setPosition(root.getWidth());
        });

        btnRemove.setOnAction(btnRemoveEvent);

        btnSave.setOnAction(setBtnSaveEvent(true));

        btnMute.setOnAction(btnMuteEvent);

        volumeSlider.valueProperty().addListener((observable) -> {
            bouncingAudio.setVolume(volumeSlider.getValue());
        });

        // Updates ball count when slider is adjusted.
        sldBallsCount.valueProperty().addListener(ballsCountListener);

    }

    ChangeListener<? super Number> paneResizeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (newValue.doubleValue() < oldValue.doubleValue()) {
                for (Projectile currentProjectile : projectiles) {
                    Circle currentCircle = currentProjectile.getCircle();
                    if (currentCircle.getCenterX() > newValue.doubleValue() - currentCircle.getRadius()) {
                        currentCircle.setCenterX(newValue.doubleValue() - currentCircle.getRadius());
                        currentProjectile.updateDirectionArrow();
                    }
                }
            }
        }
    };

    ChangeListener<? super Number> ballsCountListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            lblBallsCount.setText(newValue.intValue() + " " + objectType);
        }
    };

    // All event handlers.
    EventHandler<ActionEvent> checkArrowEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            updateArrowVisibility(checkArrow.isSelected());
        }
    };
    EventHandler<ActionEvent> comboBoxElasticityEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (comboBoxElasticity.getValue() == "Elastic") {
                DefaultAnimation.setElasticity(true);
            } else {
                DefaultAnimation.setElasticity(false);
            }
        }
    };
    EventHandler<ActionEvent> btnReturnEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (playing) {
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
        }
    };

    EventHandler<ActionEvent> setAnimationProperties(double minSize, double maxSpeed, ImagePattern ballPattern) {
        EventHandler<ActionEvent> btnPlayEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < (int) sldBallsCount.getValue(); i++) {
                    // Projectiles will have the same value for mass and radius in order to ensure they're proportional.
                    double random_Mass_Radius = minSize + (Math.random() * 10); // All projectiles will have size between minSize and 10 + minSize.
                    // These variables ensure that the projectile will be within the borders of the animationPane.
                    double minXPosition = random_Mass_Radius;
                    double maxXPosition = animationPane.getWidth() - minXPosition;
                    double randomXPosition = ((maxXPosition - minXPosition) * Math.random()) + minXPosition;
                    double minYPosition = minXPosition;
                    double maxYPosition = animationPane.getHeight() - 2 * minYPosition;
                    double randomYPosition = ((maxYPosition - minYPosition) * Math.random()) + minYPosition;
                    double direction;
                    // Randomizes direction (up/left, down/right).
                    if (Math.random() >= 0.5) {
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    Projectile addedProjectile = addedProjectile = new Projectile(random_Mass_Radius, direction * Math.random() * maxSpeed, direction * Math.random() * maxSpeed,
                            randomXPosition, randomYPosition, random_Mass_Radius);
                    if (ballPattern == null) {
                        addedProjectile.setPaint(Color.color(Math.random(), Math.random(), Math.random()));
                    } else {
                        addedProjectile.setPaint(ballPattern);
                    }
                    projectiles.add(addedProjectile);
                    animationPane.getChildren().addAll(addedProjectile.getCircle(), addedProjectile.getDirectionArrow());
                    updateArrowVisibility(checkArrow.isSelected());
                }

                disablePlayBtn();
                DefaultAnimation.setComponents(projectiles, animationPane);
                DefaultAnimation.play();
                playing = true;
            }
        };
        return btnPlayEvent;
    }

    EventHandler<ActionEvent> btnPauseEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (playing) {
                DefaultAnimation.stop();
                enableResume();

                // Add mouse click event handler for color selection when simulation is paused
                for (Projectile projectile : projectiles) {
                    addMouseClickHandler(projectile);
                }
            } else {
                DefaultAnimation.play();
                enablePause();

                // Remove mouse click event handler when simulation is resumed
                for (Projectile projectile : projectiles) {
                    removeMouseClickHandler(projectile);
                }
            }
        }
    };

    EventHandler<ActionEvent> btnResetEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            // Stop the animation if it's playing.
            if (playing) {
                DefaultAnimation.stop();
                playing = false;
            } else {
                btnPause.setText("Pause");
            }

            // Remove all projectiles and direction arrows from the pane.
            animationPane.getChildren().removeAll(projectiles.stream().flatMap(projectile -> Stream.of(projectile.getCircle(), projectile.getDirectionArrow())).toArray(Node[]::new));

            // Clear the list of projectiles.
            projectiles.clear();
            enablePlayBtn();
        }
    };
    EventHandler<ActionEvent> btnRemoveEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            Projectile removedProjectile = projectiles.remove(projectiles.size() - 1);

            // Remove the last projectile's circle and arrow from the animationPane
            animationPane.getChildren().removeAll(removedProjectile.getCircle(), removedProjectile.getDirectionArrow());

            // Update simulation components
            if (animationPane.getChildren().isEmpty()) {
                btnRemove.setDisable(true);
                // Highlight the reset button
                btnReset.requestFocus();
            }
        }
    };

    EventHandler<ActionEvent> setBtnSaveEvent(boolean isDefault) {
        EventHandler<ActionEvent> btnSaveEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Simulation simulation = new Simulation(projectiles, DefaultAnimation.isElasticity(), isDefault);
                FileChooser fileSaver = new FileChooser();
                fileSaver.setTitle("Save Simulation");
                fileSaver.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
                if (isDefault) {
                    fileSaver.setInitialFileName("default_simulation");
                } else {
                    fileSaver.setInitialFileName("asteroid_simulation");
                }
                File file = fileSaver.showSaveDialog(btnSave.getScene().getWindow());
                if (file != null) {
                    try {
                        SimulationController.save(simulation, file);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDefaultAnimationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        return btnSaveEvent;
    }
    EventHandler<ActionEvent> btnMuteEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (btnMute.getText().equals("Mute")) {
                btnMute.setText("Unmute");
                volumeSlider.setDisable(true);
                bouncingAudio.setVolume(0.0);
            } else {
                btnMute.setText("Mute");
                bouncingAudio.setVolume(volumeSlider.getValue());
                volumeSlider.setDisable(false);
            }
        }
    };

    // Helper Methods.
    public void disablePlayBtn() {
        btnPlay.setDisable(true);
        btnRemove.setDisable(false);
        btnPause.setDisable(false);
        btnReset.setDisable(false);
        sldBallsCount.setDisable(true);
    }

    public void enablePlayBtn() {
        btnPlay.setDisable(false);
        colorPicker.setDisable(true);
        btnPause.setDisable(true);
        sldBallsCount.setDisable(false);
        if (projectiles.isEmpty()) {
            btnReset.setDisable(true);
            btnRemove.setDisable(true);
        } else {
            btnReset.setDisable(false);
            btnRemove.setDisable(false);
        }
    }

    public void enablePause() {
        btnPause.setText("Pause");
        playing = true;

        // Remove mouse click event handler when simulation is resumed
        for (Projectile projectile : projectiles) {
            removeMouseClickHandler(projectile);
        }
    }

    public void enableResume() {
        btnPause.setText("Resume");
        playing = false;

        // Add mouse click event handler for color selection when simulation is paused
        for (Projectile projectile : projectiles) {
            addMouseClickHandler(projectile);
        }
    }

    public void initializeSliderPosition() {
        sldBallsCount.setValue(projectiles.size());
    }

    public void initializeBallCount() {
        lblBallsCount.setText(((Double) sldBallsCount.getValue()).intValue() + " " + objectType);
    }

    // Update the visibility of arrows based on the checkbox state
    private void updateArrowVisibility(boolean showArrows) {
        for (Projectile projectile : projectiles) {
            projectile.getDirectionArrow().setVisible(showArrows);
        }
    }

    private void addMouseClickHandler(Projectile projectile) {
        clickHandler = event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                colorPicker.setDisable(false);
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

    private void addAllProjectiles() {
        for (Projectile projectile : projectiles) {
            animationPane.getChildren().addAll(projectile.getCircle(), projectile.getDirectionArrow());
            updateArrowVisibility(false);
            // Handle color picking for each ball
            addMouseClickHandler(projectile);
        }
    }

    public void setLoadedFromFile(boolean loadedFromFile) {
        this.loadedFromFile = loadedFromFile;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

}
