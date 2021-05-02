package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.DamageStandsHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageListeners implements Listener {

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
            if (player.getCooledAttackStrength(0) < 1) damage = baseDamage / 4;
            if (event.getEntity() instanceof LivingEntity) {
                if (event.getEntity() instanceof Damageable) {
                    ((LivingEntity) event.getEntity()).damage((((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * damage) / 100, event.getDamager());
                    event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, event.getEntity().getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE) - damage);
                    DamageStandsHandler.spawnArmorStand(event.getEntity(), damage);
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageSetDefense(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            Engine.refreshPlayerStats((Player) event.getEntity());
            event.setDamage(DamageCalculations.calculateWithDefensiveStats(event.getDamage(), event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER)));
            ((Player) event.getEntity()).setHealth(Math.round((event.getEntity().getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) * event.getEntity().getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) / 100));
        }
    }

}
