package dev.thelecrafter.dimensionz.rpg.engine.utils.events;

import dev.thelecrafter.dimensionz.rpg.engine.stats.Stat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class StatsChangeEvent extends Event {
    public static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private final Stat stat;
    private final double from;
    private final double to;

    public StatsChangeEvent(Player player, Stat stat, double from, double to) {
        this.player = player;
        this.stat = stat;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public Stat getStat() {
        return stat;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
