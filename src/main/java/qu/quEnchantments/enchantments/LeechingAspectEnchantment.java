package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class LeechingAspectEnchantment extends Enchantment {
    public LeechingAspectEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, false, false, false));
        user.heal(0.25f * level);
        Random random = user.world.getRandom();
        double d = random.nextGaussian() * 0.02;
        double e = random.nextGaussian() * 0.02;
        double f = random.nextGaussian() * 0.02;
        ((ServerWorld) user.world).spawnParticles(ParticleTypes.HEART, user.getParticleX(1.0), user.getRandomBodyY(), user.getParticleZ(1.0), 1, d, e, f, 0.0);
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
}
