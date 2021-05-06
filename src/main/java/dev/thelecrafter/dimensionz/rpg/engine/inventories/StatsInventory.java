package dev.thelecrafter.dimensionz.rpg.engine.inventories;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.CustomTextureHead;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsChangeEvent;
import dev.thelecrafter.dimensionz.rpg.engine.utils.events.StatsUpdateEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class StatsInventory implements Listener {

    public static final String TITLE = ChatColor.GOLD + "Deine Stats";

    public static Inventory getInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 3 * 9, TITLE);
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
        healthMeta.setDisplayName(StatUtils.getDisplayName(Stat.HEALTH) + " Leben: " + Math.round(player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.DOUBLE)) + "/" + player.getPersistentDataContainer().get(StatUtils.MAX_HEALTH_KEY, PersistentDataType.DOUBLE));
        healthMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Deine Leben bestimmen",
                ChatColor.GRAY + "wie viel Schaden du",
                ChatColor.GRAY + "maximal erhalten darfst."
        ));
        health.setItemMeta(healthMeta);
        inv.setItem(10, health);
        ItemStack defense = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJkMzFhZDFlOTNmMTY2Zjk3ODU5Y2Q5ZjJiODBkYTUyYTgyOTQ0MDA5N2Y1ZDY3YThjMjEwZDEyMmI5ZDVlNSJ9fX0=");
        ItemMeta defenseMeta = defense.getItemMeta();
        defenseMeta.setDisplayName(StatUtils.getDisplayName(Stat.DEFENSE) + " Rüstung: " + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER));
        defenseMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Rüstung schwächt",
                ChatColor.GRAY + "den Schaden ab, den",
                ChatColor.GRAY + "du erhältst."
        ));
        defense.setItemMeta(defenseMeta);
        inv.setItem(11, defense);
        ItemStack strength = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzNkNjlhNjBkOTcwYWQwYjhhYTE1ODk3OTE0ZjVhYWMyNjVlOTllNmY1MDE2YTdkOGFhN2JlOWFjMDNiNjE0OCJ9fX0=");
        ItemMeta strengthMeta = strength.getItemMeta();
        strengthMeta.setDisplayName(StatUtils.getDisplayName(Stat.STRENGTH) + " Stärke: " + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.STRENGTH.toString()), PersistentDataType.INTEGER));
        strengthMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Stärke erhöht den",
                ChatColor.GRAY + "Schaden, den du Gegnern",
                ChatColor.GRAY + "zufügst."
        ));
        strength.setItemMeta(strengthMeta);
        inv.setItem(12, strength);
        ItemStack attackSpeed = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEzZmUyODA3ZGMwZWE1ZGQwNDk1ZDI2N2VjM2U1NWNhMzc1ZWE2MjExNThjYTAyYzQyMzUwYzQzNWQ3OTc3MSJ9fX0=");
        ItemMeta attackSpeedMeta = attackSpeed.getItemMeta();
        attackSpeedMeta.setDisplayName(StatUtils.getDisplayName(Stat.ATTACK_SPEED) + " Feinfühligkeit: " + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.ATTACK_SPEED.toString()), PersistentDataType.INTEGER));
        attackSpeedMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Bestimmt wie schnell",
                ChatColor.GRAY + "du zuschlagen kannst."
        ));
        attackSpeed.setItemMeta(attackSpeedMeta);
        inv.setItem(13, attackSpeed);
        ItemStack doubleDamage = CustomTextureHead.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY4OTZjMDg4NmJmOTQ4NjIzOWRlODQ0Mzc2ODI4ODgyOWRhNjJmYTJhNGI3ZWJkNzhhZjQxNWQ0N2IyMThjMSJ9fX0=");
        ItemMeta doubleDamageMeta = doubleDamage.getItemMeta();
        doubleDamageMeta.setDisplayName(StatUtils.getDisplayName(Stat.DOUBLE_DAMAGE) + " Raserei: " + player.getPersistentDataContainer().get(new NamespacedKey(Engine.INSTANCE, Stat.DOUBLE_DAMAGE.toString()), PersistentDataType.INTEGER));
        doubleDamageMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "Die Chance, dass",
                ChatColor.GRAY + "dein Gegner bei einem",
                ChatColor.GRAY + "Schlag doppelten",
                ChatColor.GRAY + "Schaden erhält"
        ));
        doubleDamage.setItemMeta(doubleDamageMeta);
        inv.setItem(14, doubleDamage);
        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStatsUpdate(StatsUpdateEvent event) {
        if (event.getPlayer().getOpenInventory() != null) {
            if (event.getPlayer().getOpenInventory().getTitle() != null) {
                if (event.getPlayer().getOpenInventory().getTitle().equals(TITLE)) {
                    for (int slot = 0; slot < getInventory(event.getPlayer()).getSize(); slot++) {
                        event.getPlayer().getOpenInventory().setItem(slot, getInventory(event.getPlayer()).getItem(slot));
                    }
                }
            }
        }
    }

}
