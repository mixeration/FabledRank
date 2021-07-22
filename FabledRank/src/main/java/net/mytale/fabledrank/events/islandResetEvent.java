package net.mytale.fabledrank.events;

import net.mytale.fabledrank.FabledRank;
import org.bukkit.Bukkit;
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
    public void islandEvent(com.songoda.skyblock.api.event.island.IslandCreateEvent event) throws IOException {
        Player getPlayer = event.getPlayer();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getFabledRank.getConfig().getString("group-reset-event").replace("%fabledrank_player%", getPlayer.getName()));
    }

    @EventHandler
    public void islandEvent$2(com.songoda.skyblock.api.event.island.IslandKickEvent event) throws IOException {
        Player getPlayer = (Player) event.getKicked();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user-last-kicked-from", event.getKicker().getName());
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getFabledRank.getConfig().getString("group-reset-event").replace("%fabledrank_player%", getPlayer.getName()));
        getFabledRank.getPermissions().playerAddGroup(getPlayer, getFabledRank.getConfig().getString("default-rank"));
    }

    @EventHandler
    public void islandEvent$3(com.songoda.skyblock.api.event.island.IslandBanEvent event) throws IOException {
        Player getPlayer = (Player) event.getBanned();
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user-last-banned-from", event.getIssuer().getName());
        getFabledRank.getPermissions().playerAddGroup(getPlayer, getFabledRank.getConfig().getString("default-rank"));
        getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getFabledRank.getConfig().getString("group-reset-event").replace("%fabledrank_player%", getPlayer.getName()));
    }
}
