package net.mytale.fabledrank.events;

import net.mytale.fabledrank.FabledRank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.UUID;

public class islandResetEvent implements Listener {
    private FabledRank getFabledRank;
    public islandResetEvent(FabledRank getFabledRank) {
        this.getFabledRank = getFabledRank;
    }


    @EventHandler
    public boolean islandEvent(com.songoda.skyblock.api.event.island.IslandCreateEvent event) throws IOException {
        Player getPlayer = event.getPlayer();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        for(String commands : getFabledRank.getConfig().getStringList("group-reset-event")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
        }
        return true;
    }

    @EventHandler
    public boolean islandEvent$2(com.songoda.skyblock.api.event.island.IslandKickEvent event) throws IOException {
        Player getPlayer = (Player) event.getKicked();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user-last-kicked-from", event.getKicker().getName());
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        for(String commands : getFabledRank.getConfig().getStringList("group-reset-event")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
        }
        getFabledRank.getPermissions().playerAddGroup(getPlayer, getFabledRank.getConfig().getString("default-rank"));
        return true;
    }

    @EventHandler
    public boolean islandEvent$3(com.songoda.skyblock.api.event.island.IslandBanEvent event) throws IOException {
        Player getPlayer = (Player) event.getBanned();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user-last-banned-from", event.getIssuer().getName());
        getFabledRank.getPermissions().playerAddGroup(getPlayer, getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        for(String commands : getFabledRank.getConfig().getStringList("group-reset-event")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%fabledrank_player%", getPlayer.getName()));
        }
        return true;
    }
}
