package dev.thelecrafter.dimensionz.rpg.engine.items.swords;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.builder.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StarCollectorSword {

    public static final NamespacedKey ID_KEY = new NamespacedKey(Engine.INSTANCE, "star_collector_sword");
    public static final NamespacedKey ABILITY_KEY = new NamespacedKey(Engine.INSTANCE, "star_collector_ability");

    public static ItemStack getItem() {
        ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD);
        builder.setDisplayName(ChatColor.DARK_PURPLE + "Schwert des Sternensammlers");
        builder.setDamage(30);
        builder.setStrength(90);
        builder.setDoubleDamage(10);
        builder.setAttackSpeed(25);
        Map<NamespacedKey, String> keyStringMap = new HashMap<>();
        keyStringMap.put(ID_KEY, "item_id");
        keyStringMap.put(ABILITY_KEY, "ability_id");
        builder.setNamespacedKeys(keyStringMap);
        builder.setLore(Arrays.asList(
                ChatColor.GOLD + "Fähigkeit: Energie der Sterne",
                ChatColor.GRAY + "Coming soon"
        ));
        ItemStack item = builder.build();
        return item;
    }

}
