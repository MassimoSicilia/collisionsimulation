/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.animation;

import edu.vanier.collision.controllers.FXMLDefaultAnimationController;
import edu.vanier.collision.model.Projectile;
import java.util.List;
import java.util.ListIterator;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Hassimo
 */
public class DefaultAnimation {

    static AnimationTimer animation;
    static List<Projectile> circles;
    static Pane animationPane;
    public static AudioClip bouncingAudio = new AudioClip(DefaultAnimation.class.getResource("/audio/ballBounce.wav").toExternalForm());
    static boolean elasticity = true;
    static boolean animationPlaying;

    public static void setComponents(List<Projectile> circles, Pane animationPane) {
        DefaultAnimation.circles = circles;
        DefaultAnimation.animationPane = animationPane;
    }

    public static void play() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elasticityValue;
                if (elasticity == true) {
                    elasticityValue = 1;
                } else {
                    elasticityValue = 0.995;
                }
                // every frame, will see update positions and check for collisions
                for (ListIterator<Projectile> firstIterator = circles.listIterator(); firstIterator.hasNext();) {
                    Projectile projectile1 = firstIterator.next();
                    Circle ball = projectile1.getCircle();
                    double xVelocity = projectile1.getX_velocity();
                    double yVelocity = projectile1.getY_velocity();

                    //move the ball
                    ball.setCenterX(ball.getCenterX() + xVelocity);
                    ball.setCenterY(ball.getCenterY() + yVelocity);

                    projectile1.setX_velocity(projectile1.getX_velocity() * elasticityValue);
                    projectile1.setY_velocity(projectile1.getY_velocity() * elasticityValue);

                    //check for collisions
                    resolveBallWallCollision(projectile1, ball, xVelocity, yVelocity, animationPane);

                    for (ListIterator<Projectile> secondIterator = circles.listIterator(firstIterator.nextIndex()); secondIterator.hasNext();) {
                        Projectile projectile2 = secondIterator.next();
                        resolveBallCollision(projectile1, projectile2);
                    }
                }
            }
        };
        animation.start();
        animationPlaying = true;
    }
    
    public static void stop(){
        animation.stop();
        animationPlaying = false;
    }

    public static void resolveBallWallCollision(Projectile projectile, Circle ball, double xVelocity, double yVelocity, Pane animationPane) {
        //If the ball reaches the left or right border make the step negative
        if ((ball.getCenterX() <= ball.getRadius() && xVelocity < 0)
                || (ball.getCenterX() >= animationPane.getWidth() - ball.getRadius() && xVelocity > 0)) {
            projectile.setX_velocity(-xVelocity);
            bouncingAudio.play();
        }

        //If the ball reaches the bottom or top border make the step negative
        if ((ball.getCenterY() <= ball.getRadius() && yVelocity < 0)
                || (ball.getCenterY() >= animationPane.getHeight() - ball.getRadius() && yVelocity > 0)) {
            projectile.setY_velocity(-yVelocity);
            bouncingAudio.play();
        }
    }

    public static void resolveBallCollision(Projectile projectile1, Projectile projectile2) {
        double xVelocity = projectile1.getX_velocity();
        double yVelocity = projectile1.getY_velocity();
        double xVelocity2 = projectile2.getX_velocity();
        double yVelocity2 = projectile2.getY_velocity();
        double mass1 = projectile1.getMass();
        double mass2 = projectile2.getMass();
        Circle ball1 = projectile1.getCircle();
        Circle ball2 = projectile2.getCircle();
        double deltaX = (ball2.getCenterX() + ball2.getRadius()) - (ball1.getCenterX() + ball1.getRadius());
        double deltaY = (ball2.getCenterY() + ball2.getRadius()) - (ball1.getCenterY() + ball1.getRadius());

        //https://www.vobarian.com/collisions/2dcollisions2.pdf
        if (ball1.getBoundsInParent().intersects(ball2.getBoundsInParent())
                && deltaX * (xVelocity2 - xVelocity) + deltaY * (yVelocity2 - yVelocity) < 0) {

            bouncingAudio.play();

            Vector2D normal = new Vector2D(Math.abs(ball1.getCenterX() - ball2.getCenterX()), Math.abs(ball1.getCenterY() - ball2.getCenterY()));
            normal = normal.normalize();

            Vector2D tangent = new Vector2D(-normal.getY(), normal.getX());
            Vector2D velocity1 = new Vector2D(xVelocity, yVelocity);
            Vector2D velocity2 = new Vector2D(xVelocity2, yVelocity2);

            double velocity1_normal = normal.dotProduct(velocity1);
            double velocity2_normal = normal.dotProduct(velocity2);
            double velocity1_tangent = tangent.dotProduct(velocity1);
            double velocity2_tangent = tangent.dotProduct(velocity2);

            double velocity1_normal_final = (velocity1_normal * (mass1 - mass2) + 2 * mass2 * velocity2_normal) / (mass1 + mass2);
            double velocity2_normal_final = (velocity2_normal * (mass2 - mass1) + 2 * mass1 * velocity1_normal) / (mass1 + mass2);

            Vector2D velocity1_final = normal.scalarMultiply(velocity1_normal_final).add(tangent.scalarMultiply(velocity1_tangent));
            Vector2D velocity2_final = normal.scalarMultiply(velocity2_normal_final).add(tangent.scalarMultiply(velocity2_tangent));

            projectile1.setX_velocity(velocity1_final.getX());
            projectile1.setY_velocity(velocity1_final.getY());
            projectile2.setX_velocity(velocity2_final.getX());
            projectile2.setY_velocity(velocity2_final.getY());

        }

    }

    public static boolean isElasticity() {
        return elasticity;
    }

    public static void setElasticity(boolean elasticity) {
        DefaultAnimation.elasticity = elasticity;
    }

    public static boolean isAnimationPlaying() {
        return animationPlaying;
    }
}
