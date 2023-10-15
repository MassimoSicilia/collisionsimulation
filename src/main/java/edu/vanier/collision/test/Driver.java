/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.test;

import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.simulation.Simulation;

/**
 *
 * @author andyhou
 */
public class Driver {
    public static void main(String[] args) {
        Projectile proj = new Projectile(10, 9);
        Projectile proj2 = new Projectile(20, 7);
        Simulation sim = new Simulation();
        System.out.println(sim.checkCollision(proj, proj2));
        
    }
}
