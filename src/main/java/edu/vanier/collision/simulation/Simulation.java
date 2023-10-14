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
    
    public static void addProjectile(Projectile addedProjectile){
        projectiles.add(addedProjectile);
    }
    public static void removeProjectile(Projectile removedProjectile){
        projectiles.remove(removedProjectile);
    }
    
}
