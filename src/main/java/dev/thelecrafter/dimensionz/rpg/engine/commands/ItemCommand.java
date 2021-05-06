package dev.thelecrafter.dimensionz.rpg.engine.commands;

import dev.thelecrafter.dimensionz.rpg.engine.items.swords.StarCollectorSword;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getInventory().firstEmpty() > -1) {
                if (args[0] != null) {
                    switch (args[0]) {
                        case "star_collector_sword":
                            player.getInventory().addItem(StarCollectorSword.getItem());
                            break;
                        default:
                            player.sendMessage(ChatColor.RED + "Unknown item!");
                    }
                } else player.sendMessage(ChatColor.RED + "Unknown syntax! Try /item <item>");
            } else player.sendMessage(ChatColor.RED + "Your inventory is full!");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            if ("star_collector_sword".contains(args[0].toLowerCase())) tabComplete.add("star_collector_sword");
        }

        return tabComplete;
    }
}
