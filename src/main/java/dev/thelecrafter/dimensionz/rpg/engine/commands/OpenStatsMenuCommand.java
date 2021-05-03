package dev.thelecrafter.dimensionz.rpg.engine.commands;

import dev.thelecrafter.dimensionz.rpg.engine.inventories.StatsInventory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class OpenStatsMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getOpenInventory() != null) {
                player.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
            }
            player.openInventory(StatsInventory.getInventory(player));
        }
        return true;
    }
}
