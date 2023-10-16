/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.vanier.collision.simulation;

import javafx.scene.shape.Circle;

/**
 *
 * @author Peeta
 */
public interface CollisionBox {
    void addProjectile(Circle ball);
    void removeProjectile(Circle ball);
}
