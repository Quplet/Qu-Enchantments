package net.qu.quEnchantments.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.qu.quEnchantments.QuEnchantments;

public class ModTags {

    public static class Enchantments {
        public static final TagKey<Enchantment> WEAPON_DAMAGE_ENCHANTMENTS = createTag("weapon_damage_enchantments");
        public static final TagKey<Enchantment> WEAPON_ASPECT_ENCHANTMENTS = createTag("weapon_aspect_enchantments");
        public static final TagKey<Enchantment> ARMOR_FEET_WALKER_ENCHANTMENTS = createTag("armor_feet_walker_enchantments");

        private static TagKey<Enchantment> createTag(String name) {
            return TagKey.of(Registry.ENCHANTMENT_KEY, new Identifier(QuEnchantments.MOD_ID, name));
        }
    }
}
