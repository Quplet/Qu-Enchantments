package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public record SpawnNightBloodParticlesEnchantmentEffect(NumberRange.IntRange amount) implements EnchantmentEntityEffect {

    public static final MapCodec<SpawnNightBloodParticlesEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(NumberRange.IntRange.CODEC.fieldOf("amount").forGetter(SpawnNightBloodParticlesEnchantmentEffect::amount))
            .apply(instance, SpawnNightBloodParticlesEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        Random random = user.getRandom();
        int top = random.nextBetween(amount.min().orElse(0), amount.max().orElse(255));
        for (int i = 0; i < top; i++) {
            double deltaX = random.nextGaussian() * 0.02;
            double deltaY = random.nextGaussian() * 0.02;
            double deltaZ = random.nextGaussian() * 0.02;
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, user.getParticleX(1.0), user.getRandomBodyY(), user.getParticleZ(1.0), 1, deltaX, deltaY, deltaZ, 0.0);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
