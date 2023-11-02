/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

/**
 * CircleProjectile is a Projectile of circular shape, used for ball collisions.
 * @see Projectile
 * @author andyhou
 */
public class CircleProjectile extends Projectile{
    private double radius;

    public CircleProjectile() {
        super();
    }

    public CircleProjectile(double radius, double mass, double x_velocity, double y_velocity, int x_position, int y_position) {
        super(mass, x_velocity, y_velocity, x_position, y_position);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    
}
