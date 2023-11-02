package com.vltechristmas.plugin;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Random;

public class QuestService {
    private final FileConfiguration config;

    public QuestService(FileConfiguration config) {
        this.config = config;
    }

    public String getRandomQuest() {
        List<String> quests = config.getStringList("quests");
        if (quests.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(quests.size());

        return quests.get(randomIndex);
    }

    // Yeni metod
    public PlayerQuest getPlayerQuest(String playerName) {
        // Buraya oyuncunun PlayerQuest nesnesini alacak kodu ekleyin
        // Örneğin: return playerQuestMap.get(playerName);
        // Bu örnek kod, bir oyuncunun adına göre PlayerQuest nesnesini bir harita (map) üzerinden alır.
        return null;
    }
}