package com.example.mainmenu;
import android.os.CountDownTimer;

public class TimerHelper {

    private CountDownTimer countDownTimer;
    private long mTimeLeftInMills;
    private TimerCallback callback;

    public TimerHelper(long startTimeInMillis, TimerCallback callback) {
        this.mTimeLeftInMills = startTimeInMillis;
        this.callback = callback;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMills, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMills = millisUntilFinished;
                callback.onTimerTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                callback.onTimerFinish();
            }
        }.start();
    }

    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public interface TimerCallback {
        void onTimerTick(long millisUntilFinished);
        void onTimerFinish();
    }
}