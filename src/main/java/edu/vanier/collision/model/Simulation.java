/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.model;

import java.util.List;
import javafx.scene.image.Image;

/**
 * Simulation class holds all information specific to a single simulation,
 * namely the list of projectiles and the elasticity.
 *
 * @author andyhou
 */
public class Simulation{

    private List<Projectile> projectiles;
    private boolean elasticity;
    private boolean isDefault;

    /**
     * Creates an empty simulation.
     */
    public Simulation() {
    }
    
    // constructors
    
    /**
     * Creates a simulation with specified list of projectiles and elasticity.
     *
     * @param projectiles
     * @param elasticity
     * @param isDefault
     */
    public Simulation(List<Projectile> projectiles, boolean elasticity, boolean isDefault) {
        this.projectiles = projectiles;
        this.elasticity = elasticity;
        this.isDefault = isDefault;
    }

    /**
     *
     * @param projectiles
     * @param elasticity
     * @param isDefault
     * @param background
     * @param ballImage
     */
    public Simulation(List<Projectile> projectiles, boolean elasticity, boolean isDefault, Image background, Image ballImage) {
        this.projectiles = projectiles;
        this.elasticity = elasticity;
        this.isDefault = isDefault;
    }

    // getters and setters
    
    /**
     *
     * @return
     */
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     *
     * @param projectiles
     */
    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    /**
     *
     * @return
     */
    public boolean getElasticity() {
        return elasticity;
    }

    /**
     *
     * @param elasticity
     */
    public void setElasticity(boolean elasticity) {
        this.elasticity = elasticity;
    }

    /**
     *
     * @return
     */
    public boolean isDefault() {
        return isDefault;
    }
    
}
