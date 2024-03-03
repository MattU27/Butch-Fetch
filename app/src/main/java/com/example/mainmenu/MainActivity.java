package com.example.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//wewz
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartGame = findViewById(R.id.btn_start_game);
        Button btnTutorial = findViewById(R.id.btn_tutorial);
        Button btnSettings = findViewById(R.id.btn_settings);
        Button btnAboutUs = findViewById(R.id.btn_about_us);

        btnStartGame.setOnClickListener(this);
        btnTutorial.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnAboutUs.setOnClickListener(this);
        SoundPlayer.initialize(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int viewId = view.getId();
        if (viewId == R.id.btn_start_game) {
            playSFXButton();
            intent = new Intent(this, activity_start_game.class);
        } else if (viewId == R.id.btn_tutorial) {
            playSFXButton();
            intent = new Intent(this, activity_tutorial.class);
        } else if (viewId == R.id.btn_settings) {
            playSFXButton();
            intent = new Intent(this, activity_settings.class);
        } else if (viewId == R.id.btn_about_us) {
            playSFXButton();
            intent = new Intent(this, activity_about_us.class);
        } else {
            return; // Return early if the view ID is not recognized
        }
        startActivity(intent);
    }

    // Method to navigate back to MainActivity
    public void navigateToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void playSFXButton() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isMutedSfx = prefs.getBoolean("isMutedSfx", false);
        // sfx_number calls the sfx track to be played, please view at SoundPlayer.class
        SoundPlayer.playSFX(  isMutedSfx, 1);
        }


    public void exitApp(View view) {
        finishAffinity(); // Finish this activity and all activities immediately below it in the current task
    }
}