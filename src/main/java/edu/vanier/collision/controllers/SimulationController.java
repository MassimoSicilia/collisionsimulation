/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.vanier.collision.model.Simulation;
import edu.vanier.collision.serialize.CircleTypeAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import javafx.scene.shape.Circle;

/**
 *
 * @author andyhou
 */
public class SimulationController extends Simulation{

    public static void save(Simulation simulation, File file) throws IOException{
        Writer writer = new FileWriter(file);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Circle.class, new CircleTypeAdapter())
                .setPrettyPrinting().create();
        writer.write(gson.toJson(simulation));
        writer.close();
    }

    public static Simulation load(File file) throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Circle.class, new CircleTypeAdapter())
                .setPrettyPrinting().create();
        
        Simulation loadedSimulation = gson.fromJson(new FileReader(file),Simulation.class);
        return loadedSimulation;
    }
}
