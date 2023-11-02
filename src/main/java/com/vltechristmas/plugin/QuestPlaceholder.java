package com.vltechristmas.plugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class QuestPlaceholder extends PlaceholderExpansion {
    private final Main plugin;

    public QuestPlaceholder(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "vltChristmas";
    }

    @Override
    public String getAuthor() {
        return "VOLT";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        PlayerQuest playerQuest = plugin.getPlayerQuest(player.getName());

        if (identifier.equals("current_quest")) {
            String currentQuest = playerQuest != null ? playerQuest.getCurrentQuest() : null;
            return currentQuest != null ? currentQuest : "Şu anda görev yok";
        } else if (identifier.equals("quest_progress") && playerQuest != null) {
            return String.valueOf(playerQuest.getQuestProgress());
        }

        return null;
    }
}