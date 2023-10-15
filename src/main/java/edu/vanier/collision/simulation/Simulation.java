/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.simulation;

import edu.vanier.collision.model.Projectile;
import java.util.List;

/**
 *
 * @author andyhou
 */
public class Simulation {
    private List<Projectile> projectiles;
    private double elasticity;
    
    public void addProjectile(Projectile addedProjectile){
        projectiles.add(addedProjectile);
    }
    
    public void removeProjectile(Projectile removedProjectile){
        projectiles.remove(removedProjectile);
    }
    
    public void save(){
        
    }
    
    public void reset(){
        
    }
    
    public boolean checkCollision(Projectile projectile1, Projectile projectile2){
        // collision occurs when the distance between the positions of both projectiles is equal to both of their radii added
        double collisionDistance = projectile1.getRadius() + projectile2.getRadius();
        
        // use pythagoream theorem
        double x_distance = projectile1.getX_position() + projectile2.getX_position();
        double y_distance = projectile1.getY_position() + projectile2.getY_position();
        double distance = Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));
        
        return collisionDistance == distance;

    }
    
}
