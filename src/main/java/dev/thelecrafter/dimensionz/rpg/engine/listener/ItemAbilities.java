package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.items.crafting.StarSplitter;
import dev.thelecrafter.dimensionz.rpg.engine.items.swords.StarCollectorSword;
import dev.thelecrafter.dimensionz.rpg.engine.utils.handlers.CrystallizedEntityHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class ItemAbilities implements Listener {

    @EventHandler
    public void onInteractWithoutEntity(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().hasItemMeta()) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
                    // Item abilities
                    // Crafting recipes
                    if (container.has(StarSplitter.ID_KEY, PersistentDataType.STRING)) starSplitterRecipe(event);
                }
            }
        }
    }

    @EventHandler
    public void dropCustomItems(EntityDeathEvent event) {
        // STAR SPLITTER
        if (event.getEntity().getPersistentDataContainer().has(CrystallizedEntityHandler.CRYSTALLIZED_ENTITY_KEY, PersistentDataType.STRING)) starSplitterDrop(event);
    }

    // CRAFTING RECIPES
    public void starSplitterRecipe(PlayerInteractEvent event) {
        event.getPlayer().sendMessage(ChatColor.RED + "Coming soon");
    }

    // DROP ITEMS
    public void starSplitterDrop(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (event.getEntity().getKiller().getInventory().getItem(EquipmentSlot.HAND) != null) {
                if (event.getEntity().getKiller().getInventory().getItem(EquipmentSlot.HAND).hasItemMeta()) {
                    double chance = 25;
                    if (event.getEntity().getKiller().getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getPersistentDataContainer().has(StarCollectorSword.ID_KEY, PersistentDataType.STRING)) {
                        chance = chance * 1.5;
                    }
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    if (random.nextInt() <= chance) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), StarSplitter.getItem());
                    }
                }
            }
        }
    }
}
