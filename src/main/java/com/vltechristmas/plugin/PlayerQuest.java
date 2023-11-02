package com.vltechristmas.plugin;

public class PlayerQuest {
    private String playerName;
    private String currentQuest;
    private int questProgress;

    public PlayerQuest(String playerName, String currentQuest, int questProgress) {
        this.playerName = playerName;
        this.currentQuest = currentQuest;
        this.questProgress = questProgress;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCurrentQuest() {
        return currentQuest;
    }

    public int getQuestProgress() {
        return questProgress;
    }

    public void setCurrentQuest(String currentQuest) {
        this.currentQuest = currentQuest;
        this.questProgress = 0;
    }

    public void setQuestProgress(int questProgress) {
        this.questProgress = questProgress;
    }

    public void incrementQuestProgress() {
        this.questProgress++;
    }
}