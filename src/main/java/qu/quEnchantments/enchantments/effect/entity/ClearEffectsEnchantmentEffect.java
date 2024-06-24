package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import qu.quEnchantments.util.interfaces.IEntity;

import java.util.Optional;

public record ClearEffectsEnchantmentEffect(Optional<Boolean> bs) implements EnchantmentEntityEffect {

    public static MapCodec<ClearEffectsEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("bs").forGetter(ClearEffectsEnchantmentEffect::bs)).apply(instance, ClearEffectsEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        user.extinguish();
        user.setFrozenTicks(0);
        ((IEntity)user).quEnchantments$setInaneTicks(0);

        if (!(user instanceof LivingEntity livingEntity)) return;

        livingEntity.clearStatusEffects();

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return null;
    }
}
