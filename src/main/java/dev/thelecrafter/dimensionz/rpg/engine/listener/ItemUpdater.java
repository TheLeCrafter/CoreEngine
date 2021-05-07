package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.items.swords.StarCollectorSword;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemUpdater implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for (ItemStack item : event.getPlayer().getInventory()) {
            PersistentDataContainer container = item.getPersistentDataContainer();
            // Star Collector Sword
            if (container.has(StarCollectorSword.ID_KEY, PersistentDataType.STRING)) item.setItemMeta(StarCollectorSword.getItem().getItemMeta());
        }
    }

}
