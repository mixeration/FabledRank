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
            if (getFabledRank.getConfig().getString("Groups." + groups + ".type").equalsIgnoreCase("level")) {
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
                            String iLev = String.format(String.valueOf(islandLevel));
                            int calcRank = (int) (islandLevel - neededLevel);
                            String calcRr = String.format(String.valueOf(calcRank));
                            if (neededLevel > islandLevel) {
                                getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.need-level").replace("%fabledrank_calculatedlevel%", calcRr).replace("%fabledrank_level%", iLev).replace("%fabledrank_rank%", customRankName).replace("%fabledrank_neededlevel%", neededLevelEQ)));
                            }
                            if (islandLevel >= neededLevel) {
                                getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", customRankName);
                                try {
                                    getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                getFabledRank.getPermissions().playerAddGroup(getPlayer, customGroupName);
                                for(String commands : getFabledRank.getConfig().getStringList("Groups." + groups + ".rank-up-command")) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
                                }
                                getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.rank-up").replace("%fabledrank_rank%", customRankName)));
                            }
                        }
                    } else {
                        getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.already-have-that-rank").replace("%fabledrank_rank%", customRankName)));
                    }
                    event.setCancelled(true);
                }
            } else if (getFabledRank.getConfig().getString("Groups." + groups + ".type").equalsIgnoreCase("point")) {
                int neededLevel = getFabledRank.getConfig().getInt("Groups." + groups + ".point");
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
                            islandLevel = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(getPlayer).getLevel().getPoints();
                            String iLev = String.format(String.valueOf(islandLevel));
                            int calcRank = (int) (islandLevel - neededLevel);
                            String calcRr = String.format(String.valueOf(calcRank));
                            if (neededLevel > islandLevel) {
                                getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.need-point").replace("%fabledrank_calculatedpoint%", calcRr).replace("%fabledrank_point%", iLev).replace("%fabledrank_rank%", customRankName).replace("%fabledrank_neededpoint%", neededLevelEQ)));
                            }
                            if (islandLevel >= neededLevel) {
                                getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", customRankName);
                                try {
                                    getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                getFabledRank.getPermissions().playerAddGroup(getPlayer, customGroupName);
                                for(String commands : getFabledRank.getConfig().getStringList("Groups." + groups + ".rank-up-command")) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
                                }
                                getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.rank-up").replace("%fabledrank_rank%", customRankName)));
                            }
                        }
                    } else {
                        getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.already-have-that-rank").replace("%fabledrank_rank%", customRankName)));
                    }
                    event.setCancelled(true);
                }
            } else if (getFabledRank.getConfig().getString("Groups." + groups + ".type").equalsIgnoreCase("both")) {
                int neededLevel = getFabledRank.getConfig().getInt("Groups." + groups + ".point");
                int neededLevel2 = getFabledRank.getConfig().getInt("Groups." + groups + ".level");
                String customGroupName = getFabledRank.getConfig().getString("Groups." + groups + ".group-name");
                String customRankName = getFabledRank.getConfig().getString("Groups." + groups + ".rank-name");
                String neededLevelEQ = String.format(String.valueOf(neededLevel));
                com.songoda.skyblock.api.SkyBlockAPI api = null;
                com.songoda.skyblock.api.island.IslandManager im = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager();
                String priority = getFabledRank.getConfig().getString("Groups." + groups + ".run-command");
                if (message.equalsIgnoreCase(priority)) {
                    if (!getFabledRank.getDatabase().getString(getPlayer.getUniqueId().toString() + ".player-rank").equalsIgnoreCase("Groups." + groups + ".rank-name")) {
                        double islandLevel = 0;
                        double islandPoint = 0;
                        if (!im.hasIsland(getPlayer)) {
                            islandLevel = 0;
                            islandPoint = 0;
                            getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.you-have-no-island")));
                        } else {
                            islandLevel = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(getPlayer).getLevel().getPoints();
                            islandPoint = com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(getPlayer).getLevel().getLevel();
                            String iLev = String.format(String.valueOf(islandLevel));
                            String iLev2 = String.format(String.valueOf(islandPoint));
                            int calcRank2 = (int) (islandLevel - neededLevel);
                            String calcRr2 = String.format(String.valueOf(calcRank2));
                            int calcRank = (int) (islandPoint - neededLevel2);
                            String calcRr = String.format(String.valueOf(calcRank));
                            if (islandLevel >= neededLevel) {
                                if (islandPoint >= neededLevel2) {
                                    getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", customRankName);
                                    try {
                                        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    getFabledRank.getPermissions().playerAddGroup(getPlayer, customGroupName);
                                    for(String commands : getFabledRank.getConfig().getStringList("Groups." + groups + ".rank-up-command")) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
                                    }
                                    getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.rank-up").replace("%fabledrank_rank%", customRankName)));
                                } else {
                                    getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.need-level").replace("%fabledrank_calculatedpoint%", calcRr2).replace("%fabledrank_calculatedlevel%", calcRr).replace("%fabledrank_rank%", customRankName).replace("%fabledrank_level%", iLev2).replace("%fabledrank_neededpoint%", neededLevelEQ)));
                                }
                            } else {
                                getPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.need-point").replace("%fabledrank_calculatedpoint%", calcRr2).replace("%fabledrank_calculatedlevel%", calcRr).replace("%fabledrank_point%", iLev).replace("%fabledrank_rank%", customRankName).replace("%fabledrank_neededlevel%", neededLevelEQ)));
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
}
