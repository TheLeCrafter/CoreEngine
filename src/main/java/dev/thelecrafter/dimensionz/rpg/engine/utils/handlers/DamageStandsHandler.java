package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class DamageStandsHandler implements Listener {

    public static final NamespacedKey TEMPORARY_STAND_KEY = new NamespacedKey(Engine.INSTANCE, "TEMPORARY_STAND");

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            Random random = new Random();
            double x = random.nextInt(3) + 1.5;
            double z = random.nextInt(3) + 1.5;
            ArmorStand stand = event.getEntity().getWorld().spawn(new Location(event.getEntity().getWorld(), 0, 0, 0), ArmorStand.class);
            stand.setInvisible(true);
            stand.setMarker(true);
            stand.setInvulnerable(true);
            stand.setCustomName(ChatColor.RED + "ᐊ " + event.getDamage() + " ᐅ");
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.teleport(event.getEntity().getLocation().clone().add(x, event.getEntity().getHeight(), z));
            stand.getPersistentDataContainer().set(TEMPORARY_STAND_KEY, PersistentDataType.STRING, "true");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Engine.INSTANCE, () -> {
                if (!stand.isDead()) {
                    stand.remove();
                }
            }, 3 * 20);
        }
    }

}
