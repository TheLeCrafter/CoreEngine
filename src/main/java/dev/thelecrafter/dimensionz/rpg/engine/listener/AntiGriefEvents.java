package dev.thelecrafter.dimensionz.rpg.engine.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class AntiGriefEvents implements Listener {

    @EventHandler
    public void onArmorStandDeath(EntityDeathEvent event) {
        if (event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStand(PlayerArmorStandManipulateEvent event) {
        if (!event.getPlayer().hasPermission("coreengine.grief.armorstand")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("coreengine.grief.world.place")) {
            event.setBuild(false);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("coreengine.grief.world.break")) {
            event.setCancelled(true);
        }
    }

}
