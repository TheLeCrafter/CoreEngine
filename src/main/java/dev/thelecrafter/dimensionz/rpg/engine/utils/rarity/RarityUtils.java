package dev.thelecrafter.dimensionz.rpg.engine.utils.rarity;

import net.md_5.bungee.api.ChatColor;

public class RarityUtils {

    public static String getDisplayName(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return "§f§lGEWÖHNLICH";
            case RARE:
                return "§9§lSELTEN";
            case EPIC:
                return "§d§lEPISCH";
            case LEGENDARY:
                return "§6§lLEGENDÄR";
            case MIRRORED:
                return "§b§lGESPIEGELT";
            default:
                return null;
        }
    }

    public static ChatColor getChatColor(Rarity rarity) {
        switch (rarity) {
            case COMMON:
                return ChatColor.WHITE;
            case RARE:
                return ChatColor.BLUE;
            case EPIC:
                return ChatColor.LIGHT_PURPLE;
            case LEGENDARY:
                return ChatColor.GOLD;
            case MIRRORED:
                return ChatColor.AQUA;
            default:
                return null;
        }
    }

}
