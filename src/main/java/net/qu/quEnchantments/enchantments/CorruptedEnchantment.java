package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Map;

/**
 * A Corrupted Enchantment abstraction with a custom getName(int level) method implementation.
 */
public abstract class CorruptedEnchantment extends Enchantment {

    private CorruptedEnchantment.EnchantmentType enchantmentType;
    public CorruptedEnchantment(CorruptedEnchantment.EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
        this.enchantmentType = enchantmentType;
    }

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
        for (Enchantment enchantment : corruptedEnchantment.enchantmentType.getCorruptableEnchantments()) {
            Integer i = enchantments.remove(enchantment);
            levels += (i == null) ? 0 : i.intValue();
        }
        enchantments.put(corruptedEnchantment, Math.min(Math.max(1, levels), corruptedEnchantment.getMaxLevel()));
        EnchantmentHelper.set(enchantments, stack);
        return true;
    }

    public enum EnchantmentType {
        DAMAGE(Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SMITE),
        ASPECT(Enchantments.FIRE_ASPECT, ModEnchantments.FREEZING_ASPECT, ModEnchantments.LEECHING_ASPECT);

        private final Enchantment[] corruptable;

        private EnchantmentType(Enchantment ... enchantments) {
            corruptable = enchantments;
        }

        public Enchantment[] getCorruptableEnchantments() {
            return corruptable;
        }

    }
}
