package dev.thelecrafter.dimensionz.rpg.engine;

import dev.thelecrafter.dimensionz.rpg.engine.commands.GetTemplateItemCommand;
import dev.thelecrafter.dimensionz.rpg.engine.commands.ItemCommand;
import dev.thelecrafter.dimensionz.rpg.engine.commands.OpenStatsMenuCommand;
import dev.thelecrafter.dimensionz.rpg.engine.inventories.StatsInventory;
import dev.thelecrafter.dimensionz.rpg.engine.listener.AntiGriefEvents;
import dev.thelecrafter.dimensionz.rpg.engine.listener.DamageListeners;
import dev.thelecrafter.dimensionz.rpg.engine.listener.ItemAbilities;
import dev.thelecrafter.dimensionz.rpg.engine.listener.StatUpdateListeners;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsChangeEvent;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsUpdateEvent;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.DamageStandsHandler;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.HealthStandsHandler;
import dev.thelecrafter.dimensionz.rpg.engine.utils.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
        Bukkit.getPluginManager().registerEvents(new DamageListeners(), INSTANCE);
        Bukkit.getPluginManager().registerEvents(new HealthStandsHandler(), INSTANCE);
        Bukkit.getPluginManager().registerEvents(new MenuItem(), INSTANCE);
        Bukkit.getPluginManager().registerEvents(new StatsInventory(), INSTANCE);
        Bukkit.getPluginManager().registerEvents(new AntiGriefEvents(), INSTANCE);
        Bukkit.getPluginManager().registerEvents(new ItemAbilities(), INSTANCE);
        getCommand("gettemplateitem").setExecutor(new GetTemplateItemCommand());
        getCommand("openstatsmenu").setExecutor(new OpenStatsMenuCommand());
        getCommand("item").setExecutor(new ItemCommand());
        getCommand("item").setTabCompleter(new ItemCommand());
        DamageListeners.initCustomPlayerRegeneration(2, 1, 80);
    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getPersistentDataContainer().has(DamageStandsHandler.TEMPORARY_STAND_KEY, PersistentDataType.STRING)) {
                    entity.remove();
                } else if (entity.getPersistentDataContainer().has(HealthStandsHandler.TEMPORARY_STAND_KEY, PersistentDataType.STRING)) {
                    entity.remove();
                }
            }
        }
    }

    public static void refreshPlayerStats(Player player) {
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);
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
            if (StatUtils.getMaxValue(stat) > 0) {
                if (StatUtils.getMaxValue(stat) <= value) {
                    value = StatUtils.getMaxValue(stat);
                }
            }
            if (StatUtils.getMinValue(stat) > 0) {
                if (StatUtils.getMinValue(stat) >= value) {
                    value = StatUtils.getMinValue(stat);
                }
            }
            Bukkit.getPluginManager().callEvent(new StatsUpdateEvent(player, stat));
            if (player.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                if (player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER) != value) {
                    Bukkit.getPluginManager().callEvent(new StatsChangeEvent(player, stat, player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER), value));
                }
            }
            if (stat.equals(Stat.HEALTH)) {
                player.getPersistentDataContainer().set(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE, Double.parseDouble(String.valueOf(value)));
                if (!player.getPersistentDataContainer().has(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) {
                    player.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, Double.parseDouble(String.valueOf(value)));
                }
            } else {
                player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
            }
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        player.setHealthScaled(false);
        DamageListeners.setPlayerHealth(player);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue((player.getPersistentDataContainer().get(new NamespacedKey(INSTANCE, Stat.ATTACK_SPEED.toString()), PersistentDataType.INTEGER) / 100) * 2);
    }

    public static void healPlayer(Player player, double healthToHeal, boolean capMaxHeal) {
        // Add player heal event
        if (capMaxHeal) {
            if (healthToHeal + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) > player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE)) {
                healthToHeal = player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE) - player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE);
            }
        } if (healthToHeal > 0) Bukkit.getPluginManager().callEvent(new StatsChangeEvent(player, Stat.HEALTH, player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE), player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) + healthToHeal));
        player.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE, player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE) + healthToHeal);
    }
}
