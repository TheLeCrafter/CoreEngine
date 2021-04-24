package dev.thelecrafter.dimensionz.rpg.engine;

import dev.thelecrafter.dimensionz.rpg.engine.listener.StatUpdateListeners;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Engine extends JavaPlugin {

    public static Engine INSTANCE;
    public static final EquipmentSlot[] CHECKED_SLOTS = EquipmentSlot.values();

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new StatUpdateListeners(), INSTANCE);
    }

    public static void refreshPlayerStats(Player player) {
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
        }
    }
}
