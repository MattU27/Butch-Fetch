package com.example.mainmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class activity_start_game extends AppCompatActivity implements pause_dialog.DialogCallback, TimerHelper.TimerCallback {

    // Declare class variables
    private TextView timerCount, scoreCount;
    private boolean isMuted, isMutedSFX;
    private boolean gamePaused = false;
    private boolean isGameStarted = false;
    private boolean isPauseDialogShown = false;

    // Declare objects for game components
    private pause_dialog pauseDialog;
    private TimerHelper timerHelper;
    private ScoreHelper scoreHelper;
    private CollisionHandler collisionHandler;
    private AnimationDrawable dogAnimation;
    private GestureDetector gestureDetector;

    private ImageView imageView;
    private SimulationView simulationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        isGameStarted = startGame(); // Start the game initialization process
        timerHelper = new TimerHelper(90000, this); // Initialize the timer helper
        timerHelper.startTimer(); // Start the game timer
        scoreHelper = new ScoreHelper(); // Initialize the score helper

        // Retrieve mute settings from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        isMuted = prefs.getBoolean("isMuted", false);
        isMutedSFX = prefs.getBoolean("isMutedSfx", false);

        // Start background music if not muted
        if (!isMuted) {
            SoundPlayer.playBGM(this);
        }

        // Initialize views
        timerCount = findViewById(R.id.countText);
        scoreCount = findViewById(R.id.scoreText);

        // Set onClick listener for pause button
        Button btnPause = findViewById(R.id.btn_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pause or resume game based on current game state
                if (!gamePaused) {
                    SoundPlayer.playSFX(activity_start_game.this, isMutedSFX, 1);
                    SoundPlayer.pauseBGM();
                    pauseGame();
                    gamePaused = true;
                    pauseDialog = new pause_dialog(activity_start_game.this, activity_start_game.this);
                    pauseDialog.show();
                    btnPause.setEnabled(false);
                    isPauseDialogShown = true; // Update flag when dialog is shown
                    // Initialize gesture detector here
                    gestureDetector = new GestureDetector(activity_start_game.this, new MyGestureListener());
                } else {
                    gamePaused = false;
                    if (pauseDialog != null && pauseDialog.isShowing()) {
                        pauseDialog.dismiss();
                    }
                    SoundPlayer.playBGM(activity_start_game.this);
                    btnPause.setEnabled(true);
                    resumeGame();
                }
            }
        });

        // Initialize pause dialog
        pauseDialog = new pause_dialog(activity_start_game.this, activity_start_game.this);
        pauseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!gamePaused) {
                    resumeGame();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnPause = findViewById(R.id.btn_pause);
        btnPause.setEnabled(true);
    }

    // Method to initialize the game components and layout
    private boolean startGame() {
        // Initialize gesture detector
        gestureDetector = new GestureDetector(this, new MyGestureListener());
        collisionHandler = new CollisionHandler();

        // Set layout and background resources
        setContentView(R.layout.activity_start_game);
        FrameLayout backgroundContainer = findViewById(R.id.backgroundContainer);
        backgroundContainer.setBackgroundResource(R.drawable.road);

        // Initialize simulation view
        ConstraintLayout container = findViewById(R.id.container2);
        simulationView = new activity_start_game.SimulationView(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        container.addView(simulationView, layoutParams);

        // Initialize dog animation
        imageView = findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        dogAnimation = (AnimationDrawable) imageView.getBackground();

        return isGameStarted = true;
    }

    // Method to handle window focus change events
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isGameStarted) {
            // Update game state and animation
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!gamePaused) {
                        updateGame();
                        simulationView.invalidate();
                        dogAnimation.start();
                    }
                    handler.postDelayed(this, 16);
                }
            });
        }
    }

    // Method to update game state
    private void updateGame() {
        if (!gamePaused) {
            collisionHandler.updatePositions(getImageViewHitbox());
            if (collisionHandler.checkCollision()) {
                collisionHandler.handleCollision();
            }
        }
    }

    // Method to handle touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gamePaused && !isPauseDialogShown) {
            // Allow swiping only when the game is not paused and the pause dialog is not shown
            return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
        } else {
            // Disable swiping when the game is paused or the pause dialog is shown
            return true;
        }
    }

    // Inner class representing a gesture listener for swiping gestures
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
                        moveAnimationRight(); // Move animation right on swipe right
                    } else {
                        moveAnimationLeft(); // Move animation left on swipe left
                    }
                    return true;
                }
            }
            return false;
        }
    }

    // Method to move the animation left
    private void moveAnimationLeft() {
        imageView.animate().translationXBy(-285).setDuration(100).start();
        collisionHandler.moveLeft();
    }

    // Method to move the animation right
    private void moveAnimationRight() {
        imageView.animate().translationXBy(285).setDuration(100).start();
        collisionHandler.moveRight();
    }

    // Method to get hitbox of the image view
    private Rect getImageViewHitbox() {
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        return new Rect(location[0], location[1], location[0] + imageView.getWidth(), location[1] + imageView.getHeight());
    }

    // Inner class representing the simulation view
    private class SimulationView extends View {

        public SimulationView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw obstacles
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            Rect obstacleRect = collisionHandler.getObstacle();
            canvas.drawRect(obstacleRect, paint);
        }
    }

    // Callback method for timer tick
    @Override
    public void onTimerTick(long millisUntilFinished) {
        // Update score and countdown text on timer tick
        updateScore(millisUntilFinished);
        updateCountDownText(millisUntilFinished);
    }

    // Callback method for timer finish
    @Override
    public void onTimerFinish() {
        // Code to execute when the timer finishes...
    }

    // Method to pause the game
    private void pauseGame() {
        timerHelper.pauseTimer();
        pauseAnimation();
        pauseCollisionHandler();
    }

    // Method to resume the game
    private void resumeGame() {
        gamePaused = false;
        timerHelper.startTimer();
        resumeAnimation();
        resumeCollisionHandler();
    }

    // Method to pause the animation
    private void pauseAnimation() {
        dogAnimation.stop();
    }

    // Method to resume the animation
    private void resumeAnimation() {
        if (!gamePaused) {
            dogAnimation.start();
        }
    }

    // Method to pause the collision handler
    private void pauseCollisionHandler() {
        collisionHandler.pause(); // Add a pause method in CollisionHandler
    }

    // Method to resume the collision handler
    private void resumeCollisionHandler() {
        if (!gamePaused) {
            // Resume collision handling
            collisionHandler.resume();
        }
    }

    // Method to update the countdown text
    private void updateCountDownText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        String timeleftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        timerCount.setText(timeleftFormatted);
    }

    // Method to update the score
    private void updateScore(long millisUntilFinished) {
        scoreHelper.updateScore(millisUntilFinished);
        int totalScore = scoreHelper.getTotalScore();
        String formattedScore = String.format("%06d", totalScore);
        scoreCount.setText(formattedScore);
    }

    // Callback method for pause dialog
    @Override
    public void onBooleanPassed(boolean value, boolean value2) {
        if (value) {
            resumeGame();
        }
        SoundPlayer.muteVolume(value2);
        Button btnPause = findViewById(R.id.btn_pause);
        btnPause.setEnabled(true);
        isPauseDialogShown = false; // Update flag when dialog is dismissed
    }
}

