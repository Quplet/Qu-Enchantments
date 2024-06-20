package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import qu.quEnchantments.util.config.ModConfig;

public record FreezeEnchantmentEffect(EnchantmentLevelBasedValue duration) implements EnchantmentEntityEffect {

    public static final MapCodec<FreezeEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(EnchantmentLevelBasedValue.CODEC.fieldOf("duration").forGetter(effect -> effect.duration))
                    .apply(instance, FreezeEnchantmentEffect::new)
    );

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        user.extinguish();

        if (user.canFreeze()) {
            user.setFrozenTicks(user.getMinFreezeDamageTicks() + MathHelper.floor(duration.getValue(level)));
        }

        Random random = world.getRandom();
        for (int x = 0; x < 20; ++x) {
            double d = random.nextGaussian() * 0.02;
            double e = random.nextGaussian() * 0.02;
            double f = random.nextGaussian() * 0.02;
            world.spawnParticles(ParticleTypes.SNOWFLAKE, user.getParticleX(1.0), user.getRandomBodyY(), user.getParticleZ(1.0), 1, d, e, f, 0.0);
        }
    }


    public MapCodec<FreezeEnchantmentEffect> getCodec() {
        return CODEC;
    }
}
