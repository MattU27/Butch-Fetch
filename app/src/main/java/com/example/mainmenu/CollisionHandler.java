package com.example.mainmenu;

import android.graphics.Rect;

public class CollisionHandler {

    private Rect image1; // Represents the hitbox of the ImageView
    private Rect obstacle; // Represents the obstacle's hitbox

    public CollisionHandler() {
        // Initialize hitboxes for the ImageView and the obstacle
        image1 = new Rect(0, 0, 0, 0); // Initialize with zero values
        obstacle = new Rect(460, 180, 680, 400);

        /* MED PHONE OBSTACLE EXACT COORDINATES:
            leftmost: 180 left, 400 right
            middle: 460 left, 680 right
            rightmost: 740 left, 960 right
         */
    }

    // Update positions of the hitboxes
    public void updatePositions(Rect imageViewHitbox) {
        // Update the hitbox of the ImageView
        image1.set(imageViewHitbox);

        // Update the position of the obstacle
        obstacle.offset(0, 10); // Adjust the offset for obstacle movement
    }

    // Check for collisions between the ImageView and the obstacle
    public boolean checkCollision() {
        return Rect.intersects(image1, obstacle);
    }

    // Handle collision between the ImageView and the obstacle
    public void handleCollision() {
        // Example action to handle collision (stop the obstacle)
        obstacle.offset(0, -10);
    }

    // Getters for the hitboxes
    public Rect getImage1() {
        return image1;
    }

    public Rect getObstacle() {
        return obstacle;
    }

    // Move the ImageView's hitbox left
    public void moveLeft() {
        image1.offset(-70, 0); // Adjust the offset according to your needs
    }

    // Move the ImageView's hitbox right
    public void moveRight() {
        image1.offset(70, 0); // Adjust the offset according to your needs
    }

    // Make the ImageView jump

}
