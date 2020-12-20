// Package
package me.benherrera.UncleBFactions;

// Imports
import me.benherrera.UncleBFactions.Commands.TeamsCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// Main Class
public class Main extends JavaPlugin {

    // Class Variables
    public FileConfiguration config = getConfig();

    // Method when plugin is enable
    @Override
    public void onEnable() {

        // Saves Config
        saveDefaultConfig();

        // Registers /teams
        getCommand("teams").setExecutor(new TeamsCommand(this));

    }

    // Method when plugin is disabled
    @Override
    public void onDisable() {

    }

}
