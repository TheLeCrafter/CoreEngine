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
            int x = random.nextInt(6) + 3;
            int z = random.nextInt(6) + 3;
            System.out.println(1);
            ArmorStand stand = event.getEntity().getWorld().spawn(new Location(event.getEntity().getWorld(), 0, 0, 0), ArmorStand.class);
            System.out.println(2);
            stand.setMarker(true);
            stand.setInvisible(true);
            stand.setInvulnerable(true);
            System.out.println(3);
            stand.setCustomName(ChatColor.RED + "" + event.getDamage());
            stand.setCustomNameVisible(true);
            System.out.println(4);
            stand.teleport(event.getEntity().getLocation().clone().add(x, event.getEntity().getHeight(), z));
            System.out.println(5);
            stand.getPersistentDataContainer().set(TEMPORARY_STAND_KEY, PersistentDataType.STRING, "true");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Engine.INSTANCE, () -> {
                System.out.println(6);
                if (!stand.isDead()) {
                    System.out.println(7);
                    stand.remove();
                }
            }, 3 * 20);
        }
    }

}
