package dev.thelecrafter.dimensionz.rpg.engine.items.swords;

import dev.thelecrafter.dimensionz.rpg.engine.utils.builder.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class StarCollectorSword {

    public static ItemStack getItem() {
        ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD);
        builder.setDisplayName(ChatColor.DARK_PURPLE + "Schwert des Sternensammlers");
        builder.setDamage(30);
        builder.setStrength(90);
        builder.setDoubleDamage(10);
        builder.setAttackSpeed(25);
        builder.setLore(Arrays.asList(
                ChatColor.GOLD + "Fähigkeit: Energie der Sterne",
                ChatColor.GRAY + "Coming soon"
        ));
        ItemStack item = builder.build();
        return item;
    }

}
