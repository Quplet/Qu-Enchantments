package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class ShapedGlassEnchantment extends CorruptedEnchantment {

    protected ShapedGlassEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level * 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        user.getMainHandStack().damage(20 + level * 2, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }
}
