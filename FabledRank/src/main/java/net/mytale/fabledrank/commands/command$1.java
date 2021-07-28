package net.mytale.fabledrank.commands;

import net.mytale.fabledrank.FabledRank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class command$1 implements CommandExecutor {
    private FabledRank getFabledRank;

    public command$1(FabledRank getFabledRank) {
        this.getFabledRank = getFabledRank;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("FabledRankAdmin")) {
                if (sender.hasPermission("FabledRank.Admin")) {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3FabledRank &8| &bMytale Studio"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &8&l▸ &f/FabledRankAdmin Reload"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "    &8&l▸ &f/FabledRankAdmin reset <Player>"));
                        return true;
                    }
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("reset")) {
                            Player receiver = Bukkit.getPlayerExact(args[1]);
                            if (receiver != null) {
                                if (!getFabledRank.getDatabase().getString(receiver.getUniqueId().toString() + ".user", receiver.getName()).isEmpty()) {
                                    getFabledRank.getDatabase().set(receiver.getUniqueId().toString() + ".user", receiver.getName());
                                    getFabledRank.getDatabase().set(receiver.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
                                    getFabledRank.getDatabase().set(receiver.getUniqueId().toString() + ".user-last-kicked-from", null);
                                    getFabledRank.getDatabase().set(receiver.getUniqueId().toString() + ".user-last-banned-from", null);
                                    try {
                                        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    for (String commands : getFabledRank.getConfig().getStringList("group-reset-event")) {
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", receiver.getName()));
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.player-cant-found").replace("%player%", receiver.getName())));
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.player-not-online").replace("%player%", receiver.getName())));
                            }
                        }
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("Reload")) {
                                getFabledRank.reloadConfig();
                                getFabledRank.saveConfig();
                                try {
                                    getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
                                    getFabledRank.getMessages().save(getFabledRank.TAGFM);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (getFabledRank.getConfig().getString("locale").equals("EN")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3FabledRank &8&l▸ &7Plugin reloaded."));
                                } else if (getFabledRank.getConfig().getString("locale").equals("TR")) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3FabledRank &8&l▸ &7Eklenti yenilendi."));
                                }
                            }
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getFabledRank.getMessages().getString("messages.no-permission")));
                }
            }
        } else {
            if (getFabledRank.getConfig().getString("locale").equals("EN")) {
                Bukkit.getConsoleSender().sendMessage("FabledRank : Only in game");
            } else if (getFabledRank.getConfig().getString("locale").equals("TR")) {
                Bukkit.getConsoleSender().sendMessage("FabledRank : Yanlizca oyun icinden kullanilabilir.");
            }
        }
        return true;
    }
}
