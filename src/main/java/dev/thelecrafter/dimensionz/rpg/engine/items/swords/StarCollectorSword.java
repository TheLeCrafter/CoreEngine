package dev.thelecrafter.dimensionz.rpg.engine.items.swords;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.builder.ItemBuilder;
import dev.thelecrafter.dimensionz.rpg.engine.utils.rarity.Rarity;
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
        builder.setDisplayName("Schwert des Sternensammlers");
        builder.setDamage(60);
        builder.setStrength(25);
        builder.setAttackSpeed(35);
        builder.setDoubleDamage(5);
        builder.setRarity(Rarity.EPIC);
        Map<NamespacedKey, String> keyStringMap = new HashMap<>();
        keyStringMap.put(ID_KEY, "item_id");
        keyStringMap.put(ABILITY_KEY, "ability_id");
        builder.setNamespacedKeys(keyStringMap);
        builder.setLore(Arrays.asList(
                ChatColor.GOLD + "Fähigkeit: Energie der Sterne",
                ChatColor.GRAY + "Erhöht die Chance [STERNSPLITTER DISPLAY NAME]",
                ChatColor.GRAY + "zu erhalten um " + ChatColor.YELLOW + "50%"
        ));
        ItemStack item = builder.build();
        return item;
    }

}
