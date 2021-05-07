package dev.thelecrafter.dimensionz.rpg.engine.listener;

import dev.thelecrafter.dimensionz.rpg.engine.items.swords.StarCollectorSword;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemAbilities implements Listener {

    @EventHandler
    public void onInteractWithoutEntity(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().hasItemMeta()) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
                    // Star collector sword
                    if (container.has(StarCollectorSword.ABILITY_KEY, PersistentDataType.STRING)) starCollectorSwordAbility(event);
                }
            }
        }
    }

    public void starCollectorSwordAbility(PlayerInteractEvent event) {
        event.getPlayer().sendMessage(ChatColor.GREEN + "Coming soon!");
    }

}
