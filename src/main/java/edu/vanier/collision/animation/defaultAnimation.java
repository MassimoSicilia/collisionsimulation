/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.animation;

import edu.vanier.collision.model.CircleProjectile;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Hassimo
 */
public class defaultAnimation {

    static Timeline timeline;

    public static void play(List<CircleProjectile> circles, Pane animationPane) {
        timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

            double dx = 4; //Step on x or velocity
            double dy = 2; //Step on y

            double dx2 = 4;
            double dy2 = -1;

            double mass1 = 1;
            double mass2 = 1;

            @Override
            public void handle(ActionEvent t) {
                for (CircleProjectile firstProj : circles) {
                    Circle firstBall = firstProj.getCircleProjectile();
                    firstBall.setLayoutX(firstBall.getLayoutX() + dx);
                    firstBall.setLayoutY(firstBall.getLayoutY() + dy);
                    Bounds bounds = animationPane.getBoundsInLocal();

                        //If the ball reaches the left or right border make the step negative
                        if (firstBall.getLayoutX() <= (bounds.getMinX() + firstBall.getRadius())
                                || firstBall.getLayoutX() >= (bounds.getMaxX() - firstBall.getRadius())) {

                            dx = -dx;

                        }

                        //If the ball reaches the bottom or top border make the step negative
                        if (firstBall.getLayoutY() >= (bounds.getMaxY() - firstBall.getRadius())
                                || firstBall.getLayoutY() <= (bounds.getMinY() + firstBall.getRadius())) {

                            dy = -dy;

                        }
                    for (CircleProjectile secondProj : circles) {
                        Circle secondBall = secondProj.getCircleProjectile();
                        //move the ball
                        if (firstBall == secondBall) {
                            continue;
                        }

                        if (secondBall.getLayoutX() <= (bounds.getMinX() + secondBall.getRadius())
                                || secondBall.getLayoutX() >= (bounds.getMaxX() - secondBall.getRadius())) {

                            dx2 = -dx2;

                        }

                        //If the ball reaches the bottom or top border make the step negative
                        if (secondBall.getLayoutY() >= (bounds.getMaxY() - secondBall.getRadius())
                                || secondBall.getLayoutY() <= (bounds.getMinY() + secondBall.getRadius())) {

                            dy2 = -dy2;

                        }

                        //https://www.vobarian.com/collisions/2dcollisions2.pdf
                        if (firstBall.getBoundsInParent().intersects(secondBall.getBoundsInParent())) {
                            Vector2D normal = new Vector2D(Math.abs(firstBall.getLayoutX() - secondBall.getLayoutX()), Math.abs(firstBall.getLayoutY() - secondBall.getLayoutY()));
                            normal = normal.normalize();

                            Vector2D tangent = new Vector2D(-normal.getY(), normal.getX());
                            Vector2D velocity1 = new Vector2D(dx, dy);
                            Vector2D velocity2 = new Vector2D(dx2, dy2);

                            double velocity1_normal = normal.dotProduct(velocity1);
                            double velocity2_normal = normal.dotProduct(velocity2);
                            double velocity1_tangent = tangent.dotProduct(velocity1);
                            double velocity2_tangent = tangent.dotProduct(velocity2);

                            double velocity1_normal_final = (velocity1_normal * (mass1 - mass2) + 2 * mass2 * velocity2_normal) / (mass1 + mass2);
                            double velocity2_normal_final = (velocity2_normal * (mass2 - mass1) + 2 * mass1 * velocity1_normal) / (mass1 + mass2);

                            Vector2D velocity1_final = normal.scalarMultiply(velocity1_normal_final).add(tangent.scalarMultiply(velocity1_tangent));
                            Vector2D velocity2_final = normal.scalarMultiply(velocity2_normal_final).add(tangent.scalarMultiply(velocity2_tangent));

                            dx = velocity1_final.getX();
                            dy = velocity1_final.getY();
                            dx2 = velocity2_final.getX();
                            dy2 = velocity2_final.getY();

                        }

                    }
                }
            }
        }
        ));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
