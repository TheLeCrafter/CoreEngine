package dev.thelecrafter.dimensionz.rpg.engine.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatUpdateListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

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

    @EventHandler
    public void onCooldown(PlayerItemCooldownEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

    @EventHandler
    public void onCooldownReset(PlayerAttackEntityCooldownResetEvent event) {
        Engine.refreshPlayerStats(event.getPlayer());
    }

}
