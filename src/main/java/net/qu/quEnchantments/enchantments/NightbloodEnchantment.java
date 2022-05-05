package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;

import java.util.Random;

public class NightbloodEnchantment extends CorruptedEnchantment {

    protected NightbloodEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static void onTargetHit(LivingEntity user, LivingEntity target) {
        if (!(target instanceof EnderDragonEntity) && !(target instanceof WitherEntity) && !(target instanceof WitherSkeletonEntity)) {
            if (!target.world.isClient()) {
                if (user instanceof PlayerEntity) {
                    target.damage(DamageSource.player((PlayerEntity) user), Float.MAX_VALUE);
                } else {
                    target.damage(DamageSource.mob(user), Float.MAX_VALUE);
                }
            }
            Random random = new Random();
            for (int i = 0; i < 20; ++i) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                target.world.addParticle(ParticleTypes.SMOKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), d, e, f);
            }
        }
    }

    public static void drain(LivingEntity user, int level) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            if (!player.getAbilities().creativeMode) {
                if (player.experienceLevel > 0 || player.experienceProgress > 0) {
                    player.addExperience(-2 / level);
                } else if (user.world.getDifficulty().getId() != 0 && player.getHungerManager().getFoodLevel() > 0) {
                    player.getHungerManager().addExhaustion(1.0f / level);
                } else {
                    player.damage(DamageSource.MAGIC, 2 / level);
                }
            }
        } else {
            user.damage(DamageSource.MAGIC, 2 / level);
        }
    }
}
