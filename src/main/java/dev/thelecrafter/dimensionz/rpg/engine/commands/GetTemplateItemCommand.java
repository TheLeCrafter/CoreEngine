package dev.thelecrafter.dimensionz.rpg.engine.commands;

import com.google.common.collect.ArrayListMultimap;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.builder.ItemBuilder;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GetTemplateItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            ItemBuilder builder = new ItemBuilder(Material.IRON_SWORD);
            builder.setDisplayName(ChatColor.GOLD + "Template Sword");
            builder.setDamage(20);
            builder.setStrength(10);
            builder.setDefense(60);
            builder.setAttackSpeed(160);
            builder.setDoubleDamage(20);
            builder.setLore(Arrays.asList(
                    "§7Dies ist das allmächtige",
                    "§7Template Schwert!",
                    "§7Ich hab eindeutig zu",
                    "§7wenig hobbies..."
            ));
            ((Player) sender).getInventory().addItem(builder.build());
        }
        return true;
    }
}
