package com.vltechristmas.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Random;
public class ConfigManager {
    private final JavaPlugin plugin;
    private final File configFile;
    private final File questFile;
    private FileConfiguration config;
    private FileConfiguration questConfig;
    private final FileConfiguration rewardsConfig;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.questFile = new File(plugin.getDataFolder(), "quest.yml");

        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();

        this.config = loadConfig(configFile);
        this.questConfig = loadConfig(questFile);
        this.rewardsConfig = loadConfig(new File(plugin.getDataFolder(), "rewards.yml"));

        saveDefaultRewardsConfig();
    }

    public void saveDefaultRewardsConfig() {
        File rewardsFile = new File(plugin.getDataFolder(), "rewards.yml");

        if (!rewardsFile.exists()) {
            plugin.saveResource("rewards.yml", false);
        }
    }

    private FileConfiguration loadConfig(File file) {
        FileConfiguration config = plugin.getConfig();

        if (!file.exists()) {
            plugin.saveResource(file.getName(), false);
        }

        try {
            config.load(file);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getQuestConfig() {
        return questConfig;
    }

    public void reloadConfig() {
        config = loadConfig(configFile);
        questConfig = loadConfig(questFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerQuest(PlayerQuest playerQuest) {
        // Buraya PlayerQuest nesnesini kaydetmek için gerekli kodu ekleyin
        // Örneğin: playerQuestMap.put(playerQuest.getPlayerName(), playerQuest);
        // Bu örnek kod, bir harita (map) kullanarak PlayerQuest nesnesini oyuncu adına göre kaydeder.
    }

    public String getRandomReward() {
        List<String> rewards = rewardsConfig.getStringList("rewards");
        if (rewards.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(rewards.size());

        return rewards.get(randomIndex);
    }


    public void saveQuestConfig() {
        try {
            questConfig.save(questFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}