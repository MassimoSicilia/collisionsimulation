/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

/**
 *
 * @author andyhou
 */
public class RectangleProjectile extends Projectile{
    private double height;
    private double width;

    public RectangleProjectile() {
        super();
    }

    public RectangleProjectile(double height, double width, double mass, double x_velocity, double y_velocity, double angle, int x_position, int y_position) {
        super(mass, x_velocity, y_velocity, angle, x_position, y_position);
        this.height = height;
        this.width = width;
    }
    
}
