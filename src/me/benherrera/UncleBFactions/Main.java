// Package
package me.benherrera.UncleBFactions;

// Imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

    }

    // Method when plugin is disabled
    @Override
    public void onDisable() {

    }

    // Handles all commands
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Command for /teams
        if (label.equalsIgnoreCase("teams")) {

            // Grabs player object that executed the command
            Player player = (Player) sender;

            // Sends response message
            for (String key : config.getConfigurationSection("factions").getKeys(false)) {

                // Local Variables
                StringBuilder members;
                members = new StringBuilder();
                int counter = 0;

                // Concatenates members from config
                for (String name : config.getStringList("factions." + key + ".members")) {

                    // Checks if it is the last key in the list so that it doesn't place a comma at the end
                    if (counter == (config.getStringList("factions." + key + ".members").size() - 1)) {

                        members.append("§e").append(name);

                    } else {

                        members.append("§e").append(name).append(", ");

                    }

                    // Increments the counter variable
                    counter += 1;

                }

                // Main body to send messages
                // Message Structure:
                /*
                 *
                 *
                 * &6&l<Faction Name>:
                 *   &6&lLeader: &e&l<Leader Name>
                 *   &6&lMembers: &e<Members of Faction>
                 *
                 *
                 */
                player.sendMessage("\n\n");
                player.sendMessage("§6§l" + config.getString("factions." + key + ".name") + ":\n");
                player.sendMessage("§6§l  Leader: §e§l" + config.getString("factions." + key + ".leader") + "\n");
                player.sendMessage("§6§l  Members: §e" + members);
                player.sendMessage("\n\n");

            }

            // Ends handler
            return true;

        }

        // Ends handler
        return false;

    }

}
