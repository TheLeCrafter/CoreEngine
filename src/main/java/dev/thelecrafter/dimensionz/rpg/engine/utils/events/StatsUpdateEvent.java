package dev.thelecrafter.dimensionz.rpg.engine.utils.events;

import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class StatsUpdateEvent extends Event {
    public static final HandlerList HANDLER_LIST = new HandlerList();
    public final Player player;
    public final Stat stat;

    public StatsUpdateEvent(Player player, Stat stat) {
        this.player = player;
        this.stat = stat;
    }

    public Player getPlayer() {
        return player;
    }

    public Stat getStat() {
        return stat;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
