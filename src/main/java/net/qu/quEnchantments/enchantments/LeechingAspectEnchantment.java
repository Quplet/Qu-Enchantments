package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;

import java.util.Random;

public class LeechingAspectEnchantment extends Enchantment {
    public LeechingAspectEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof FireAspectEnchantment || other instanceof FreezingAspectEnchantment) {
            return false;
        }
        return super.canAccept(other);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public static void leech(LivingEntity user, LivingEntity target, int level) {
        if (!target.world.isClient()) {
            user.heal(((float) level)/2);
        } else {
            Random random = new Random();
            double d = random.nextGaussian() * 0.02;
            double e = random.nextGaussian() * 0.02;
            double f = random.nextGaussian() * 0.02;
            target.world.addParticle(ParticleTypes.HEART, user.getParticleX(1.0), user.getRandomBodyY(), user.getParticleZ(1.0), d, e, f);
        }
    }
}
