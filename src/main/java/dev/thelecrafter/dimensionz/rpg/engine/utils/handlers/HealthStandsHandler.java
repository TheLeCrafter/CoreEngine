package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class HealthStandsHandler implements Listener {

    public static final NamespacedKey TEMPORARY_STAND_KEY = new NamespacedKey(Engine.INSTANCE, "TEMPORARY_HEALTH_STAND");
    public static final Map<Entity, ArmorStand> STAND_MAP = new HashMap<>();

    public String getDisplayName(LivingEntity entity) {
        double health = entity.getHealth();
        if (entity.getPersistentDataContainer().has(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) health = entity.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE);
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (entity.getPersistentDataContainer().has(StatUtils.MAX_HEALTH_KEY, PersistentDataType.INTEGER)) maxHealth = entity.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.INTEGER);
        return ChatColor.RED + "" + health + ChatColor.GREEN + "/" + ChatColor.RED + maxHealth + StatUtils.getDisplayName(Stat.HEALTH);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND) && !event.getEntity().getType().equals(EntityType.PLAYER)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                ArmorStand stand = entity.getWorld().spawn(event.getLocation().clone().add(0, ((LivingEntity) event.getEntity()).getEyeHeight(), 0), ArmorStand.class);
                stand.setMarker(true);
                stand.setGravity(false);
                stand.setInvulnerable(true);
                stand.setInvisible(true);
                stand.setCustomName(getDisplayName(entity));
                stand.setCustomNameVisible(true);
                stand.getPersistentDataContainer().set(TEMPORARY_STAND_KEY, PersistentDataType.STRING, "true");
                STAND_MAP.put(entity, stand);
            }
        }
    }

    @EventHandler
    public void onMove(EntityMoveEvent event) {
        if (STAND_MAP.containsKey(event.getEntity())) {
            STAND_MAP.get(event.getEntity()).teleport(event.getEntity().getLocation().clone().add(0, event.getEntity().getEyeHeight(), 0));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (STAND_MAP.containsKey(event.getEntity())) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            STAND_MAP.get(entity).setCustomName(getDisplayName(entity));
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (STAND_MAP.containsKey(event.getEntity())) {
            LivingEntity entity = event.getEntity();
            STAND_MAP.get(entity).remove();
            STAND_MAP.remove(entity);
        }
    }

}
