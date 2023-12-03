/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.vanier.collision.model.Simulation;
import edu.vanier.collision.serialize.CircleTypeAdapter;
import edu.vanier.collision.serialize.LineTypeAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author andyhou
 */
public class SimulationController extends Simulation{

    public static void save(Simulation simulation, File file) throws IOException{
        Writer writer = new FileWriter(file);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Circle.class, new CircleTypeAdapter())
                .registerTypeAdapter(Line.class, new LineTypeAdapter())
                .setPrettyPrinting()
                .create();
        writer.write(gson.toJson(simulation));
        writer.close();
    }

    public static Simulation load(File file) throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Circle.class, new CircleTypeAdapter())
                .registerTypeAdapter(Line.class, new LineTypeAdapter())
                .setPrettyPrinting().
                create();
        
        Simulation loadedSimulation = gson.fromJson(new FileReader(file),Simulation.class);
        return loadedSimulation;
    }
}
