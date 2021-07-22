package net.mytale.fabledrank.events;

import net.mytale.fabledrank.FabledRank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;

public class customCommandListener implements Listener {
    private FabledRank getFabledRank;

    public customCommandListener(FabledRank getFabledRank) {
        this.getFabledRank = getFabledRank;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(AsyncPlayerChatEvent event) {
        Player getPlayer = event.getPlayer();
        String message = event.getMessage();
        for (String groups : getFabledRank.getConfig().getConfigurationSection("Groups").getKeys(false)) {
            int neededLevel = getFabledRank.getConfig().getInt("Groups." + groups + ".level");
            String customGroupName = getFabledRank.getConfig().getString("Groups." + groups + ".group-name");
            String customRankName = getFabledRank.getConfig().getString("Groups." + groups + ".rank-name");
            String neededLevelEQ = String.format(String.valueOf(neededLevel));
            com.songoda.skyblock.api.SkyBlockAPI api = null;
            com.songoda.skyblock.api.island.IslandManager im = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager();
            String priority = getFabledRank.getConfig().getString("Groups." + groups + ".run-command");
            if (message.equalsIgnoreCase(priority)) {
                if (!getFabledRank.getDatabase().getString(getPlayer.getUniqueId().toString() + ".player-rank").equalsIgnoreCase("Groups." + groups + ".rank-name")) {
                    double islandLevel = 0;
                    if (!im.hasIsland(getPlayer)) {
                        islandLevel = 0;
                        getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.you-have-no-island")));
                    } else {
                        islandLevel = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(getPlayer).getLevel().getLevel();
                        if (neededLevel > islandLevel) {
                            getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.need-level").replace("%fabledrank_rank%", customRankName).replace("%fabledrank_neededlevel%", neededLevelEQ)));
                        }
                        if (islandLevel >= neededLevel) {
                            getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", customRankName);
                            try {
                                getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getFabledRank.getPermissions().playerAddGroup(getPlayer, customGroupName);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getFabledRank.getConfig().getString("Groups." + groups + ".rank-up-command").replace("%fabledrank_player%", getPlayer.getName()));
                            getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.rank-up").replace("%fabledrank_rank%", customRankName)));
                        }
                    }
                } else {
                    getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.already-have-that-rank").replace("%fabledrank_rank%", customRankName)));
                }
                event.setCancelled(true);
            }
        }
    }
}
