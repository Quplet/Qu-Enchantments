package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import qu.quEnchantments.util.interfaces.IEntity;

public record DamageAndTeleportWhenWetEnchantmentEffect(float damage, int teleportAttempts, EnchantmentLevelBasedValue range) implements EnchantmentEntityEffect {

    public static final MapCodec<DamageAndTeleportWhenWetEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    Codec.FLOAT.fieldOf("damage").forGetter(DamageAndTeleportWhenWetEnchantmentEffect::damage),
                    Codec.INT.optionalFieldOf("teleportAttempts", 5).forGetter(DamageAndTeleportWhenWetEnchantmentEffect::teleportAttempts),
                    EnchantmentLevelBasedValue.CODEC.fieldOf("range").forGetter(DamageAndTeleportWhenWetEnchantmentEffect::range)
            ).apply(instance, DamageAndTeleportWhenWetEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!user.isWet()) return;

        user.damage(user.getDamageSources().drown(), damage);

        if (!(user instanceof LivingEntity livingEntity)) return;

        Random random = user.getRandom();

        for (int i = 0; i < teleportAttempts; i++) {
            float leveledRange = range.getValue(level);
            double x = user.getX() + random.nextDouble() * 2 * leveledRange - leveledRange;
            double y = user.getY() + random.nextDouble() * 2 * leveledRange - leveledRange;
            double z = user.getZ() + random.nextDouble() * 2 * leveledRange - leveledRange;

            if (IEntity.teleportTo(livingEntity, x, y, z, MathHelper.floor(leveledRange), true)) break;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
