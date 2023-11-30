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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author 2276884
 */
public class LineTypeAdapter implements JsonSerializer<Line>, JsonDeserializer<Line>{

    @Override
    public JsonElement serialize(Line t, Type type, JsonSerializationContext jsc) {
        JsonObject line = new JsonObject();
        line.addProperty("endX", t.getEndX());
        line.addProperty("startX", t.getStartX());
        line.addProperty("endY", t.getEndY());
        line.addProperty("startY", t.getStartY());
        return jsc.serialize(line);
    }

    @Override
    public Line deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject deserializedLine = je.getAsJsonObject();
        Line line = new Line(deserializedLine.get("startX").getAsDouble(), deserializedLine.get("startY").getAsDouble(),
                deserializedLine.get("endX").getAsDouble(), deserializedLine.get("endY").getAsDouble());
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(1.5);
        return line;
    }
    
}
