package dev.thelecrafter.dimensionz.rpg.engine.utils.stats;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;

public class StatUtils {

    public static final NamespacedKey MAX_HEALTH_KEY = new NamespacedKey(Engine.INSTANCE, "MAX_HEALTH");

    public static int getBaseValue(Stat stat) {
        switch (stat) {
            case HEALTH:
                return 20;
            case ATTACK_SPEED:
                return 100;
            default:
                return 0;
        }
    }

    public static int getMinValue(Stat stat) {
        switch (stat) {
            case HEALTH:
                return 5;
            case ATTACK_SPEED:
                return 1;
            default:
                return -1; // That means no cap
        }
    }

    public static int getMaxValue(Stat stat) {
        switch (stat) {
            case ATTACK_SPEED:
                return 300;
            case DOUBLE_DAMAGE:
                return 100;
            default:
                return -1; // That means no cap
        }
    }

    public static String getDisplayName(Stat stat) {
        switch (stat) {
            case HEALTH:
                return ChatColor.RED + "❤";
            case DEFENSE:
                return ChatColor.GREEN + "✦";
            case STRENGTH:
                return ChatColor.BLUE + "⚡";
            case ATTACK_SPEED:
                return ChatColor.YELLOW + "⌛";
            case DOUBLE_DAMAGE:
                return ChatColor.RED + "✴";
            default:
                return ChatColor.RED + "§lFEHLER";
        }
    }

}
