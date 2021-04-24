package dev.thelecrafter.dimensionz.rpg.engine.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class StatUpdateListeners implements Listener {

    @EventHandler
    public void onArmorSwitch(PlayerArmorChangeEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

}
