package dev.thelecrafter.dimensionz.rpg.engine.utils.items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuItem implements Listener {

    public static ItemStack item() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Menü öffnen");
        meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Klicke mit diesem",
                ChatColor.GRAY + "Um in das Hauptmenü",
                ChatColor.GRAY + "Zu gelangen."
        ));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        return item;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setItem(8, item());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().isSimilar(item())) {
                Bukkit.dispatchCommand(event.getPlayer(), "openstatsmenu");
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().isSimilar(item())) {
            event.setCancelled(true);
            Bukkit.dispatchCommand(event.getPlayer(), "openstatsmenu");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().isSimilar(item())) {
                Bukkit.dispatchCommand(event.getWhoClicked(), "openstatsmenu");
            }
        }
    }

}
