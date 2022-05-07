package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;
import net.qu.quEnchantments.util.ModTags;

import java.util.Map;
import java.util.Set;

/**
 * A Corrupted Enchantment abstraction with a custom getName(int level) method implementation.
 */
public abstract class CorruptedEnchantment extends Enchantment {

    private final CorruptedEnchantment.EnchantmentType enchantmentType;
    public CorruptedEnchantment(CorruptedEnchantment.EnchantmentType enchantmentType, Rarity weight,
                                EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
        this.enchantmentType = enchantmentType;
    }


    /**
     * Will return the formatted name of the enchantment. Corrupted Enchantments will have a light purple color and will
     * not display the enchantment level if the level is 1. I am considering changing this.
     * @param level The level of the enchantment.
     * @return The formatted name of the enchantment.
     */
    @Override
    public Text getName(int level) {
        TranslatableText mutableText = new TranslatableText(this.getTranslationKey());
        mutableText.formatted(Formatting.LIGHT_PURPLE);
        if (level != 1) {
            mutableText.append(" ").append(new TranslatableText("enchantment.level." + level));
        }
        return mutableText;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof CorruptedEnchantment) {
            return false;
        }
        return super.canAccept(other);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    public EnchantmentType getEnchantmentType() {
        return enchantmentType;
    }

    /**
     * Accepts an ItemStack and, if the stack contains a Corrupted Enchantment, will corrupt all other enchantments of
     * the same type. The Corrupted Enchantment's level will match the highest consumed enchantment's level.
     * @param stack The {@link ItemStack} to corrupt.
     * @return True if successfully corrupted, false otherwise.
     */
    public static boolean corruptEnchantments(ItemStack stack) {
        if(stack == null) {
            return false;
        }
        if (stack.isEmpty() && !stack.hasEnchantments()) {
            return false;
        }
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        CorruptedEnchantment corruptedEnchantment = null;
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (entry.getKey() instanceof CorruptedEnchantment) {
                corruptedEnchantment = (CorruptedEnchantment) entry.getKey();
                break;
            }
        }
        if (corruptedEnchantment == null) {
            return false;
        }
        int levels = 0;
        Set<Enchantment> newSet = Set.copyOf(enchantments.keySet());
        for (Enchantment enchantment : newSet) {
            if (Registry.ENCHANTMENT.getOrCreateEntry(Registry.ENCHANTMENT.getKey(enchantment).get()).isIn(corruptedEnchantment.enchantmentType.corruptible)) {
                levels += enchantments.remove(enchantment);
            }
        }
        enchantments.put(corruptedEnchantment, Math.min(Math.max(EnchantmentHelper.getLevel(corruptedEnchantment, stack), levels), corruptedEnchantment.getMaxLevel()));
        EnchantmentHelper.set(enchantments, stack);
        return true;
    }

    /**
     * Contains a record of enchantment types currently in use by Corrupted Enchantments.
     */
    public enum EnchantmentType {
        DAMAGE(ModTags.Enchantments.WEAPON_DAMAGE_ENCHANTMENTS),
        ASPECT(ModTags.Enchantments.WEAPON_ASPECT_ENCHANTMENTS),

        WALKER(ModTags.Enchantments.ARMOR_FEET_WALKER_ENCHANTMENTS);

        private final TagKey<Enchantment> corruptible;

        EnchantmentType(TagKey<Enchantment> tag) {
            corruptible = tag;
        }

        /**
         * Returns a {@link TagKey} of enchantments given to the type. There is no automatic system that categorizes
         * enchantments. All elements must be added manually through the tag system:
         * {@code src/main/resources/data/qu-enchantments/tags/enchantment/<table to alter>.json}.
         * @return The {@link TagKey} of enchantments given to the type.
         */
        public TagKey<Enchantment> getCorruptibleEnchantments() {
            return corruptible;
        }

    }
}
