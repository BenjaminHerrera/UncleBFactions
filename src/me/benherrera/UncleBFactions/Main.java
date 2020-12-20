// Directory of class
package me.benherrera.UncleBFactions;

// Imports
import me.benherrera.UncleBFactions.plugincommands.TeamsCommand;
import me.benherrera.UncleBFactions.rewards.EnderDragonRewards;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

// Main Class
public class Main extends JavaPlugin {

    // Method when plugin is enable
    @Override
    public void onEnable() {

        // Method variables
        PluginManager pluginManager = getServer().getPluginManager();

        // Saves config
        saveDefaultConfig();

        // Registers /teams
        // noinspection ConstantConditions
        getCommand("teams").setExecutor(new TeamsCommand(this));

        // Registers Ender Dragon death event
        pluginManager.registerEvents(new EnderDragonRewards(this), this);

    }

    // Method when plugin is disabled
    @Override
    public void onDisable() {

    }

}
