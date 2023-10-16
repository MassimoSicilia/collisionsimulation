/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.simulation;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.collision.model.Projectile;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Simulation class holds all information specific to a single simulation, namely the list of projectiles and the elasticity.
 * @author andyhou
 */
public class Simulation {

    private List<Projectile> projectiles;
    private double elasticity;

    /**
     * Creates an empty simulation.
     */
    public Simulation() {
    }

    /**
     * Creates a simulation with specified list of projectiles and elasticity.
     * @param projectiles
     * @param elasticity
     */
    public Simulation(List<Projectile> projectiles, double elasticity) {
        this.projectiles = projectiles;
        this.elasticity = elasticity;
    }

    /**
     * Adds projectile to the simulation.
     * @param addedProjectile
     */
    public void addProjectile(Projectile addedProjectile) {
        projectiles.add(addedProjectile);
    }

    /**
     * Removes projectile to the simulation.
     * @param removedProjectile
     */
    public void removeProjectile(Projectile removedProjectile) {
        projectiles.remove(removedProjectile);
    }

    /**
     * Saves current simulation into a csv file, note that the last row will only contain the elasticity of the simulation and not a Projectile object.
     * @throws IOException
     * @throws CsvDataTypeMismatchException
     * @throws CsvRequiredFieldEmptyException
     * @see https://opencsv.sourceforge.net/#writing_from_a_list_of_beans
     */
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter("simulation.csv"); // we should update this so it asks the user what they want to name the file
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(projectiles);
        // the last row will only hold the elasticity of the simulation in the angle column (first column), all other columns will be null
        writer.write(Double.toString(elasticity));
        writer.close();
    }

    /**
     * Removes all projectiles in the simulation.
     */
    public void reset() {
        projectiles.removeAll(projectiles);
    }

    /**
     * Checks if a collision has occurred between two projectiles.
     * @param projectile1
     * @param projectile2
     * @return
     */
    public boolean checkCollision(Projectile projectile1, Projectile projectile2) {
        // collision occurs when the distance between the positions of both projectiles is equal to both of their radii added
        double collisionDistance = projectile1.getRadius() + projectile2.getRadius();

        // use pythagoream theorem
        double x_distance = projectile1.getX_position() + projectile2.getX_position();
        double y_distance = projectile1.getY_position() + projectile2.getY_position();
        double distance = Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));

        return collisionDistance == distance;

    }

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
    public double getElasticity() {
        return elasticity;
    }

    /**
     *
     * @param elasticity
     */
    public void setElasticity(double elasticity) {
        this.elasticity = elasticity;
    }

}
