/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author andyhou
 */
public class CircleTypeAdapter implements JsonSerializer<Circle>, JsonDeserializer<Circle> {

    /**
     * Serializes the Circle properties needed.
     * @param t
     * @param type
     * @param jsc
     * @return
     */
    @Override
    public JsonElement serialize(Circle t, Type type, JsonSerializationContext jsc) {
        JsonObject circle = new JsonObject();
        circle.addProperty("radius", t.getRadius());
        circle.addProperty("centerX", t.getCenterX());
        circle.addProperty("centerY", t.getCenterY());
        try {
            circle.addProperty("red", ((Color) t.getFill()).getRed());
            circle.addProperty("blue", ((Color) t.getFill()).getBlue());
            circle.addProperty("green", ((Color) t.getFill()).getGreen());
            circle.addProperty("opacity", ((Color) t.getFill()).getOpacity());
        } catch (Exception e) {
            circle.addProperty("imageURL", ((ImagePattern) t.getFill()).getImage().getUrl());
        }
        return jsc.serialize(circle);
    }

    /**
     * Deserializes the JSON into a Circle.
     * @param je
     * @param type
     * @param jdc
     * @return
     * @throws JsonParseException
     */
    @Override
    public Circle deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject deserializedCircle = je.getAsJsonObject();
        Circle circle = new Circle(
                deserializedCircle.get("centerX").getAsDouble(),
                deserializedCircle.get("centerY").getAsDouble(),
                deserializedCircle.get("radius").getAsDouble());
        try {
            circle.setFill(new Color(deserializedCircle.get("red").getAsDouble(),
                    deserializedCircle.get("green").getAsDouble(),
                    deserializedCircle.get("blue").getAsDouble(),
                    deserializedCircle.get("opacity").getAsDouble()));
            circle.setStroke(Color.BLACK);
        } catch (Exception e) {
            circle.setFill(new ImagePattern(new Image(deserializedCircle.get("imageURL").getAsString())));
        }
        return circle;
    }

}
