// Package
package me.benherrera.UncleBFactions.Commands;

// Imports
import me.benherrera.UncleBFactions.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

// Class to handle /teams command
public class TeamsCommand implements CommandExecutor{

    //  Class variables
    Main plugin;

    // Constructor
    public TeamsCommand(Main instance) {

        // Passes plugin object to plugin class variable
        plugin = instance;

    }

    // Command handler
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Method variables
        FileConfiguration config = plugin.getConfig();

        // Command for /teams
        if (label.equalsIgnoreCase("teams")) {

            // Grabs player object that executed the command
            Player player = (Player) sender;

            // Sends response message
            for (String key : config.getConfigurationSection("factions").getKeys(false)) {

                // Local variables
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
