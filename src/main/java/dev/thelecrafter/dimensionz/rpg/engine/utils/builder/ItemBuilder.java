package dev.thelecrafter.dimensionz.rpg.engine.utils.builder;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.rarity.Rarity;
import dev.thelecrafter.dimensionz.rpg.engine.utils.rarity.RarityUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.StatUtils;
import dev.thelecrafter.dimensionz.rpg.engine.utils.calculations.DamageCalculations;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder {

    private List<String> lore = null;
    private String displayName = null;
    private int damage = 0;
    private int strength = 0;
    private int defense = 0;
    private int health = 0;
    private int attack_speed = 0;
    private int double_damage = 0;
    private final Material material;
    private Map<NamespacedKey, String> namespacedKeys = null;
    private Rarity rarity = Rarity.COMMON;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public @Nullable String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public @Nullable List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public Material getMaterial() {
        return material;
    }

    public @Nullable Map<NamespacedKey, String> getNamespacedKeys() {
        return namespacedKeys;
    }

    public void setNamespacedKeys(Map<NamespacedKey, String> namespacedKeys) {
        this.namespacedKeys = namespacedKeys;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackSpeed() {
        return attack_speed;
    }

    public void setAttackSpeed(int attack_speed) {
        this.attack_speed = attack_speed;
    }

    public int getDoubleDamage() {
        return double_damage;
    }

    public void setDoubleDamage(int double_damage) {
        this.double_damage = double_damage;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (displayName != null) meta.setDisplayName(RarityUtils.getChatColor(rarity) + displayName);
        List<String> lore = new ArrayList<>();
        if (damage > 0) {
            lore.add(ChatColor.RED + "??? Schaden: " + ChatColor.GREEN + damage);
            meta.getPersistentDataContainer().set(DamageCalculations.BASE_DAMAGE_KEY, PersistentDataType.INTEGER, damage);
        }
        if (strength > 0) {
            lore.add(StatUtils.getDisplayName(Stat.STRENGTH) + " St??rke: " + ChatColor.GREEN + strength);
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.STRENGTH.toString()), PersistentDataType.INTEGER, strength);
        }
        if (attack_speed > 0) {
            lore.add(StatUtils.getDisplayName(Stat.ATTACK_SPEED) + " Feinf??hligkeit: " + ChatColor.GREEN + attack_speed);
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.ATTACK_SPEED.toString()), PersistentDataType.INTEGER, attack_speed);
        }
        if (double_damage > 0) {
            lore.add(StatUtils.getDisplayName(Stat.DOUBLE_DAMAGE) + " Raserei: " + ChatColor.GREEN + double_damage);
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.DOUBLE_DAMAGE.toString()), PersistentDataType.INTEGER, double_damage);
        }
        if (health > 0) {
            lore.add(StatUtils.getDisplayName(Stat.HEALTH) + " Leben: " + ChatColor.GREEN + health);
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.HEALTH.toString()), PersistentDataType.INTEGER, health);
        }
        if (defense > 0) {
            lore.add(StatUtils.getDisplayName(Stat.DEFENSE) + " R??stung: " + ChatColor.GREEN + defense);
            meta.getPersistentDataContainer().set(new NamespacedKey(Engine.INSTANCE, Stat.DEFENSE.toString()), PersistentDataType.INTEGER, defense);
        }
        if (lore != null) {
            lore.add("");
            lore.addAll(this.lore);
        }
        lore.add("");
        lore.add(RarityUtils.getDisplayName(rarity));
        meta.setLore(lore);
        Multimap<Attribute, AttributeModifier> multimap = ArrayListMultimap.create();
        multimap.put(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "REPLACE_ALL", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setAttributeModifiers(multimap);
        if (namespacedKeys != null) {
            for (NamespacedKey key : namespacedKeys.keySet()) {
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, namespacedKeys.get(key));
            }
        }
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        return item;
    }
}
