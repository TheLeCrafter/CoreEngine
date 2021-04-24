package dev.thelecrafter.dimensionz.rpg.engine.stats;

public class StatUtils {

    public static int getBaseValue(Stat stat) {
        switch (stat) {
            case HEALTH:
                return 20;
            default:
                return 0;
        }
    }

}
