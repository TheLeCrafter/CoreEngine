package dev.thelecrafter.dimensionz.rpg.engine.stats;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;

public class StatUtils {

    public static final NamespacedKey MAX_HEALTH_KEY = new NamespacedKey(Engine.INSTANCE, "MAX_HEALTH");

    public static int getBaseValue(Stat stat) {
        switch (stat) {
            case HEALTH:
                return 20;
            default:
                return 0;
        }
    }

    public static String getDisplayName(Stat stat) {
        switch (stat) {
            case HEALTH:
                return ChatColor.RED + "❤";
            case DEFENSE:
                return ChatColor.GREEN + "✦";
            case STRENGTH:
                return ChatColor.YELLOW + "⚡";
            default:
                return ChatColor.RED + "§lFEHLER";
        }
    }

}
