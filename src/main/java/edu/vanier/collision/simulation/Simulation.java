/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.simulation;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import edu.vanier.collision.model.CircleProjectile;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.model.RectangleProjectile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulation class holds all information specific to a single simulation,
 * namely the list of projectiles and the elasticity.
 *
 * @author andyhou
 */
public class Simulation {

    private List<Projectile> projectiles;
    private boolean elasticity;

    /**
     * Creates an empty simulation.
     */
    public Simulation() {
    }

    /**
     * Creates a simulation with specified list of projectiles and elasticity.
     *
     * @param projectiles
     * @param elasticity
     */
    public Simulation(List<Projectile> projectiles, boolean elasticity) {
        this.projectiles = projectiles;
        this.elasticity = elasticity;
    }

    /**
     * Adds projectile to the simulation.
     *
     * @param addedProjectile
     */
    public void addProjectile(Projectile addedProjectile) {
        projectiles.add(addedProjectile);
    }

    /**
     * Removes projectile to the simulation.
     *
     * @param removedProjectile
     */
    public void removeProjectile(Projectile removedProjectile) {
        projectiles.remove(removedProjectile);
    }

    /**
     * Saves current simulation into a csv file, note that the last row will
     * only contain the elasticity of the simulation and not a Projectile
     * object.
     *
     * @throws IOException
     * @throws CsvDataTypeMismatchException
     * @throws CsvRequiredFieldEmptyException
     * @see
     * <a href="https://opencsv.sourceforge.net/#writing_from_a_list_of_beans">OpenCSV
     * Documentation</a>
     */
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter("simulation.csv"); // we should update this so it asks the user what they want to name the file
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
        beanToCsv.write(projectiles);
        // the last row will only hold the elasticity of the simulation in the angle column (first column), all other columns will be null
        writer.write(Boolean.toString(elasticity));
        writer.close();
    }

    // https://opencsv.sourceforge.net/#reading_into_an_array_of_strings
    public static Simulation load(File file) throws FileNotFoundException, IOException, CsvValidationException {
        Simulation loadedSimulation = new Simulation();
        List<Projectile> loadedProjectiles = new ArrayList<>();
        CSVReader reader = new CSVReaderBuilder(new FileReader(file.getName())).build();
        String[] nextLine = reader.readNext(); // first row will only contain the titles of each column
        if (nextLine.length == 7) {
            while ((nextLine = reader.readNext()) != null) {
                // formatted as [angle][mass][radius][x_position][x_velocity][y_position][y_velocity] for circleProjectile
                if (nextLine.length == 1) { // last row is always the elasticity
                    loadedSimulation.setElasticity(Boolean.getBoolean(nextLine[0]));
                    break;
                }
                loadedProjectiles.add(new CircleProjectile(Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[6]), Double.parseDouble(nextLine[0]), Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[5])));
                loadedSimulation.setProjectiles(loadedProjectiles);
            }
        } else if (nextLine.length == 8) {
            while ((nextLine = reader.readNext()) != null) {
                // formatted as [angle][height][mass][width][x_position][x_velocity][y_position][y_velocity] for rectangleProjectile
                if (nextLine.length == 1) { // last row is always the elasticity
                    loadedSimulation.setElasticity(Boolean.getBoolean(nextLine[0]));
                    break;
                }
                loadedProjectiles.add(new RectangleProjectile(Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[3]), Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[5]), Double.parseDouble(nextLine[7]), Double.parseDouble(nextLine[0]), Integer.parseInt(nextLine[4]), Integer.parseInt(nextLine[6])));
                loadedSimulation.setProjectiles(loadedProjectiles);
            }
        }
        return loadedSimulation;
    }

    /**
     * Removes all projectiles in the simulation.
     */
    public void reset() {
        projectiles.removeAll(projectiles);
    }

    /**
     * Checks if a collision has occurred between two projectiles.
     *
     * @param circle1
     * @param circle2
     * @return
     */
    public boolean checkCircleCollision(CircleProjectile circle1, CircleProjectile circle2) { //useless
        // collision occurs when the distance between the positions of both projectiles is equal to both of their radii added
        double collisionDistance = circle1.getRadius() + circle2.getRadius();

        // use pythagoream theorem
        double x_distance = circle1.getX_position() + circle2.getX_position();
        double y_distance = circle1.getY_position() + circle2.getY_position();
        double distance = Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));

        return collisionDistance == distance;

    }

    public boolean checkRectangleCollision(RectangleProjectile rectangle1, RectangleProjectile rectangle2) { //useless
        return true;
    }

    public void collisionUpdate(CircleProjectile circle1, CircleProjectile circle2, boolean elasticity) { //useless

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

}
