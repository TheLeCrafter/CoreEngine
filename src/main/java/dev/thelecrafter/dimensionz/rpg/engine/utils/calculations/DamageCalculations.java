package dev.thelecrafter.dimensionz.rpg.engine.utils.calculations;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import org.bukkit.NamespacedKey;

public class DamageCalculations {

    public static final NamespacedKey BASE_DAMAGE_KEY = new NamespacedKey(Engine.INSTANCE, "base_damage");

    public static double calculateWithStats(int baseDamage, int strength) {
        // Formula base damage + ((base damage * strength) / 100)
        double result = baseDamage;
        double multiplier = baseDamage * strength;
        multiplier = multiplier / 100;
        result = result + multiplier;
        return result;
    }

}
