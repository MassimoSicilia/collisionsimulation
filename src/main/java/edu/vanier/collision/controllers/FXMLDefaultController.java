/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import edu.vanier.collision.animation.Animation;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Massimo
 */
public class FXMLDefaultController {

    List<Projectile> projectiles = new ArrayList<>();
    private static Animation animation = new Animation();
    private static boolean playing;
    private static boolean isDefaultAnimation;
    private boolean isElastic;
    private boolean loadedFromFile;
    private EventHandler<MouseEvent> clickHandler;
    private static String objectType;
    private AudioClip bouncingAudio;
    
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
    Button btnChangeBall;
    @FXML
    Button btnChangeBackground;
    @FXML
    Slider sldVolume;
    @FXML
    Slider sldBallsCount;
    @FXML
    Label lblBallsCount;
    @FXML
    CheckBox checkArrow;
    @FXML
    ColorPicker colorPicker;

    //layouts
    @FXML
    AnchorPane animationPane;
    @FXML
    SplitPane root;


    public FXMLDefaultController() {
        this.checkArrowEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                updateArrowVisibility(checkArrow.isSelected());
            }
        };
        objectType = "Balls";
        isDefaultAnimation = true;
        bouncingAudio = new AudioClip(Animation.class.getResource("/audio/ballBounce.wav").toExternalForm());
    }

    public FXMLDefaultController(List<Projectile> projectiles) {
        this.checkArrowEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                updateArrowVisibility(checkArrow.isSelected());
            }
        };
        objectType = "Balls";
        this.projectiles = projectiles;
        loadedFromFile = true;
        isDefaultAnimation = true;
        bouncingAudio = new AudioClip(Animation.class.getResource("/audio/ballBounce.wav").toExternalForm());
    }

    public FXMLDefaultController(List<Projectile> projectiles, boolean elastic) {
        this.checkArrowEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                updateArrowVisibility(checkArrow.isSelected());
            }
        };
        objectType = "Balls";
        this.projectiles = projectiles;
        loadedFromFile = true;
        isElastic = elastic;
        isDefaultAnimation = true;
        bouncingAudio = new AudioClip(Animation.class.getResource("/audio/ballBounce.wav").toExternalForm());
    }

    @FXML
    public void initialize() {
        Divider divider = root.getDividers().get(0);
        layoutInitialize();
        // Rearranges positions of projectiles of pane is resized.
        animationPane.widthProperty().addListener(paneResizeListener);

        checkArrow.setOnAction(checkArrowEvent);
        // Elasticity of the simulation.
        comboBoxElasticity.setOnAction(comboBoxElasticityEvent);

        btnReturn.setOnAction(btnReturnEvent);

        btnPlay.setOnAction(setAnimationProperties(7.5, 7.5, null));

        btnPause.setOnAction(btnPauseEvent);

        btnReset.setOnAction(btnResetEvent);

        btnHide.setOnAction((event) -> {
            divider.setPosition(root.getWidth());
        });

        btnRemove.setOnAction(btnRemoveEvent);

        btnSave.setOnAction(setBtnSaveEvent());

        btnMute.setOnAction(btnMuteEvent);

        btnChangeBall.setOnAction(btnChangeBallEvent);

        btnChangeBackground.setOnAction(btnChangeBackgroundEvent);

        sldVolume.valueProperty().addListener((observable) -> {
            Animation.bouncingAudio.setVolume(sldVolume.getValue());
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
    EventHandler<ActionEvent> checkArrowEvent;
    EventHandler<ActionEvent> comboBoxElasticityEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            animation.setElastic(comboBoxElasticSelection());
        }
    };
    EventHandler<ActionEvent> btnReturnEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (playing) {
                animation.stop();
                playing = false;
            }
            try {
                FXMLLoader returnLoader = new FXMLLoader(getClass().getResource("/fxml/ChooseSceneryPane.fxml"));
                returnLoader.setController(new FXMLChooseSceneryController());
                Parent root = returnLoader.load();
                btnReturn.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDefaultController.class.getName()).log(Level.SEVERE, null, ex);
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
                animation = new Animation(projectiles, animationPane);
                animation.setElastic(comboBoxElasticSelection());
                animation.play();
                playing = true;
            }
        };
        return btnPlayEvent;
    }

    EventHandler<ActionEvent> btnPauseEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (playing) {
                animation.stop();
                enableResume();

                // Add mouse click event handler for color selection when simulation is paused
                for (Projectile projectile : projectiles) {
                    addMouseClickHandler(projectile);
                }
            } else {
                animation.play();
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
                animation.stop();
                playing = false;
            } else {
                btnPause.setText("Pause");
            }

            // Store the current volume state before resetting
            double storedVolume = sldVolume.getValue();
            boolean wasMuted = btnMute.getText().equals("Unmute");

            // Remove all projectiles and direction arrows from the pane.
            animationPane.getChildren().removeAll(projectiles.stream().flatMap(projectile -> Stream.of(projectile.getCircle(), projectile.getDirectionArrow())).toArray(Node[]::new));

            // Clear the list of projectiles.
            projectiles.clear();

            // Set the volume slider to the current volume
            if (sldVolume != null) {
                sldVolume.setValue(Animation.bouncingAudio.getVolume());
            }
            initializeBallCount();
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
            updateBallsCount();
        }
    };

    EventHandler<ActionEvent> setBtnSaveEvent() {
        EventHandler<ActionEvent> btnSaveEvent = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Simulation simulation = new Simulation(projectiles, animation.isElastic(), isDefaultAnimation);
                FileChooser fileSaver = new FileChooser();
                fileSaver.setTitle("Save Simulation");
                fileSaver.getExtensionFilters().add(new ExtensionFilter("JSON File", "*.json"));
                if (isDefaultAnimation) {
                    fileSaver.setInitialFileName("default_simulation");
                } else {
                    fileSaver.setInitialFileName("asteroid_simulation");
                }
                File file = fileSaver.showSaveDialog(btnSave.getScene().getWindow());
                if (file != null) {
                    try {
                        SimulationController.save(simulation, file);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDefaultController.class.getName()).log(Level.SEVERE, null, ex);
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
                sldVolume.setDisable(true);
                Animation.bouncingAudio.setVolume(0.0);
            } else {
                btnMute.setText("Mute");
                Animation.bouncingAudio.setVolume(sldVolume.getValue());
                sldVolume.setDisable(false);
            }
        }
    };
    EventHandler<ActionEvent> btnChangeBallEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Ball Picture");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Image files", "*.png", "*.jpeg", "*.png", "*.gif"));
                File ballPicture = fileChooser.showOpenDialog(btnChangeBall.getScene().getWindow());
                for (Projectile projectile : projectiles) {
                    projectile.setPaint(new ImagePattern(new Image(ballPicture.getPath())));
                }
            } catch (Exception e) {

            }
        }
    };
    EventHandler<ActionEvent> btnChangeBackgroundEvent = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Background Picture");
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Image files", "*.png", "*.jpeg", "*.png", "*.gif"));
                File backgroundPicture = fileChooser.showOpenDialog(btnChangeBall.getScene().getWindow());
                animationPane.setBackground(new Background(new BackgroundImage(new Image(backgroundPicture.getPath()),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));
            } catch (Exception e) {

            }
        }
    };

    // Helper Methods.

    /**
     *returns base layout
     */
    public void layoutInitialize() {
        enablePlayBtn();
        animation = new Animation(projectiles, animationPane);
        animation.setBouncingAudio(bouncingAudio);
        comboBoxElasticity.getItems().addAll("Elastic", "Non-Elastic");
        comboBoxElasticity.getSelectionModel().select("Elastic");
        initializeBallCount();
        if (loadedFromFile) {
            disablePlayBtn();
            enableResume();
            // Set the position of the slider and the ball count relative to the number of projectiles.
            initializeSliderPosition();
            // Add all projectiles to the pane.
            addAllProjectiles();
            if (isElastic()) {
                comboBoxElasticity.getSelectionModel().select("Elastic");
            }else{
                comboBoxElasticity.getSelectionModel().select("Non-Elastic");
            }
        }
    }

    /**
     *disables the play button and enables features that can change
     * if the animation is running
     */
    public void disablePlayBtn() {
        btnPlay.setDisable(true);
        btnRemove.setDisable(false);
        btnPause.setDisable(false);
        btnReset.setDisable(false);
        sldBallsCount.setDisable(true);
    }

    /**
     *enables the play button and disables features that shouldn't change
     * if the animation is running
     */
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

    /**
     *enables pause button and sets the text to "Pause"
     * 
     */
    public void enablePause() {
        btnPause.setText("Pause");
        playing = true;

        // Remove mouse click event handler when simulation is resumed
        for (Projectile projectile : projectiles) {
            removeMouseClickHandler(projectile);
        }
    }

    /**
     *while the animation is stopped, the "Pause" becomes "Resume"
     *
     */
    public void enableResume() {
        btnPause.setText("Resume");
        playing = false;

        // Add mouse click event handler for color selection when simulation is paused
        for (Projectile projectile : projectiles) {
            addMouseClickHandler(projectile);
        }
    }

    /**
     *allows the list of projectiles to be equal to the value the slider is on
     */
    public void initializeSliderPosition() {
        sldBallsCount.setValue(projectiles.size());
    }

    /**
     *sets the label for the number of projectiles equal to 
     * the number the slider is on
     */
    public void initializeBallCount() {
        lblBallsCount.setText(projectiles.size() + " " + objectType);
    }

    // Update the visibility of arrows based on the checkbox state
    private void updateArrowVisibility(boolean showArrows) {
        for (Projectile projectile : projectiles) {
            projectile.getDirectionArrow().setVisible(showArrows);
        }
    }
//opens color picker
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
//adding projectiles to the pane
    private void addAllProjectiles() {
        for (Projectile projectile : projectiles) {
            animationPane.getChildren().addAll(projectile.getCircle(), projectile.getDirectionArrow());
            updateArrowVisibility(false);
            // Handle color picking for each ball
            addMouseClickHandler(projectile);
        }
    }

    /**
     *
     * @param loadedFromFile
     */
    public void setLoadedFromFile(boolean loadedFromFile) {
        this.loadedFromFile = loadedFromFile;
    }
    
    /**
     *
     * @param objectType
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     *
     * @param isDefaultAnimation
     */
    public void setIsDefaultAnimation(boolean isDefaultAnimation) {
        this.isDefaultAnimation = isDefaultAnimation;
    }

    /**
     *
     * @return
     */
    public static Animation getAnimation() {
        return animation;
    }

    /**
     *
     * @param animation
     */
    public static void setAnimation(Animation animation) {
        FXMLDefaultController.animation = animation;
    }

    /**
     *
     * @return
     */
    public static boolean isPlaying() {
        return playing;
    }

    /**
     *
     * @param bouncingAudio
     */
    public void setBouncingAudio(AudioClip bouncingAudio) {
        this.bouncingAudio = bouncingAudio;
    }

    private void updateBallsCount() {
        int currentBalls = projectiles.size();
        lblBallsCount.setText(currentBalls + " " + objectType);
    }

    public boolean isElastic() {
        return isElastic;
    }

    public void setElastic(boolean isElastic) {
        this.isElastic = isElastic;
    }
    public boolean comboBoxElasticSelection(){
        boolean selection = comboBoxElasticity.getValue().equals("Elastic");
        setElastic(selection);
        return selection;
    }

}
