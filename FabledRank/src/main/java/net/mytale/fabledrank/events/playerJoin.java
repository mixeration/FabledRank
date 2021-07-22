package net.mytale.fabledrank.events;

import net.mytale.fabledrank.FabledRank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class playerJoin implements Listener {
    private FabledRank getFabledRank;
    public playerJoin(FabledRank getFabledRank) {
        this.getFabledRank = getFabledRank;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) throws IOException {

        Player getPlayer = event.getPlayer();
        if (getFabledRank.getDatabase().getString(getPlayer.getUniqueId().toString() + ".user") == null) {
            getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".user", getPlayer.getName());
            getFabledRank.getDatabase().set(getPlayer.getUniqueId().toString() + ".player-rank", getFabledRank.getConfig().getString("default-rank"));
            getFabledRank.getDatabase().save(getFabledRank.TAGFMm);
        }
    }
}
