package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record BashingEnchantmentEffect(EnchantmentLevelBasedValue knockback) implements EnchantmentEntityEffect {

    public static final MapCodec<BashingEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(EnchantmentLevelBasedValue.CODEC.fieldOf("knockback").forGetter(effect -> effect.knockback))
                    .apply(instance, BashingEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        LivingEntity defender = context.owner();
        if (defender == null || !defender.isBlocking() || !(user instanceof LivingEntity attacker)) return;

        double dx = defender.getX() - attacker.getX();
        double dz = defender.getZ() - attacker.getZ();
        while (dx * dx + dz * dz < 1.0E-4) {
            dx = (Math.random() - Math.random()) * 0.01;
            dz = (Math.random() - Math.random()) * 0.01;
        }

        attacker.takeKnockback(knockback.getValue(level), dx, dz);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
