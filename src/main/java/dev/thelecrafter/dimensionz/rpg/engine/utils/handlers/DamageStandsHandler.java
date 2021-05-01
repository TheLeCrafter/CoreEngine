package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class DamageStandsHandler implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            Random random = new Random();
            int x = random.nextInt(8) + 4;
            int z = random.nextInt(8) + 4;
            ArmorStand stand = event.getEntity().getWorld().spawn(new Location(event.getEntity().getWorld(), 0, 0, 0), ArmorStand.class);
            stand.setMarker(true);
            stand.setInvisible(true);
            stand.setInvulnerable(true);
            stand.setCustomName(ChatColor.RED + "" + event.getDamage());
            stand.setCustomNameVisible(true);
            stand.teleport(event.getEntity().getLocation().clone().add(x, event.getEntity().getHeight(), z));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Engine.INSTANCE, () -> {
                if (!stand.isDead()) {
                    stand.remove();
                }
            }, 3 * 20);
        }
    }

}
