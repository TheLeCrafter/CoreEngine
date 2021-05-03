package dev.thelecrafter.dimensionz.rpg.engine.inventories;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.CustomTextureHead;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class StatsInventory implements Listener {

    public static Inventory getInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 3 * 9, ChatColor.GOLD + "Deine Stats");
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta meta = placeholder.getItemMeta();
            meta.setDisplayName("§7");
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            placeholder.setItemMeta(meta);
            inv.setItem(i, placeholder);
        }
        ItemStack health = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThkYTdlMjU1ZTA5YzhiMzc4ZWM4NmMwYjkyMmZhODY0YzRiMTlkMGU1ZTVkYTRkOGM3M2MyYjU2OWMyMjUwMiJ9fX0=");
        ItemMeta healthMeta = health.getItemMeta();
        healthMeta.setDisplayName(StatUtils.getDisplayName(Stat.HEALTH) + " Maximale Leben: " + player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE));
        healthMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Deine Leben bestimmen",
                ChatColor.GRAY + "wie viel Schaden du",
                ChatColor.GRAY + "maximal erhalten darfst."
        ));
        health.setItemMeta(healthMeta);
        inv.setItem(10, health);
        ItemStack defense = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAxY2JmYjQxNDc2MGVmZTUwNGQ0YWY3Mzk3MDhhMThiODliNjE0NWQ3NjU5YmNmNTI2ZjFlNDJkN2JlZGIzNyJ9fX0=");
        ItemMeta defenseMeta = defense.getItemMeta();
        defenseMeta.setDisplayName(StatUtils.getDisplayName(Stat.DEFENSE) + " Rüstung: " + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER));
        defenseMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Rüstung schwächt",
                ChatColor.GRAY + "den Schaden ab, den",
                ChatColor.GRAY + "du erhältst."
        ));
        return inv;
    }

}
