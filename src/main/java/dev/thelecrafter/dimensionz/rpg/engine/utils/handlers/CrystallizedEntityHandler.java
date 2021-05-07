package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.StatUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class CrystallizedEntityHandler implements Listener {

    public static final NamespacedKey CRYSTALLIZED_ENTITY_KEY = new NamespacedKey(Engine.INSTANCE, "crystallized_entity");

    @EventHandler(priority = EventPriority.LOW)
    public void onSpawn(EntitySpawnEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ARMOR_STAND) && !event.getEntity().getType().equals(EntityType.PLAYER)) {
            Random random = new Random();
            int chance = 5;
            if (random.nextInt(100) + 1 <= chance) {
                if (event.getEntity() instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) event.getEntity();
                    event.getEntity().getPersistentDataContainer().set(CRYSTALLIZED_ENTITY_KEY, PersistentDataType.STRING, "true");
                    if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) {
                        event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, livingEntity.getHealth());
                    }
                    event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) * 1.5);
                    if (!event.getEntity().getPersistentDataContainer().has(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) {
                        event.getEntity().getPersistentDataContainer().set(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE, livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    }
                    event.getEntity().getPersistentDataContainer().set(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE));
                    if (!event.getEntity().getPersistentDataContainer().has(HealthStandsHandler.ENTITY_DAMAGE_KEY, PersistentDataType.DOUBLE)) {
                        event.getEntity().getPersistentDataContainer().set(HealthStandsHandler.ENTITY_DAMAGE_KEY, PersistentDataType.DOUBLE, ((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
                    }
                    event.getEntity().getPersistentDataContainer().set(HealthStandsHandler.ENTITY_DAMAGE_KEY, PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(HealthStandsHandler.ENTITY_DAMAGE_KEY, PersistentDataType.DOUBLE));
                }
            }
        }
    }

}
