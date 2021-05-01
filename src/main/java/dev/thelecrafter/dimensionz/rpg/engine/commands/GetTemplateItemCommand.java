package dev.thelecrafter.dimensionz.rpg.engine.commands;

import com.google.common.collect.ArrayListMultimap;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
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
            ItemStack itemStack = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta meta = itemStack.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.STRENGTH.toString()), PersistentDataType.INTEGER, 10);
            meta.getPersistentDataContainer().set(DamageCalculations.BASE_DAMAGE_KEY, PersistentDataType.INTEGER, 20);
            meta.setLore(Arrays.asList(
                    "§eSchaden: §a20",
                    "§cStärke: §a10"
            ));
            meta.setAttributeModifiers(ArrayListMultimap.create());
            meta.setDisplayName("§6Template Schwert");
            itemStack.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(itemStack);
        }
        return true;
    }
}
