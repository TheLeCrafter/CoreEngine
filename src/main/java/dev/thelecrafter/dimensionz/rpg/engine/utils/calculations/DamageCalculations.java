package dev.thelecrafter.dimensionz.rpg.engine.utils.calculations;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

import java.util.UUID;

public class DamageCalculations {

    public static final NamespacedKey BASE_DAMAGE_KEY = new NamespacedKey(Engine.INSTANCE, "base_damage");

    public static double calculateWithDamageStats(int baseDamage, int strength) {
        // Formula base damage + ((base damage * strength) / 100)
        double result = baseDamage;
        double multiplier = baseDamage * strength;
        multiplier = multiplier / 100;
        result = result + multiplier;
        return result;
    }

    public static double calculateWithDefensiveStats(double damage, int health, int defense) {
        // Formula damage - (damage * (defense / defense + 100))
        double result = damage;
        double multiplier = defense / (defense + 100);
        multiplier = damage * multiplier;
        result = result - multiplier;
        return result;
    }

}
