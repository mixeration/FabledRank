package net.mytale.fabledrank;

import net.milkbowl.vault.permission.Permission;
import net.mytale.fabledrank.commands.command$1;
import net.mytale.fabledrank.events.customCommandListener;
import net.mytale.fabledrank.events.islandResetEvent;
import net.mytale.fabledrank.events.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class FabledRank extends JavaPlugin {


    private static Permission perms = null;

    public File TAGFMm;
    public File TAGFM;
    public FileConfiguration TAGCM;
    public FileConfiguration TAGCMm;

    public File file;
    public FileConfiguration config;
    public PluginManager pm = Bukkit.getServer().getPluginManager();

    public long getPlayerIslandLevel(Player getPlayer) {
        return com.songoda.skyblock.api.SkyBlockAPI.getIslandManager().getIsland(getPlayer).getLevel().getLevel();
    }

    public void loadConfig() {
        this.config = this.getConfig();
        this.file = new File(this.getDataFolder(), "config.yml");
        this.saveDefaultConfig();
    }

    private void createDatabase() {
        this.TAGFMm = new File(this.getDataFolder(), "database.yml");
        if (!this.TAGFMm.exists()) {
            this.TAGFMm.getParentFile().mkdirs();
            this.saveResource("database.yml", false);
        }

        this.TAGCMm = new YamlConfiguration();

        try {
            this.TAGCMm.load(this.TAGFMm);
        } catch (InvalidConfigurationException | IOException var2) {
            var2.printStackTrace();
        }

    }

    public FileConfiguration getDatabase() {
        return this.TAGCMm;
    }

    private void createMessages() {
        this.TAGFM = new File(this.getDataFolder(), "messages.yml");
        if (!this.TAGFM.exists()) {
            this.TAGFM.getParentFile().mkdirs();
            this.saveResource("messages.yml", false);
        }

        this.TAGCM = new YamlConfiguration();

        try {
            this.TAGCM.load(this.TAGFM);
        } catch (InvalidConfigurationException | IOException var2) {
            var2.printStackTrace();
        }

    }

    public FileConfiguration getMessages() {
        return this.TAGCM;
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    public static Permission getPermissions() {
        return perms;
    }

    public void setupVaultAndFabledSkyBlock() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            if(getConfig().getString("locale").equals("EN")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Vault can not found, plugin closed."));
                Bukkit.getPluginManager().disablePlugin(this);
            } else if(getConfig().getString("locale").equals("TR")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Vault bulunamadi, eklenti kapatiliyor."));
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            if(getConfig().getString("locale").equals("EN")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Vault found."));
            } else if(getConfig().getString("locale").equals("TR")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Vault bulundu."));
            }
        }
        if (Bukkit.getPluginManager().getPlugin("FabledSkyBlock") == null) {
            if(getConfig().getString("locale").equals("EN")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Fabled skyblock can not found, plugin closed."));
                Bukkit.getPluginManager().disablePlugin(this);
            } else if(getConfig().getString("locale").equals("TR")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Fabled skyblock bulunamadi, eklenti kapatiliyor."));
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            if (getConfig().getString("locale").equals("EN")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Fabled skyblock found."));
            } else if (getConfig().getString("locale").equals("TR")) {
                Bukkit.getConsoleSender().sendMessage(String.format("Fabled skyblock bulundu."));
            }
        }
        setupPermissions();
        loadConfig();
        createMessages();
        createDatabase();
        this.getCommand("FabledRankAdmin").setExecutor(new command$1(this));
        pm.registerEvents(new islandResetEvent(this), this);
        pm.registerEvents(new customCommandListener(this), this);
        pm.registerEvents(new playerJoin(this), this);
    }

    @Override
    public void onEnable() {
        setupVaultAndFabledSkyBlock();
        reloadConfig();
        saveConfig();
        Bukkit.getLogger().info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }
}
