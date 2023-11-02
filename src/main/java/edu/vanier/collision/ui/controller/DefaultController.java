/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Hassimo
 */
public class DefaultController {
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
    
    //layouts
    @FXML
    Pane animationPane;
    @FXML
    Pane bottomPane;
    @FXML
    HBox HBoxTop; 
    
    
    @FXML
    public void initialize(){
        
    }
    
}
