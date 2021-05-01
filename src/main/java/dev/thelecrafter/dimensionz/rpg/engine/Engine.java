package dev.thelecrafter.dimensionz.rpg.engine;

import dev.thelecrafter.dimensionz.rpg.engine.listener.StatUpdateListeners;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsChangeEvent;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Engine extends JavaPlugin {

    public static Engine INSTANCE;
    public static final EquipmentSlot[] CHECKED_SLOTS = EquipmentSlot.values();
    public static final Attribute[] ATTRIBUTES = new Attribute[]{
            Attribute.GENERIC_ARMOR,
            Attribute.GENERIC_ATTACK_DAMAGE
    };

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new StatUpdateListeners(), INSTANCE);
    }

    public static void refreshPlayerStats(Player player) {
        for (Attribute attribute : ATTRIBUTES) {
            player.getAttribute(attribute).setBaseValue(0);
            for (AttributeModifier modifier : player.getAttribute(attribute).getModifiers()) {
                player.getAttribute(attribute).removeModifier(modifier);
            }
        }
        for (Stat stat : Stat.values()) {
            NamespacedKey key = new NamespacedKey(INSTANCE, stat.toString());
            int value = StatUtils.getBaseValue(stat);
            for (EquipmentSlot slot : CHECKED_SLOTS) {
                if (player.getInventory().getItem(slot) != null) {
                    if (player.getInventory().getItem(slot).hasItemMeta()) {
                        if (player.getInventory().getItem(slot).getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                            value = value + player.getInventory().getItem(slot).getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
                        }
                    }
                }
            }
            Bukkit.getPluginManager().callEvent(new StatsUpdateEvent(player, stat));
            if (player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) != value) {
                Bukkit.getPluginManager().callEvent(new StatsChangeEvent(player, stat, player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER), value));
            }
            player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        }
    }
}
