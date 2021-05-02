package dev.thelecrafter.dimensionz.rpg.engine.utils.calculations;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

import java.util.Random;
import java.util.UUID;

public class DamageCalculations {

    public static final NamespacedKey BASE_DAMAGE_KEY = new NamespacedKey(Engine.INSTANCE, "base_damage");

    public static double calculateWithDamageStats(int baseDamage, int strength) {
        // Formula base damage + ((base damage * strength) / 100)
        return baseDamage + ((baseDamage * strength) / 100);
    }

    public static double calculateDoubleDamage(double damage, int doubleDamageChance) {
        Random random = new Random();
        int result = random.nextInt(100) + 1;
        if (result <= doubleDamageChance) {
            damage = damage * 2;
        }
        return damage;
    }

    public static double calculateWithDefensiveStats(double damage, int defense) {
        // Formula damage - (damage * ((defense + 1) / (defense + 1) + 100))
        return damage - (damage * ((defense + 1) / ((defense + 1) + 100)));
    }

}
