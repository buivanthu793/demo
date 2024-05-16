package gui_package.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class wordleStatisticsController {
    private int count_1;
    private int count_2;
    private int count_3;
    private int count_4;
    private int count_5;
    private int count_6;
    private int gameCount;
    private int currStreak;
    private int bestStreak;
    private int totalWins;
    private double winningRate;
    private double guessDist;

    @FXML
    private Label counter_1;

    @FXML
    private Label counter_2;

    @FXML
    private Label counter_3;

    @FXML
    private Label counter_4;

    @FXML
    private Label counter_5;

    @FXML
    private Label counter_6;

    @FXML
    private Label gamePlayed;

    @FXML
    private Label guessDistribution;

    @FXML
    private Label streakBest;

    @FXML
    private Label streakCurrent;

    @FXML
    private Label winRate;

    public wordleStatisticsController() {
        count_1 = count_2 = count_3 = count_4 = count_5 = count_6 = 0;
        currStreak = bestStreak = gameCount = totalWins = 0;
        winningRate = guessDist = 0.0;
    }

    public void update() {
        counter_1.setText(String.valueOf(count_1));
        counter_2.setText(String.valueOf(count_2));
        counter_3.setText(String.valueOf(count_3));
        counter_4.setText(String.valueOf(count_4));
        counter_5.setText(String.valueOf(count_5));
        counter_6.setText(String.valueOf(count_6));

        if (gameCount != 0) {
            winningRate = ((double) totalWins / gameCount) * 100;
        } else {
            winningRate = 0;
        }

        if (totalWins != 0) {
            guessDist = (double) (count_1 + count_2 * 2 + count_3 * 3 + count_4 * 4 + count_5 * 5 + count_6 * 6) / totalWins;
        } else {
            guessDist = 0;
        }

        gamePlayed.setText(String.valueOf(gameCount));
        winRate.setText(String.format("%.1f", winningRate) + "%");
        streakCurrent.setText(String.valueOf(currStreak));
        streakBest.setText(String.valueOf(bestStreak));
        guessDistribution.setText(String.format("%.1f", guessDist));
    }

    public void addCount(int count) {
        switch (count) {
            case 1 -> count_1++;
            case 2 -> count_2++;
            case 3 -> count_3++;
            case 4 -> count_4++;
            case 5 -> count_5++;
            case 6 -> count_6++;
        }
    }

    public void setCount_1(int count_1) {
        this.count_1 = count_1;
    }

    public int getCount_1() {
        return count_1;
    }

    public void setCount_2(int count_2) {
        this.count_2 = count_2;
    }

    public int getCount_2() {
        return count_2;
    }

    public void setCount_3(int count_3) {
        this.count_3 = count_3;
    }

    public int getCount_3() {
        return count_3;
    }

    public void setCount_4(int count_4) {
        this.count_4 = count_4;
    }

    public int getCount_4() {
        return count_4;
    }

    public void setCount_5(int count_5) {
        this.count_5 = count_5;
    }

    public int getCount_5() {
        return count_5;
    }

    public void setCount_6(int count_6) {
        this.count_6 = count_6;
    }

    public int getCount_6() {
        return count_6;
    }

    public void setCurrStreak(int currStreak) {
        this.currStreak = currStreak;
    }

    public int getCurrStreak() {
        return currStreak;
    }

    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setWinningRate(double winningRate) {
        this.winningRate = winningRate;
    }

    public double getWinningRate() {
        return winningRate;
    }

    public void setGuessDist(double guessDist) {
        this.guessDist = guessDist;
    }

    public double getGuessDist() {
        return guessDist;
    }
}