package com.example.mainmenu;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class ObstacleRandomizer {
    private static final int NUM_LANES = 3;
    private static final int INITIAL_SPEED = 5;
    private static final int MAX_SPEED = 15;
    private static final int SPEED_INCREASE_INTERVAL = 30;

    private Random random;
    private float obstacleSpeed;
    private long startTime;
    private SoundPlayer soundPlayer;
    private Context context;

    public ObstacleRandomizer(Context context, SoundPlayer soundPlayer) {
        this.context = context;
        this.soundPlayer = soundPlayer;

        random = new Random();
        obstacleSpeed = INITIAL_SPEED;
        startTime = System.currentTimeMillis();
    }

    public ObstacleRandomizer() {

    }


    public Obstacle generateObstacle() {
        updateSpeed();

        Obstacle.Type type = getRandomObstacleType();
        int lane = getRandomLane();

        return new Obstacle(type, lane, obstacleSpeed);
    }

    private Obstacle.Type getRandomObstacleType() {
        Obstacle.Type[] types = Obstacle.Type.values();
        return types[random.nextInt(types.length)];
    }

    private int getRandomLane() {
        return random.nextInt(NUM_LANES);
    }

    private void updateSpeed() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        if (elapsedTime % SPEED_INCREASE_INTERVAL == 0 && obstacleSpeed < MAX_SPEED) {
            obstacleSpeed += 2;
        }
    }

    private void playObstacleSound(Obstacle.Type type) {
        // di pa toh nagana, intayin ko pa maconnect randomizer sa collision
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isMute = preferences.getBoolean("isMute", false);
        switch (type) {
            case MANHOLE:
                soundPlayer.playSFX(isMute, 6);
                break;
            case CONE:
                soundPlayer.playSFX(isMute, 5);
                break;
            case TRASHCAN:
                soundPlayer.playSFX(isMute, 4);
                break;
            case FOG:
                //temporary
                soundPlayer.playSFX(isMute, 2);
                break;
            case TREATS:
                soundPlayer.playSFX(isMute, 3);
                break;
            //MANHOLE, CONE, TRASHCAN, FOG, TREATS, TRAFFIC_CONE
        }
    }
}