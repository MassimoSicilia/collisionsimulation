/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.test;

import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.simulation.CollisionBox;
import edu.vanier.collision.simulation.PaneCollisionBox;
import edu.vanier.collision.simulation.Simulation;

/**
 *
 * @author andyhou
 */
public class Driver {

    public static void main(String[] args) {
        Projectile proj = new Projectile(10, 9);
        Projectile proj2 = new Projectile(20, 7);
        //Simulation sim = new Simulation(0.9);
        //System.out.println(sim.checkCollision(proj, proj2));

        // Create a collision box
        CollisionBox collisionBox = new PaneCollisionBox();
        Simulation simulation = new Simulation(collisionBox);

        System.out.println("Adding a projectile...");
        simulation.addProjectile();

        // Add more projectiles if needed
        // System.out.println("Adding another projectile...");
        // simulation.addProjectile();
        System.out.println("Removing a projectile...");
        simulation.removeProjectile();
    }
}
