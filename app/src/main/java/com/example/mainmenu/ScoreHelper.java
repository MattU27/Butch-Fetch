package com.example.mainmenu;

public class ScoreHelper {
    private int runningScore = 0, pickupScore = 0, totalScore = 0;

    public void updateScore(long mTimeLeftInMills) {
        // Increment running score based on time left
        if (mTimeLeftInMills >= 61000) {
            runningScore += 1;
        } else if (mTimeLeftInMills >= 31000) {
            runningScore += 5;
        } else {
            runningScore += 10;
        }

        // Ensure that the running score does not exceed the maximum limit
        runningScore = Math.min(runningScore, 4800);

        // Calculate total score
        totalScore = runningScore + pickupScore;
    }

    public int
    getTotalScore() {
        return totalScore;
    }

    public void addPickupScore(int score) {
        pickupScore += score;
    }
}