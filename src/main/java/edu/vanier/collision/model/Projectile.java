/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * Projectile class displays information that every shape object should have,
 * which affects collisions.
 *
 * @author andyhou
 */
public class Projectile {

    // variables
    
    private double mass;
    private double x_velocity;
    private double y_velocity;
    private Circle circle;
    private Line directionArrow;

    // constructors
    
    /**
     * Creates default Projectile object, all default objects will have no
     * velocity or angle.
     */
    public Projectile() {
        // Initialize the direction arrow in the default constructor
        directionArrow = new Line();
        directionArrow.setStroke(Color.BLACK);
        directionArrow.setStrokeWidth(1.5);
    }

    /**
     * Creates Projectile object with all specified variables.
     *
     * @param mass
     * @param x_velocity
     * @param y_velocity
     * @param x_position
     * @param y_position
     * @param radius
     */
    public Projectile(double mass, double x_velocity, double y_velocity, double x_position, double y_position, double radius) {
        this(); // Call the default constructor to initialize the direction arrow
        this.mass = mass;
        this.x_velocity = x_velocity;
        this.y_velocity = y_velocity;
        circle = new Circle(x_position, y_position, radius);
        circle.setStroke(Color.BLACK);
    }

    // getters and setters
    
    /**
     *
     * @return
     */
    public double getMass() {
        return mass;
    }

    /**
     *
     * @param mass
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     *
     * @return
     */
    public double getX_velocity() {
        return x_velocity;
    }

    /**
     *
     * @param x_velocity
     */
    public void setX_velocity(double x_velocity) {
        this.x_velocity = x_velocity;
    }

    /**
     *
     * @return
     */
    public double getY_velocity() {
        return y_velocity;
    }

    /**
     *
     * @param y_velocity
     */
    public void setY_velocity(double y_velocity) {
        this.y_velocity = y_velocity;
    }

    /**
     *
     * @return
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     *
     * @return
     */
    public Line getDirectionArrow() {
        return directionArrow;
    }

    /**
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        circle.setFill(paint);
    }

    /**
     * changes where the arrow points based on the direction the object is moving
     */
    public void updateDirectionArrow() {

        // Move the arrow along with the ball
        directionArrow.setStartX(getCircle().getCenterX());
        directionArrow.setStartY(getCircle().getCenterY());

        // Calculate the end position based on the ball's direction
        double arrowLength = 20.0; // Adjust as needed
        double endX = getCircle().getCenterX() + getX_velocity() * arrowLength;
        double endY = getCircle().getCenterY() + getY_velocity() * arrowLength;

        // Update arrow's end position only if it's not NaN (Not a Number)
        if (!Double.isNaN(endX) && !Double.isNaN(endY)) {
            directionArrow.setEndX(endX);
            directionArrow.setEndY(endY);
        }
    }

    /**
     * Resets speed of the objects
     */
    public void resetSpeed() {
        setX_velocity(0.0);
        setY_velocity(0.0);
    }
    
}
