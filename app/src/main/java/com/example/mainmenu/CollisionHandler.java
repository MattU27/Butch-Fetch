package com.example.mainmenu;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import java.util.Random;

public class CollisionHandler {

    private Rect image1; // Represents the hitbox of the ImageView
    private Rect blueObstacle; // Represents the blue obstacle's hitbox
    private Rect redObstacle; // Represents the red obstacle's hitbox
    private Rect greenObstacle; // Represents the green obstacle's hitbox
    private boolean isPaused = false; // Flag to indicate whether collision detection is paused or not

    private long lastObstacleGenerationTime = 5; // Track the time of the last obstacle generation
    private static final long OBSTACLE_GENERATION_INTERVAL = 1000; // 5 seconds in milliseconds

    private Handler obstacleHandler;
    private Runnable obstacleRunnable;

    public CollisionHandler() {
        // Initialize hitboxes for the ImageView and the obstacles
        image1 = new Rect(0, 0, 0, 0); // Initialize with zero values

        // Initialize hitboxes for the obstacles with random positions
        generateObstaclePositions();

        // Initialize the handler and runnable for obstacle generation
        obstacleHandler = new Handler();
        obstacleRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    generateObstacle();
                }
                // Schedule the next obstacle generation after the specified interval
                obstacleHandler.postDelayed(this, OBSTACLE_GENERATION_INTERVAL);
            }
        };
        // Start generating obstacles
        obstacleHandler.post(obstacleRunnable);
    }

    // Update positions of the hitboxes
    public void updatePositions(Rect imageViewHitbox) {
        // Update the hitbox of the ImageView only if collision detection is not paused
        if (!isPaused) {
            image1.set(imageViewHitbox);

            // Move obstacles down the screen
            moveObstaclesDown();
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

    public Rect getBlueObstacle() {
        return blueObstacle;
    }

    public Rect getRedObstacle() {
        return redObstacle;
    }

    public Rect getGreenObstacle() {
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
        // Define the obstacle height
        int obstacleHeight = 200;

        // Initialize hitboxes for the obstacles with random positions in each lane
        blueObstacle = new Rect(100, 0, 300, obstacleHeight);
        redObstacle = new Rect(400, 0, 600, obstacleHeight);
        greenObstacle = new Rect(700, 0, 900, obstacleHeight);
    }

    // Generate a new obstacle
    private void generateObstacle() {
        // Adjust the lane and position of the obstacle as needed
        Random random = new Random();
        int lane = random.nextInt(3); // Randomly select a lane (0, 1, or 2)
        int obstaclePosition = lane * 300 + 100; // Calculate the obstacle position based on the lane


        // Create the obstacle hitbox based on the selected lane and position
        // For example, if lane = 0, the obstacle will be in the left lane, if lane = 1, middle lane, etc.
        // Adjust the size of the obstacle as needed
        Rect obstacle;
        switch (lane) {
            case 0:
                obstacle = blueObstacle;
                break;
            case 1:
                obstacle = redObstacle;
                break;
            case 2:
                obstacle = greenObstacle;
                break;
            default:
                obstacle = blueObstacle;
                break;
        }
        obstacle.offsetTo(obstaclePosition, 0);
    }

    // Move obstacles down the screen
    private void moveObstaclesDown() {
        // Adjust the speed and direction of obstacle movement as needed
        int obstacleSpeed = 20;

        // Move each obstacle down by the specified speed
        blueObstacle.offset(0, obstacleSpeed);
        redObstacle.offset(0, obstacleSpeed);
        greenObstacle.offset(0, obstacleSpeed);
    }
}
