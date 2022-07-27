package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import qu.quEnchantments.util.ModTags;

import java.util.Optional;

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
     * Will remove 4/level xp from the user until drained, then hunger, then health.
     * @param user The {@link LivingEntity} to drain.
     * @param level The level of the enchantment.
     */
    public static void drain(LivingEntity user, int level) {
        if (user instanceof PlayerEntity player) {
            if (!player.getAbilities().creativeMode) {
                if (player.experienceLevel > 0 || player.experienceProgress > 0) {
                    player.addExperience(-4 / level);
                } else if (user.world.getDifficulty().getId() != 0 && player.getHungerManager().getFoodLevel() > 0) {
                    player.getHungerManager().addExhaustion(1.5f / level);
                } else {
                    player.damage(DamageSource.MAGIC, 2.0f / level);
                }
            }
        } else {
            user.damage(DamageSource.MAGIC, 2.0f / level);
        }
    }

    public static float calculateDamage(Entity target, LivingEntity attacker) {
        if (!target.world.isClient) {
            Random random = target.world.getRandom();
            for (int x = 0; x < 25; ++x) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                ((ServerWorld) target.world).spawnParticles(ParticleTypes.LARGE_SMOKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, d, e, f, 0.0);
            }
            Optional<RegistryKey<EntityType<?>>> key;
            if ((key = Registry.ENTITY_TYPE.getKey(target.getType())).isPresent() && !Registry.ENTITY_TYPE.getOrCreateEntry(key.orElseThrow()).isIn(ModTags.NIGHTBLOOD_IMMUNE_ENTITIES)) {
                if (target instanceof LivingEntity livingEntity) {
                    if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.OMEN_OF_IMMUNITY, livingEntity) > 0) return 0.0f;
                    livingEntity.disableExperienceDropping();
                }
                return 1000000.0f;
            } else if (target instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 1, false, true), attacker);
            }
        }
        return 0.0f;
    }
}
