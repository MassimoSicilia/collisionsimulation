/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.collision.animation;

import edu.vanier.collision.model.Projectile;
import java.util.List;
import java.util.ListIterator;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Hassimo
 */
public class Animation {

    AnimationTimer animation;
    List<Projectile> circles;
    Pane animationPane;

    static public AudioClip bouncingAudio;
    boolean elastic = true;

    /**
     * Default constructor to create Animation object.
     */
    public Animation() {
    }

    /**
     * Creates Animation object with a list of circles and the Pane where the animation will occur.
     * @param circles
     * @param animationPane
     */
    public Animation(List<Projectile> circles, Pane animationPane) {
        this.circles = circles;
        this.animationPane = animationPane;
    }

    /**
     * Plays the animation.
     */
    public void play() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elasticityValue;
                if (elastic == true) {
                    elasticityValue = 1;
                } else {
                    // elasticityValue will slowly reduce the velocities of the projectiles until it reaches 0.
                    elasticityValue = 0.995;
                }
                // every frame, will see update positions and check for collisions
                for (ListIterator<Projectile> firstIterator = circles.listIterator(); firstIterator.hasNext();) {
                    Projectile projectile1 = firstIterator.next();
                    Circle ball = projectile1.getCircle();
                    double xVelocity = projectile1.getX_velocity();
                    double yVelocity = projectile1.getY_velocity();

                    // move the ball
                    ball.setCenterX(ball.getCenterX() + xVelocity);
                    ball.setCenterY(ball.getCenterY() + yVelocity);
                    
                    projectile1.setX_velocity(projectile1.getX_velocity() * elasticityValue);
                    projectile1.setY_velocity(projectile1.getY_velocity() * elasticityValue);

                    // check for collisions
                    resolveBallWallCollision(projectile1, ball, xVelocity, yVelocity, animationPane);
                }

                // update direction arrow outside of the loop
                for (Projectile projectile : circles) {
                    // Update direction arrow
                    projectile.updateDirectionArrow();
                }

                for (ListIterator<Projectile> firstIterator = circles.listIterator(); firstIterator.hasNext();) {
                    Projectile projectile1 = firstIterator.next();

                    for (ListIterator<Projectile> secondIterator = circles.listIterator(firstIterator.nextIndex()); secondIterator.hasNext();) {
                        Projectile projectile2 = secondIterator.next();
                        resolveBallCollision(projectile1, projectile2);
                    }
                }
            }
        };
        animation.start();
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        animation.stop();
    }

    /**
     * Checks for collisions between all the projectiles and the border of the animationPane.
     * @param projectile
     * @param ball
     * @param xVelocity
     * @param yVelocity
     * @param animationPane
     */
    public void resolveBallWallCollision(Projectile projectile, Circle ball, double xVelocity, double yVelocity, Pane animationPane) {
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

    /**
     * Checks for collisions between the projectiles and updates their velocities if there is a collision.
     * @param projectile1
     * @param projectile2
     */
    public void resolveBallCollision(Projectile projectile1, Projectile projectile2) {
        double mass1 = projectile1.getMass();
        double mass2 = projectile2.getMass();

        // All steps followed from https://www.vobarian.com/collisions/2dcollisions2.pdf
        Vector2D velocity1 = new Vector2D(projectile1.getX_velocity(), projectile1.getY_velocity());
        Vector2D velocity2 = new Vector2D(projectile2.getX_velocity(), projectile2.getY_velocity());
        Vector2D relativePosition = new Vector2D(projectile2.getCircle().getCenterX() - projectile1.getCircle().getCenterX(),
                projectile2.getCircle().getCenterY() - projectile1.getCircle().getCenterY());

        if (projectile1.getCircle().getBoundsInParent().intersects(projectile2.getCircle().getBoundsInParent())
                && relativePosition.dotProduct(velocity2.subtract(velocity1)) < 0) {

            Vector2D normal = relativePosition.normalize();
            Vector2D tangent = new Vector2D(-normal.getY(), normal.getX());

            double velocity1_normal_final = (normal.dotProduct(velocity1) * (mass1 - mass2) + 2 * mass2 * normal.dotProduct(velocity2)) / (mass1 + mass2);
            double velocity2_normal_final = (normal.dotProduct(velocity2) * (mass2 - mass1) + 2 * mass1 * normal.dotProduct(velocity1)) / (mass1 + mass2);

            Vector2D velocity1_final = normal.scalarMultiply(velocity1_normal_final).add(tangent.scalarMultiply(velocity1.dotProduct(tangent)));
            Vector2D velocity2_final = normal.scalarMultiply(velocity2_normal_final).add(tangent.scalarMultiply(velocity2.dotProduct(tangent)));

            projectile1.setX_velocity(velocity1_final.getX());
            projectile1.setY_velocity(velocity1_final.getY());
            projectile2.setX_velocity(velocity2_final.getX());
            projectile2.setY_velocity(velocity2_final.getY());
            bouncingAudio.play();

        }

    }

    /**
     * Returns the elasticity of the animation.
     * @return
     */
    public boolean isElastic() {
        return elastic;
    }

    /**
     * Set the elasticity of the animation.
     * @param elastic
     */
    public void setElastic(boolean elastic) {
        this.elastic = elastic;
    }

    /**
     * Sets the audio to play when there is a collision.
     * @param bouncingAudio
     */
    public static void setBouncingAudio(AudioClip bouncingAudio) {
        Animation.bouncingAudio = bouncingAudio;
    }
    
}
