package qu.quEnchantments.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

public class ModTags {

    public static final TagKey<Enchantment> WEAPON_DAMAGE_ENCHANTMENTS = enchantmentTagKeyOf("weapon_damage_enchantments");
    public static final TagKey<Enchantment> WEAPON_ASPECT_ENCHANTMENTS = enchantmentTagKeyOf("weapon_aspect_enchantments");
    public static final TagKey<Enchantment> ARMOR_FEET_WALKER_ENCHANTMENTS = enchantmentTagKeyOf("armor_feet_walker_enchantments");
    public static final TagKey<Enchantment> ARMOR_THORNS_ENCHANTMENTS = enchantmentTagKeyOf("armor_thorns_enchantments");
    public static final TagKey<Enchantment> RUNE_ENCHANTMENTS = enchantmentTagKeyOf("rune_enchantments");
    public static final TagKey<Enchantment> MINING_TOOL_DROP_ENCHANTMENTS = enchantmentTagKeyOf("mining_tool_drop_enchantments");
    public static final TagKey<Enchantment> PREVENTS_FEET_BURNING = enchantmentTagKeyOf("prevents_feet_burning");

    public static final TagKey<EntityType<?>> NIGHTBLOOD_IMMUNE_ENTITIES = entityTypeTagKeyOf("nightblood_immune_entities");

    public static final TagKey<Item> SHIELD_ENCHANTABLE = itemTagKeyOf("enchantable/shield");
    public static final TagKey<Item> RUNE_ENCHANTABLE = itemTagKeyOf("enchantable/rune");
    public static final TagKey<Item> HORSE_ARMOR_ENCHANTABLE = itemTagKeyOf("enchantable/horse_armor");

    private static TagKey<Enchantment> enchantmentTagKeyOf(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(QuEnchantments.MOD_ID, id));
    }

    private static TagKey<EntityType<?>> entityTypeTagKeyOf(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(QuEnchantments.MOD_ID, id));
    }

    private static TagKey<Item> itemTagKeyOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(QuEnchantments.MOD_ID, id));
    }

}
