/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.simulation;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 *
 * @author Peeta
 */
public class PaneCollisionBox extends Pane implements CollisionBox {

    public PaneCollisionBox() {
        // Constructor for PaneCollisionBox, no need to initialize the "pane" field
    }

    @Override
    public void addProjectile(Circle ball) {
        getChildren().add(ball); // Use getChildren() directly, as "this" is a Pane
    }

    @Override
    public void removeProjectile(Circle ball) {
        getChildren().remove(ball);
    }
}
