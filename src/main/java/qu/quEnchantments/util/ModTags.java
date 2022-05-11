package qu.quEnchantments.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModTags {

    public static class Enchantments {
        public static final TagKey<Enchantment> WEAPON_DAMAGE_ENCHANTMENTS = createEnchantmentTag("weapon_damage_enchantments");
        public static final TagKey<Enchantment> WEAPON_ASPECT_ENCHANTMENTS = createEnchantmentTag("weapon_aspect_enchantments");
        public static final TagKey<Enchantment> ARMOR_FEET_WALKER_ENCHANTMENTS = createEnchantmentTag("armor_feet_walker_enchantments");

        private static TagKey<Enchantment> createEnchantmentTag(String name) {
            return TagKey.of(Registry.ENCHANTMENT_KEY, new Identifier(QuEnchantments.MOD_ID, name));
        }


    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> NIGHTBLOOD_IMMUNE_ENTITIES = createEntityTag("nightblood_immune_entities");
        private static TagKey<EntityType<?>> createEntityTag(String name) {
            return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(QuEnchantments.MOD_ID, name));
        }
    }
}
