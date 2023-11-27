/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

import java.io.Serializable;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Projectile class displays information that every shape object should have,
 * which affects collisions.
 *
 * @author andyhou
 */
public class Projectile {

    private double mass;
    private double x_velocity;
    private double y_velocity;
    private Circle circle;

    /**
     * Creates default Projectile object, all default objects will have no
     * velocity or angle.
     */
    public Projectile() {
    }

    /**
     * Creates Projectile object with all specified variables.
     *
     * @param mass
     * @param x_velocity
     * @param y_velocity
     * @param x_position
     * @param y_position
     */
    public Projectile(double mass, double x_velocity, double y_velocity, double x_position, double y_position, Color color, double radius) {
        this.mass = mass;
        this.x_velocity = x_velocity;
        this.y_velocity = y_velocity;
        circle = new Circle(x_position, y_position, radius, color);
        circle.setStroke(Color.BLACK);
    }
    public Projectile(double mass, double x_velocity, double y_velocity, double x_position, double y_position, ImagePattern asteroidImage, double radius) {
        this.mass = mass;
        this.x_velocity = x_velocity;
        this.y_velocity = y_velocity;
        circle = new Circle(x_position, y_position, radius);
        circle.setFill(asteroidImage);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getX_velocity() {
        return x_velocity;
    }

    public void setX_velocity(double x_velocity) {
        this.x_velocity = x_velocity;
    }

    public double getY_velocity() {
        return y_velocity;
    }

    public void setY_velocity(double y_velocity) {
        this.y_velocity = y_velocity;
    }

    public Circle getCircle(){
        return circle;
    }

}
