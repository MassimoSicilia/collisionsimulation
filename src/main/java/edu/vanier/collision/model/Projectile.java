/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

/**
 *
 * @author andyhou
 */
public abstract class Projectile {
    private double mass;
    private double x_velocity;
    private double y_velocity;
    private double angle; // in degrees
    private int x_position;
    private int y_position;;

    /**
     * Creates default Projectile object, all default objects will have no velocity or angle.
     */
    public Projectile() {
        this.mass = 10;
        this.x_position = 0;
        this.y_position = 0;
    }

    /**
     * Creates Projectile object with all specified variables.
     * @param mass
     * @param x_velocity
     * @param y_velocity
     * @param angle
     * @param x_position
     * @param y_position
     * @param radius
     */
    public Projectile(double mass, double x_velocity, double y_velocity, double angle, int x_position, int y_position) {
        this.mass = mass;
        this.x_velocity = x_velocity;
        this.y_velocity = y_velocity;
        this.angle = angle;
        this.x_position = x_position;
        this.y_position = y_position;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getX_position() {
        return x_position;
    }

    public void setX_position(int x_position) {
        this.x_position = x_position;
    }

    public int getY_position() {
        return y_position;
    }

    public void setY_position(int y_position) {
        this.y_position = y_position;
    }
       
}
