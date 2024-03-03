package com.example.mainmenu;
/*

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RacingGame extends ApplicationAdapter {
//kinomment out ko muna dito para tumakbo, pa tangal na lng pag gagamitin na - Munoz
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ObstacleRandomizer obstacleRandomizer;
    private Obstacle currentObstacle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        obstacleRandomizer = new ObstacleRandomizer();
        currentObstacle = obstacleRandomizer.generateObstacle();
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
        float obstacleY = currentObstacle.getSpeed() * deltaTime;
        currentObstacle = obstacleRandomizer.generateObstacle();
        // Handle collisions, check game over conditions, etc.
        // Update camera
    }

    private void draw() {
        batch.begin();

        // Input graphics
        batch.draw(obstacleTexture, currentObstacle.getLane() * LANE_WIDTH, obstacleY);
        // Change ObstacleTexture, Lane_Width, ObstacleY to the butch_fetch parameters
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
    */