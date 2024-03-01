package com.example.mainmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainGame extends AppCompatActivity {

    private CollisionHandler collisionHandler;
    private SimulationView simulationView;
    private ImageView imageView;
    private GestureDetector gestureDetector;
    private AnimationDrawable dogAnimation;

    private boolean isGameStarted = false;

    /*
    COMMENTS NI GIO DO NOT TOUCH

     what i could do for road scrolling could be similar to the translation code for the dog
     wherein we set the y translation to whateverthefuck value to make it scroll up BUT not before
     we spawn the student and everything (kailangan magwait ng onting delay for the translation, add a delay method for
     the translation in the updateGame method)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        isGameStarted = startGame();
    }

    // call this method to start the game, di ko na alam logic nung menus huhu
    private boolean startGame() {
        gestureDetector = new GestureDetector(this, new MyGestureListener());
        collisionHandler = new CollisionHandler();

        setContentView(R.layout.main_game);

        FrameLayout backgroundContainer = findViewById(R.id.backgroundContainer);
        backgroundContainer.setBackgroundResource(R.drawable.road);


        ConstraintLayout container = findViewById(R.id.container);
        simulationView = new SimulationView(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );

        container.addView(simulationView, layoutParams);

        imageView = findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        dogAnimation = (AnimationDrawable) imageView.getBackground();

        return isGameStarted = true;
    }


    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isGameStarted) {
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateGame();
                    simulationView.invalidate();
                    handler.postDelayed(this, 16);
                    dogAnimation.start();
                }
            });
        }
    }

    private void updateGame() {
        // Update positions and check for collisions
        collisionHandler.updatePositions(getImageViewHitbox());
        if (collisionHandler.checkCollision()) {
            collisionHandler.handleCollision();
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // Swipe right
                        moveAnimationRight();
                    } else {
                        // Swipe left
                        moveAnimationLeft();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private void moveAnimationLeft() {
        imageView.animate().translationXBy(-285).setDuration(100).start();
        collisionHandler.moveLeft();
    }

    private void moveAnimationRight() {
        imageView.animate().translationXBy(285).setDuration(100).start();
        collisionHandler.moveRight();
    }


    private Rect getImageViewHitbox() {
        // Get the hitbox of the ImageView
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        return new Rect(location[0], location[1], location[0] + imageView.getWidth(), location[1] + imageView.getHeight());
    }

    private class SimulationView extends View {

        public SimulationView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw the obstacle
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            Rect obstacleRect = collisionHandler.getObstacle();
            canvas.drawRect(obstacleRect, paint);
        }
    }
}