package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.DamageStandsHandler;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.HealthStandsHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class DamageListeners implements Listener {

    public static final NamespacedKey REGENERATION_STAT_KEY = new NamespacedKey(Engine.INSTANCE, "REGENERATION_PERCENTAGE");

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageSetAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.PLAYER)) {
            Engine.refreshPlayerStats((Player) event.getDamager());
            Player player = (Player) event.getDamager();
            int baseDamage = 1;
            if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()) != null) {
                if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()).hasItemMeta()) {
                    if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()).getItemMeta().getPersistentDataContainer().has(DamageCalculations.BASE_DAMAGE_KEY, PersistentDataType.INTEGER)) {
                        baseDamage = player.getInventory().getItem(player.getInventory().getHeldItemSlot()).getItemMeta().getPersistentDataContainer().get(DamageCalculations.BASE_DAMAGE_KEY, PersistentDataType.INTEGER);
                    }
                }
            }
            int strength = 0;
            if (player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.STRENGTH.toString()), PersistentDataType.INTEGER) > 0) {
                strength = player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.STRENGTH.toString()), PersistentDataType.INTEGER);
            }
            double damage = DamageCalculations.calculateWithDamageStats(baseDamage, strength);
            if (damage < 0) damage = 0;
            if (damage > 0) damage = DamageCalculations.calculateDoubleDamage(damage, player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DOUBLE_DAMAGE.toString()), PersistentDataType.INTEGER));
            if (player.getCooledAttackStrength(0) < 1) damage = baseDamage / 4;
            if (event.getEntity() instanceof LivingEntity) {
                if (event.getEntity() instanceof Damageable) {
                    if (event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) - damage <= 0) {
                        event.setDamage(((LivingEntity) event.getEntity()).getHealth());
                    } else {
                        event.setDamage((damage * event.getEntity().getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) / 100);
                    }
                    event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) - damage);
                    if (event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) < 0) {
                        event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, 0.0);
                    }
                    if (HealthStandsHandler.STAND_MAP.containsKey(event.getEntity())) {
                        HealthStandsHandler.STAND_MAP.get(event.getEntity()).setCustomName(HealthStandsHandler.getDisplayName((LivingEntity) event.getEntity()));
                    }
                    DamageStandsHandler.spawnArmorStand(event.getEntity(), damage);
                }
            } else event.setDamage(0);
        }
    }

    @EventHandler
    public void onDamageSetDefense(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            Engine.refreshPlayerStats((Player) event.getEntity());
            event.setDamage(DamageCalculations.calculateWithDefensiveStats(event.getDamage(), event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER)));
            event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) - event.getDamage());
            setPlayerHealth(((Player) event.getEntity()).getPlayer());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, event.getPlayer().getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE));
        Engine.refreshPlayerStats(event.getPlayer());
    }

    // DISABLE PLAYER HUNGER
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    // DISABLE PLAYER REGENERATION
    @EventHandler
    public void onRegeneration(EntityRegainHealthEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            if (!event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.CUSTOM)) event.setCancelled(true);
        }
    }

    // CUSTOM PLAYER REGENERATION
    public static void initCustomPlayerRegeneration(@Nonnegative @Nonnull int secondsPerRegeneration, @Nonnegative @Nonnull int baseRegenPercentage, @Nonnegative @Nonnull int maxRegenPercentage) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Engine.INSTANCE, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getPersistentDataContainer().has(REGENERATION_STAT_KEY, PersistentDataType.INTEGER)) {
                    player.getPersistentDataContainer().set(REGENERATION_STAT_KEY, PersistentDataType.INTEGER, baseRegenPercentage);
                }
                if (player.getPersistentDataContainer().get(REGENERATION_STAT_KEY, PersistentDataType.INTEGER) < baseRegenPercentage) {
                    player.getPersistentDataContainer().set(REGENERATION_STAT_KEY, PersistentDataType.INTEGER, baseRegenPercentage);
                }
                if (player.getPersistentDataContainer().get(REGENERATION_STAT_KEY, PersistentDataType.INTEGER) > maxRegenPercentage) {
                    player.getPersistentDataContainer().set(REGENERATION_STAT_KEY, PersistentDataType.INTEGER, maxRegenPercentage);
                }
                // Do the regeneration
                int regenPercentage = player.getPersistentDataContainer().get(REGENERATION_STAT_KEY, PersistentDataType.INTEGER);
                double healthToHeal = (regenPercentage * player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) / 100;
                Engine.healPlayer(player, healthToHeal, true);
                setPlayerHealth(player);
            }
        }, 0, 20 * secondsPerRegeneration);
    }

    public static void setPlayerHealth(Player player) {
        double health = player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE);
        double maxHealth = player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE);
        double percentage = health / maxHealth;
        double playerHealthFromPercentage = percentage * player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (playerHealthFromPercentage < 1) playerHealthFromPercentage = 1;
        player.setHealth(Math.round(playerHealthFromPercentage));
    }

}
