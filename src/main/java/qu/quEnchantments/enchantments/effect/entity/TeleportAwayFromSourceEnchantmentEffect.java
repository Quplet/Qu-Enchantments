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

public record TeleportAwayFromSourceEnchantmentEffect(EnchantmentLevelBasedValue range, int teleportAttempts) implements EnchantmentEntityEffect {

    public static MapCodec<TeleportAwayFromSourceEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("range").forGetter(TeleportAwayFromSourceEnchantmentEffect::range),
                    Codec.INT.optionalFieldOf("teleportAttempts", 5).forGetter(TeleportAwayFromSourceEnchantmentEffect::teleportAttempts)
            )
            .apply(instance, TeleportAwayFromSourceEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        LivingEntity attacker;
        if (!(user instanceof LivingEntity livingEntity) || IEntity.isCreativePlayer(livingEntity) || (attacker = livingEntity.getAttacker()) == null) return;

        Vec3d teleportDir = new Vec3d(user.getX(), 0, user.getZ()).subtract(attacker.getX(), 0, attacker.getZ()).normalize();
        Random random = world.getRandom();

        for (int i = 0; i < teleportAttempts; i++) {
            float xVariation = random.nextFloat() * MathHelper.PI - MathHelper.PI * 0.5f;
            float zVariation = random.nextFloat() * MathHelper.PI - MathHelper.PI * 0.5f;
            Vec3d teleportPos = user.getPos().add(teleportDir.rotateX(xVariation).rotateZ(zVariation).multiply(range.getValue(level)));
            double y = user.getY() + random.nextDouble() * range.getValue(level) * 2 - range.getValue(level);

            if (IEntity.teleportTo(livingEntity, teleportPos.x, y, teleportPos.z, MathHelper.floor(range.getValue(level)), true)) break;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
