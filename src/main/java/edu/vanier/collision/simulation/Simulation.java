/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.simulation;

import edu.vanier.collision.model.Projectile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author andyhou
 */
public class Simulation {
    private List<Projectile> projectiles;
    private CollisionBox collisionBox; // Use the interface type
    private double elasticity;

    public Simulation(CollisionBox collisionBox) {
        this.projectiles = new ArrayList<>();
        this.collisionBox = collisionBox;
    }

    public void addProjectile() {
        // Create a new Projectile with mass and radius
        Projectile projectile = new Projectile(2.0, 20.0, 100, 100, 10, 10, 10); // Provide appropriate values

        // Create a visual representation of the projectile (e.g., a circle)
        Circle ball = new Circle(projectile.getRadius());
        ball.setFill(javafx.scene.paint.Color.RED);

        // Set the initial position
        ball.setTranslateX(projectile.getX_position());
        ball.setTranslateY(projectile.getY_position());

        // Set the visual representation of the projectile
        projectile.setVisualRepresentation(ball);

        // Add the projectile to the list
        projectiles.add(projectile);

        // Add the circle to the collision box
        collisionBox.addProjectile(ball);
        
        // Test Driver
        System.out.println("Projectile added!");
    }

    public void removeProjectile() {
        if (!projectiles.isEmpty()) {
            Projectile lastProjectile = projectiles.get(projectiles.size() - 1);

            // Retrieve the visual representation (Circle) of the lastProjectile
            Circle lastProjectileVisual = lastProjectile.getVisualRepresentation();

            // Remove the visual representation from the collisionBox
            collisionBox.removeProjectile(lastProjectileVisual);

            // Remove the Projectile from the list
            projectiles.remove(lastProjectile);
            
            
            // Test Driver
            System.out.println("Projectile removed!");
        }
    }
    

    public void updateSimulation(double time) {
        updatePositions(time); // Update positions directly in the simulation
        handleCollisions();
    }

    private void updatePositions(double time) {
        for (Projectile projectile : projectiles) {
            // Update the position based on velocity
            int newX = (int) (projectile.getX_position() + projectile.getX_velocity() * time);
            int newY = (int) (projectile.getY_position() + projectile.getY_velocity() * time);

            projectile.setX_position(newX);
            projectile.setY_position(newY);
        }
    }

    private void handleCollisions() {
        for (int i = 0; i < projectiles.size(); i++) {
            for (int j = i + 1; j < projectiles.size(); j++) {
                Projectile projectile1 = projectiles.get(i);
                Projectile projectile2 = projectiles.get(j);

                if (checkCollision(projectile1, projectile2)) {
                    // Implement elastic collision logic here
                    // Update velocities accordingly
                    // The elasticity factor (this.elasticity) can be used to adjust the collision response
                    // e.g., update velocities based on mass and angle of collision
                }
            }
        }
    }

    public void save() {

    }

    public void reset() {

    }

    /*
    public boolean checkCollision(Projectile projectile1, Projectile projectile2){
        // collision occurs when the distance between the positions of both projectiles is equal to both of their radii added
        double collisionDistance = projectile1.getRadius() + projectile2.getRadius();
        
        // use pythagoream theorem
        double x_distance = projectile1.getX_position() + projectile2.getX_position();
        double y_distance = projectile1.getY_position() + projectile2.getY_position();
        double distance = Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));
        
        return collisionDistance == distance;

    }
     */
    private boolean checkCollision(Projectile projectile1, Projectile projectile2) {
        double collisionDistance = projectile1.getRadius() + projectile2.getRadius();
        double x_distance = projectile1.getX_position() - projectile2.getX_position();
        double y_distance = projectile1.getY_position() - projectile2.getY_position();
        double distance = Math.sqrt(x_distance * x_distance + y_distance * y_distance);

        return collisionDistance >= distance;
    }
    
    
}
