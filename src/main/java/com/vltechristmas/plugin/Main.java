package com.vltechristmas.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.Inventory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main extends JavaPlugin implements Listener {
    private ConfigManager configManager;
    private QuestService questService;
    private List<PlayerQuest> playerQuests;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        questService = new QuestService(configManager.getConfig());
        playerQuests = new ArrayList<>();
        new QuestPlaceholder(this).register();
        configManager.saveDefaultRewardsConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(this, this);

        startDailyQuestTask();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        // Oyuncunun görevini al
        PlayerQuest playerQuest = questService.getPlayerQuest(playerName);

        // Oyuncunun görevi null değilse işlem yap
        if (playerQuest != null) {
            String currentQuest = playerQuest.getCurrentQuest();

            // Null kontrolü eklendi
            if (currentQuest != null) {
                player.sendMessage("Şu anda göreviniz: " + currentQuest);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Sağ tıklama etkinliğini kontrol et
        if (event.getAction().name().contains("RIGHT")) {
            // Kafa kontrolü
            if (player.getInventory().getItemInMainHand().getType().name().contains("SKULL_ITEM")) {
                // Ödül verme işlemi
                giveRandomReward(player);

                // Kafayı temizleme
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
    }

    private void giveRandomReward(Player player) {
        // rewards.yml dosyasından rastgele bir ödül al
        String randomReward = configManager.getRandomReward();
        if (randomReward != null && !randomReward.isEmpty()) {
            getServer().dispatchCommand(getServer().getConsoleSender(), randomReward.replace("{player}", player.getName()));
            player.sendMessage("Ödül kazandınız: " + randomReward);
        }
    }

    private void startDailyQuestTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                giveDailyQuests();
            }
        }.runTaskTimer(this, 0, 20 * 60 * 60 * 24); // 24 saatte bir görev ver
    }

    private void giveDailyQuests() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String playerName = player.getName();
            PlayerQuest playerQuest = getPlayerQuest(playerName);
            String newQuest = questService.getRandomQuest();

            playerQuest.setCurrentQuest(newQuest);
            configManager.savePlayerQuest(playerQuest);
        }
    }

    public PlayerQuest getPlayerQuest(String playerName) {
        PlayerQuest playerQuest = playerQuests.stream()
                .filter(pq -> pq.getPlayerName().equals(playerName))
                .findFirst()
                .orElse(new PlayerQuest(playerName, "", 0));

        playerQuests.remove(playerQuest); // Var olanı kaldır
        playerQuests.add(playerQuest); // Güncellenmişi ekle

        return playerQuest;
    }
}