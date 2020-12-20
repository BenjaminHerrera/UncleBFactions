// Directory of class
package me.benherrera.UncleBFactions.rewards;

// Imports
import me.benherrera.UncleBFactions.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.DragonBattle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

// Class to handle Ender Dragon kill reward
public class EnderDragonRewards implements Listener {

    // Class variable
    Location locationBlock;
    ArmorStand hologram;
    int particleTaskID;
    Main plugin;

    // Constructor
    public EnderDragonRewards(Main instance) {

        // Passes parameters to class variables
        plugin = instance;

    }

    // Listens if the Ender Dragon has been killed
    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onEnderDragonDeath(EntityDeathEvent entity) {

        // Method variables
        FileConfiguration config = plugin.getConfig();

        // Checks if the entity killed is an Ender Dragon
        if (entity.getEntity() instanceof EnderDragon) {

            // Obtains the details of the battle for the dragon
            DragonBattle battle = ((EnderDragon) entity.getEntity()).getDragonBattle();

            // Broadcasts that a dragon has been killed
            Bukkit.getServer().broadcastMessage(config.getString("enderDragonReward.killMessage"));

            // Generates locations where the reward, particles, and hologram will be
            locationBlock = new Location(battle.getEndPortalLocation().getWorld(), battle.getEndPortalLocation().getX(), battle.getEndPortalLocation().getY() + 6, battle.getEndPortalLocation().getZ());
            Location locationHologram = new Location(battle.getEndPortalLocation().getWorld(), battle.getEndPortalLocation().getX() + 0.5, battle.getEndPortalLocation().getY() + 5.5, battle.getEndPortalLocation().getZ() + 0.5);
            Location locationParticle = new Location(battle.getEndPortalLocation().getWorld(), battle.getEndPortalLocation().getX() + 0.5, battle.getEndPortalLocation().getY() + 6.5, battle.getEndPortalLocation().getZ() + 0.5);

            // Places the reward block above the end portal
            locationBlock.getBlock().setType(Material.getMaterial(config.getString("enderDragonReward.block")));

            // Creates an armorstand object
            hologram = (ArmorStand) locationHologram.getWorld().spawnEntity(locationHologram, EntityType.ARMOR_STAND);

            // Applies property changes to the armorstand object
            hologram.setVisible(false);
            hologram.setCustomNameVisible(true);
            hologram.setCustomName(config.getString("enderDragonReward.hologramText"));
            hologram.setGravity(false);

            // Spawns particles persistently
            particleTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    locationParticle.getWorld().spawnParticle(Particle.valueOf(config.getString("enderDragonReward.particle")), locationParticle, 45);
                }
            }, 20, 20);

            // Spawns a particle effect on the location of the reward block
            locationBlock.getWorld().spawnParticle(Particle.valueOf(config.getString("enderDragonReward.particle")), locationBlock, 50);

        }

    }

    // Handles the interact event of the reward block
    @EventHandler
    @SuppressWarnings("ConstantConditions")
    public void onInteractBlock(PlayerInteractEvent event) {

        // Method variables
        Block block = event.getClickedBlock();
        FileConfiguration config = plugin.getConfig();

        // Prevents NullPointerError Spams in the console
        if (block == null || locationBlock == null) {

            return;

        }

        // Checks if the clicked block is the reward block
        if (block.getLocation().getX() == locationBlock.getX() && block.getLocation().getY() == locationBlock.getY() && block.getLocation().getZ() == locationBlock.getZ()) {

            // Cancels the particle task, removes the block, and removes the armor stand
            hologram.remove();
            Bukkit.getScheduler().cancelTask(particleTaskID);
            block.getLocation().getBlock().setType(Material.AIR);

            // Spawns a firework on the player
            Firework firework = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
            FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
            data.addEffects(FireworkEffect.builder().withColor(Color.WHITE).withColor(Color.ORANGE).with(FireworkEffect.Type.BALL_LARGE).withFlicker().build());
            data.setPower(1);
            firework.setFireworkMeta(data);

            // Broadcasts the reward claimed message
            Bukkit.getServer().broadcastMessage(config.getString("enderDragonReward.rewardMessage") + "§f§l " + event.getPlayer().getName());

            // Give reward to player
            ItemStack itemStack = new ItemStack(Material.matchMaterial(config.getString("enderDragonReward.reward")), 1);
            event.getPlayer().getInventory().addItem(itemStack);

            // Send player a message
            event.getPlayer().sendMessage(config.getString("enderDragonReward.rewardMessageToPlayer01") + "§f§l " + config.getString("enderDragonReward.reward") + " " + config.getString("enderDragonReward.rewardMessageToPlayer02"));

        }

    }

}
