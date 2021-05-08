package dev.thelecrafter.dimensionz.rpg.engine.items.crafting;

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

public class StarSplitter {

    public static final NamespacedKey ID_KEY = new NamespacedKey(Engine.INSTANCE, "star_splitter");

    public static ItemStack getItem() {
        ItemBuilder builder = new ItemBuilder(Material.QUARTZ);
        Map<NamespacedKey, String> namespacedKeyStringMap = new HashMap<>();
        namespacedKeyStringMap.put(ID_KEY, "true");
        builder.setRarity(Rarity.EPIC);
        builder.setDisplayName("Sternsplitter");
        builder.setNamespacedKeys(namespacedKeyStringMap);
        builder.setLore(Arrays.asList(
                "",
                ChatColor.GRAY + "Dieser Splitter kommt",
                ChatColor.GRAY + "aus den großen Weiten des",
                ChatColor.GRAY + "Universums und birgt",
                ChatColor.GRAY + "eine mächtige Energie",
                "",
                ChatColor.DARK_GRAY + "Klicke, um die Rezepte zu sehen"
        ));
        ItemStack item = builder.build();
        return item;
    }

}
