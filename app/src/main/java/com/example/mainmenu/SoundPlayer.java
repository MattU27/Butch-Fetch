package com.example.mainmenu;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {
    private static MediaPlayer player;
    private static SoundPool soundPool;
    private static int sfx1, sfx2, sfx3, sfx4, sfx5, sfx6, sfx7, sfx8;
    private static int length;

    private static AudioAttributes aa = new AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_GAME)
            .build();

    public static void initialize(Context context) {
        if (soundPool == null) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
            sfx1 = soundPool.load(context, R.raw.buttonpress, 1);
            sfx2 = soundPool.load(context, R.raw.incoming_warning, 2);
            sfx3 = soundPool.load(context, R.raw.powerup_crunch, 3);
            sfx4 = soundPool.load(context, R.raw.trashcan_hit, 4);
            sfx5 = soundPool.load(context, R.raw.car_crash, 5);
            sfx6 = soundPool.load(context, R.raw.traffic_cone, 6);
            sfx7 = soundPool.load(context, R.raw.scream, 7);
            sfx8 = soundPool.load(context, R.raw.impact, 7);

        }
    }

    public static void playBGM(Context context) {
        player = MediaPlayer.create(context, R.raw.runningbgm1);
        player.seekTo(length);
        player.start();
    }

    public static void pauseBGM() {
        if (player != null && player.isPlaying()) {
            player.pause();
            length = player.getCurrentPosition();
        }
    }

    public static void stopBGM() {
        if (player != null) {
            player.stop();
            player.release();
            length = 0;
        }
    }

    public static void muteVolume(boolean isMute) {
        float volume = isMute ? 0.0f : 1.0f;
        if (player != null) {
            player.setVolume(volume, volume);
        }
    }


    public static void playSFX( boolean isMute, int sfx_number) {
        if (!isMute && soundPool != null) {
            switch (sfx_number) {
                case 1:
                    //button
                    soundPool.play(sfx1, 1, 1, 1, 0, 1);
                    break;
                case 2:
                    //warning_hit
                    soundPool.play(sfx2, 1, 1, 1, 0, 1);
                    break;
                case 3:
                    //treat_picked
                    soundPool.play(sfx3, 1, 1, 1, 0, 1);
                    break;
                case 4:
                    //trashcan_hit
                    soundPool.play(sfx4, 1, 1, 1, 0, 1);
                    break;
                case 5:
                    //Manhole_hit
                    soundPool.play(sfx7, 1, 1, 1, 0, 1);
                    break;
                case 6:
                    //cone_hit
                    soundPool.play(sfx6, 1, 1, 1, 0, 1);
                    break;

            }
        }
    }
}

