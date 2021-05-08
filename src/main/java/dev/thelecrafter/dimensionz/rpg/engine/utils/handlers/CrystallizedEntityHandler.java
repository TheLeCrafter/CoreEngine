package dev.thelecrafter.dimensionz.rpg.engine.utils.handlers;

import dev.thelecrafter.dimensionz.rpg.engine.Engine;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.Stat;
import dev.thelecrafter.dimensionz.rpg.engine.utils.stats.StatUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class CrystallizedEntityHandler implements Listener {

    public static final NamespacedKey CRYSTALLIZED_ENTITY_KEY = new NamespacedKey(Engine.INSTANCE, "crystallized_entity");

    // THIS WILL BE USED AT ANOTHER TIME

}
