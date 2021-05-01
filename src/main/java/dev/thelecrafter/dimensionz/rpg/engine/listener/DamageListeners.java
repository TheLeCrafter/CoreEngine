package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageListeners implements Listener {

    @EventHandler
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
            System.out.println("damage = " + damage);
            event.setDamage(damage);
        }
    }

    @EventHandler
    public void onDamageSetDefense(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            Engine.refreshPlayerStats((Player) event.getEntity());
            Player player = (Player) event.getEntity();
            System.out.println("Original damage: " + event.getDamage());
            event.setDamage(DamageCalculations.calculateWithDefensiveStats(event.getDamage(), player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.INTEGER), player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER)));
            System.out.println("New damage: " + event.getDamage());
        }
    }

}
