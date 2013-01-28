package com.kreckin.herobrined;

import com.kreckin.herobrined.actions.CreateRingOfFire;
import com.kreckin.herobrined.actions.DestroyTorches;
import com.kreckin.herobrined.actions.PlaceSign;
import com.kreckin.herobrined.actions.PlaceTorch;
import com.kreckin.herobrined.impl.ActionManager;
import com.kreckin.herobrined.listeners.CommandListener;
import com.kreckin.herobrined.listeners.EventListener;
import com.kreckin.herobrined.util.Util;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Herobrined extends JavaPlugin {

    private static ActionManager actionManager;
    private static YamlConfiguration config;

    @Override
    public void onEnable() {
        Herobrined.actionManager = new ActionManager();
        Herobrined.actionManager.registerAction(new PlaceTorch());
        Herobrined.actionManager.registerAction(new PlaceSign());
        Herobrined.actionManager.registerAction(new DestroyTorches());
        Herobrined.actionManager.registerAction(new CreateRingOfFire());
        this.getCommand("hb").setExecutor(new CommandListener());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            if (!new File(this.getDataFolder() + "/config.yml").exists()) {
                this.saveResource("config.yml", false);
            }
            Herobrined.config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder() + "/config.yml"));
        } catch (Exception ex) {
            this.getLogger().severe("Failed to properly config the plugin!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        Herobrined.log("Survival Only: " + Herobrined.config.getBoolean("Herobrined.survivalOnly"), Level.INFO);
        Herobrined.log("Action Chance: " + Herobrined.config.getInt("Herobrined.actionChance"), Level.INFO);
        this.printArray(Herobrined.config.getStringList("Herobrined.signMessages"), "Sign Messages");
        this.printArray(Herobrined.config.getStringList("Herobrined.disallowedWorlds"), "Disallowed Worlds");
        this.printArray(Herobrined.config.getStringList("Herobrined.disallowedActions"), "Disallowed Actions");
    }
    
    @Override
    public void onDisable() {
        Herobrined.actionManager = null;
        Herobrined.config = null;
    }
    
    public static void log(String message, Level level) {
        Bukkit.getLogger().log(level, ChatColor.stripColor(Util.formatString(message)));
    }
    
    private void printArray(List<String> list, String tag) {
        if (list.isEmpty()) {
            Herobrined.log(tag + ": None", Level.INFO);
        } else {
            Herobrined.log(tag + ":", Level.INFO);
            for (int index = 0; index < list.size(); index++) {
                Herobrined.log("\t" + (index + 1) + ". " + list.get(index), Level.INFO);
            }
        }
    }
    
    public static YamlConfiguration getConfigFile() {
        return Herobrined.config;
    }
    
    public static ActionManager getActionManager() {
        return Herobrined.actionManager;
    }
}
