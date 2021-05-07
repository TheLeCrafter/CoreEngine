package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.StatUtils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class HealthStandsHandler implements Listener {

    public static final NamespacedKey TEMPORARY_STAND_KEY = new NamespacedKey(Engine.INSTANCE, "TEMPORARY_HEALTH_STAND");
    public static final Map<Entity, ArmorStand> STAND_MAP = new HashMap<>();

    public static String getDisplayName(LivingEntity entity) {
        double health = entity.getHealth();
        if (entity.getPersistentDataContainer().has(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) health = entity.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE);
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (entity.getPersistentDataContainer().has(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) maxHealth = entity.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE);
        return ChatColor.RED + "" + health + ChatColor.GREEN + "/" + ChatColor.RED + maxHealth + StatUtils.getDisplayName(Stat.HEALTH);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND) && !event.getEntity().getType().equals(EntityType.PLAYER)) {
            if (event.getEntity() instanceof Damageable) {
                if (event.getEntity() instanceof LivingEntity) {
                    double health = ((LivingEntity) event.getEntity()).getHealth();
                    double maxHealth = ((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) {
                        event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, health);
                    }
                    if (!event.getEntity().getPersistentDataContainer().has(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) {
                        event.getEntity().getPersistentDataContainer().set(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE, maxHealth);
                    }
                    ((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
                    ((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    spawnArmorStands((LivingEntity) event.getEntity());
                }
            }
        }
    }

    public void spawnArmorStands(LivingEntity entity) {
        ArmorStand stand = entity.getWorld().spawn(entity.getLocation().clone().add(0, entity.getEyeHeight(), 0), ArmorStand.class);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setInvulnerable(true);
        stand.setInvisible(true);
        stand.setCustomName(getDisplayName(entity));
        stand.setCustomNameVisible(true);
        stand.getPersistentDataContainer().set(TEMPORARY_STAND_KEY, PersistentDataType.STRING, "true");
        STAND_MAP.put(entity, stand);
    }

    @EventHandler
    public void onMove(EntityMoveEvent event) {
        if (STAND_MAP.containsKey(event.getEntity())) {
            STAND_MAP.get(event.getEntity()).teleport(event.getEntity().getLocation().clone().add(0, event.getEntity().getEyeHeight(), 0));
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
