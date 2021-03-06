package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class FreezingAspectEnchantment extends Enchantment {
    public FreezingAspectEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof FireAspectEnchantment || other instanceof LeechingAspectEnchantment) {
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

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.world.isClient) {
            target.extinguish();
            if (target.canFreeze()) {
                target.setFrozenTicks(target.getMinFreezeDamageTicks() + 75 * level);
            }
            Random random = target.world.getRandom();
            for (int i = 0; i < 20; ++i) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                ((ServerWorld) target.world).spawnParticles(ParticleTypes.SNOWFLAKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, d, e, f, 0.0);
            }
        }
    }
}
