package com.example.mainmenu;

import android.graphics.Rect;

import java.util.Random;

public class CollisionHandler {

    private Rect image1; // Represents the hitbox of the ImageView
    private Rect blueObstacle; // Represents the blue obstacle's hitbox
    private Rect redObstacle; // Represents the red obstacle's hitbox
    private Rect greenObstacle; // Represents the green obstacle's hitbox
    private boolean isPaused = false; // Flag to indicate whether collision detection is paused or not

    public CollisionHandler() {
        // Initialize hitboxes for the ImageView and the obstacles
        image1 = new Rect(0, 0, 0, 0); // Initialize with zero values

        // Initialize hitboxes for the obstacles with random positions
        generateObstaclePositions();
    }

    // Update positions of the hitboxes
    public void updatePositions(Rect imageViewHitbox) {
        // Update the hitbox of the ImageView only if collision detection is not paused
        if (!isPaused) {
            image1.set(imageViewHitbox);

            // Update the position of the obstacles (if they move)
            // Example: obstacle.offset(0, 10); // Adjust the offset for obstacle movement
        }
    }

    // Check for collisions between the ImageView and the obstacles
    public boolean checkCollision() {
        return !isPaused && (Rect.intersects(image1, blueObstacle) || Rect.intersects(image1, redObstacle) || Rect.intersects(image1, greenObstacle));
    }

    // Handle collision between the ImageView and the obstacles
    public void handleCollision() {
        // Example action to handle collision (stop the obstacles)
        // obstacle.offset(0, -10);
    }

    // Getters for the hitboxes
    public Rect getImage1() {
        return image1;
    }

    // Getter methods for obstacle positions
    public Rect getBlueObstaclePosition() {
        return blueObstacle;
    }

    public Rect getRedObstaclePosition() {
        return redObstacle;
    }

    public Rect getGreenObstaclePosition() {
        return greenObstacle;
    }

    // Move the ImageView's hitbox left
    public void moveLeft() {
        image1.offset(-70, 0); // Adjust the offset according to your needs
    }

    // Move the ImageView's hitbox right
    public void moveRight() {
        image1.offset(70, 0); // Adjust the offset according to your needs
    }

    // Pause collision detection
    public void pause() {
        isPaused = true;
    }

    // Resume collision detection
    public void resume() {
        isPaused = false;
    }

    // Generate random positions for the obstacles
    private void generateObstaclePositions() {
        // Define the lane boundaries
        int leftLane = 100;
        int middleLane = 400;
        int rightLane = 700;

        // Define the obstacle height
        int obstacleHeight = 200;

        // Generate random initial vertical positions for the obstacles
        Random random = new Random();
        int initialVerticalPosition = -500; // Adjust the initial position as needed

        // Generate random positions for the obstacles in each lane
        int leftPosition = random.nextInt(100) + leftLane; // Random position in the left lane
        int middlePosition = random.nextInt(100) + middleLane; // Random position in the middle lane
        int rightPosition = random.nextInt(100) + rightLane; // Random position in the right lane

        // Initialize hitboxes for the obstacles with random positions in each lane
        blueObstacle = new Rect(leftPosition, initialVerticalPosition, leftPosition + 200, initialVerticalPosition + obstacleHeight);
        redObstacle = new Rect(middlePosition, initialVerticalPosition, middlePosition + 200, initialVerticalPosition + obstacleHeight);
        greenObstacle = new Rect(rightPosition, initialVerticalPosition, rightPosition + 200, initialVerticalPosition + obstacleHeight);
    }

    // Method to generate a blue obstacle
    public void generateBlueObstacle() {
        generateObstacle(blueObstacle);
    }

    // Method to generate a red obstacle
    public void generateRedObstacle() {
        generateObstacle(redObstacle);
    }

    // Method to generate a green obstacle
    public void generateGreenObstacle() {
        generateObstacle(greenObstacle);
    }

    // Utility method to generate an obstacle at a random position in the respective lane
    private void generateObstacle(Rect obstacle) {
        Random random = new Random();
        int laneWidth = 300; // Adjust the width of each lane as needed
        int lanePosition = random.nextInt(laneWidth); // Random position within the lane
        int laneOffset = 100; // Adjust the offset for each lane as needed

        // Update the obstacle's position within the lane
        obstacle.left = laneOffset + lanePosition;
        obstacle.top = -500; // Adjust the initial vertical position as needed
        obstacle.right = obstacle.left + 200; // Adjust the obstacle's width as needed
        obstacle.bottom = obstacle.top + 200; // Adjust the obstacle's height as needed
    }
}
