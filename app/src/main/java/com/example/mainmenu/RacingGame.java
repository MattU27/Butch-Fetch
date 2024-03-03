package com.example.mainmenu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RacingGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ObstacleRandomizer obstacleRandomizer;
    private Obstacle currentObstacle;
    private Texture obstacleTexture; // Declare obstacleTexture
    private float obstacleY; // Declare obstacleY
    private static final float LANE_WIDTH = 100; // Declare and assign value to LANE_WIDTH

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        obstacleRandomizer = new ObstacleRandomizer();
        currentObstacle = obstacleRandomizer.generateObstacle();
        obstacleTexture = new Texture("path_to_your_texture"); // Initialize obstacleTexture with your texture file
        obstacleY = 0; // Initialize obstacleY
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }

    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        // Move the obstacle
        obstacleY += currentObstacle.getSpeed() * deltaTime; // Update obstacleY
        currentObstacle = obstacleRandomizer.generateObstacle();
        // Handle collisions, check game over conditions, etc.
        // Update camera
    }

    private void draw() {
        batch.begin();

        // Input graphics
        batch.draw(obstacleTexture, currentObstacle.getLane() * LANE_WIDTH, obstacleY);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        obstacleTexture.dispose(); // Dispose texture when no longer needed
    }
}
