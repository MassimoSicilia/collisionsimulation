/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.test;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.collision.model.Projectile;
import edu.vanier.collision.simulation.Simulation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andyhou
 */
public class Driver {
    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Projectile p1 = new Projectile(10, 1);
        Projectile p2 = new Projectile(20, 2);
        Projectile p3 = new Projectile(30, 3);
        Projectile p4 = new Projectile(40, 4);
        
        Simulation sim = new Simulation();
        List<Projectile> projs = new ArrayList<>();
        projs.add(p1);
        projs.add(p2);
        projs.add(p3);
        projs.add(p4);
        sim.setProjectiles(projs);
        sim.setElasticity(100);
        sim.save();
    }
}
