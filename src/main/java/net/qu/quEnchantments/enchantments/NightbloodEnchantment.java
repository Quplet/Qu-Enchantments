package net.qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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

    protected NightbloodEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
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

    /**
     * Custom implementation of {@link net.minecraft.enchantment.Enchantment#onTargetDamaged(LivingEntity, Entity, int)
     * onTargetDamaged} that allows for client side activity. Unlike
     * {@link net.minecraft.enchantment.Enchantment#onTargetDamaged(LivingEntity, Entity, int) onTargetDamaged},
     * this will not automatically be called. Has to called manually from an on hit event.
     * <p>If the user hit with a weapon that has the Nightblood Enchantment on it, will instakill all non-boss enemies
     * (excluding Wither Skeleton). This does include players. Upon instakill, will create smoke particles around the
     * target.
     * @param user The {@link LivingEntity} attacker.
     * @param target the {@link Entity} subject being attacked.
     */
    public static void onTargetHit(LivingEntity user, Entity target) {
        if (target.getType() != EntityType.ENDER_DRAGON && target.getType() != EntityType.WITHER && target.getType() != EntityType.WITHER_SKELETON) {
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

    /**
     * Will remove 2/level xp from the user until drained, then hunger, then health.
     * @param user The {@link LivingEntity} to drain.
     * @param level The level of the enchantment.
     */
    public static void drain(LivingEntity user, int level) {
        if (user instanceof PlayerEntity player) {
            if (!player.getAbilities().creativeMode) {
                if (player.experienceLevel > 0 || player.experienceProgress > 0) {
                    player.addExperience(-2 / level);
                } else if (user.world.getDifficulty().getId() != 0 && player.getHungerManager().getFoodLevel() > 0) {
                    player.getHungerManager().addExhaustion(1.0f / level);
                } else {
                    player.damage(DamageSource.MAGIC, 2.0f / level);
                }
            }
        } else {
            user.damage(DamageSource.MAGIC, 2.0f / level);
        }
    }
}
