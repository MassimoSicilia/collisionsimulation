package edu.vanier.collision.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class MyFirstGame extends Application {

    @Override
    public void start(Stage stage) {

        Pane canvas = new Pane();
        Scene scene = new Scene(canvas, 300, 300, Color.ALICEBLUE);
        Circle ball = new Circle(10, Color.CADETBLUE);
        ball.relocate(200, 10);

        Circle ball2 = new Circle(10, Color.BLACK);
        ball2.relocate(10, 20);

        canvas.getChildren().addAll(ball, ball2);

        stage.setTitle("Animated Ball");
        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

            double dx = -4; //Step on x or velocity
            double dy = 2; //Step on y

            double dx2 = 4;
            double dy2 = -1;
            
            double mass1 = 1;
            double mass2 = 1;

            @Override
            public void handle(ActionEvent t) {
                //move the ball
                ball.setLayoutX(ball.getLayoutX() + dx);
                ball.setLayoutY(ball.getLayoutY() + dy);

                ball2.setLayoutX(ball2.getLayoutX() + dx2);
                ball2.setLayoutY(ball2.getLayoutY() + dy2);

                Bounds bounds = canvas.getBoundsInLocal();
                        

                //If the ball reaches the left or right border make the step negative
                if (ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius())
                        || ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius())) {

                    dx = -dx;

                }

                //If the ball reaches the bottom or top border make the step negative
                if (ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius())
                        || ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius())) {

                    dy = -dy;

                }

                if (ball2.getLayoutX() <= (bounds.getMinX() + ball2.getRadius())
                        || ball2.getLayoutX() >= (bounds.getMaxX() - ball2.getRadius())) {

                    dx2 = -dx2;

                }

                //If the ball reaches the bottom or top border make the step negative
                if (ball2.getLayoutY() >= (bounds.getMaxY() - ball2.getRadius())
                        || ball2.getLayoutY() <= (bounds.getMinY() + ball2.getRadius())) {

                    dy2 = -dy2;

                }
                
                //https://www.vobarian.com/collisions/2dcollisions2.pdf
                if (ball.getBoundsInParent().intersects(ball2.getBoundsInParent())){
                    Vector2D normal = new Vector2D(Math.abs(ball.getLayoutX() - ball2.getLayoutX()),Math.abs(ball.getLayoutY() - ball2.getLayoutY()));
                    normal = normal.normalize();
                    
                    Vector2D tangent = new Vector2D(-normal.getY(), normal.getX());
                    Vector2D velocity1 = new Vector2D(dx, dy);
                    Vector2D velocity2 = new Vector2D(dx2, dy2);
                    
                    double velocity1_normal = normal.dotProduct(velocity1);
                    double velocity2_normal = normal.dotProduct(velocity2);
                    double velocity1_tangent = tangent.dotProduct(velocity1);
                    double velocity2_tangent = tangent.dotProduct(velocity2);
                    
                    double velocity1_normal_final = (velocity1_normal * (mass1 - mass2) + 2 * mass2 * velocity2_normal)/(mass1+mass2);
                    double velocity2_normal_final = (velocity2_normal * (mass2 - mass1) + 2 * mass1 * velocity1_normal)/(mass1+mass2);
                    
                    Vector2D velocity1_final = normal.scalarMultiply(velocity1_normal_final).add(tangent.scalarMultiply(velocity1_tangent));
                    Vector2D velocity2_final = normal.scalarMultiply(velocity2_normal_final).add(tangent.scalarMultiply(velocity2_tangent));

                    dx = velocity1_final.getX();
                    dy = velocity1_final.getY();
                    dx2 = velocity2_final.getX();
                    dy2 = velocity2_final.getY();
                    
                }

            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
