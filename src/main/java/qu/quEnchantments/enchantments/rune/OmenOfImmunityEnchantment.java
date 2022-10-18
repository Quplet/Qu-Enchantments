package qu.quEnchantments.enchantments.rune;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

public class OmenOfImmunityEnchantment extends CorruptedEnchantment {

    private static final ModConfig.OmenOfImmunityOptions CONFIG = QuEnchantments.getConfig().omenOfImmunityOptions;

    public OmenOfImmunityEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level * 20;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 5 : 0;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.EnchantingTable;
    }

    @Override
    public void tickWhileEquipped(LivingEntity wearer, ItemStack stack, int level) {
        if (!(wearer instanceof PlayerEntity player && player.getAbilities().creativeMode) && wearer.age % 20 == 0) {
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 6 - level));
            if (stack.getDamage() >= stack.getMaxDamage() && CONFIG.breakOnNoDurability) stack.damage(1, wearer, e -> e.sendEquipmentBreakStatus(wearer.getStackInHand(Hand.MAIN_HAND) == stack ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
        }
        wearer.clearStatusEffects();
        wearer.extinguish();
        wearer.setFrozenTicks(0);
        ((IEntity)wearer).setInaneTicks(0);
    }
}
