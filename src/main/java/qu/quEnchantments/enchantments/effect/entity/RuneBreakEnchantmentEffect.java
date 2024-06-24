package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import qu.quEnchantments.util.interfaces.IEntity;

import java.util.Optional;

public record RuneBreakEnchantmentEffect(Optional<Boolean> bs) implements EnchantmentEntityEffect {

    public static MapCodec<RuneBreakEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(Codec.BOOL.optionalFieldOf("bs").forGetter(RuneBreakEnchantmentEffect::bs))
                    .apply(instance, RuneBreakEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        ItemStack stack = context.stack();
        if (stack.isDamageable() && !IEntity.isCreativePlayer(user) && !user.isSpectator() && user.age % 20 == 0 && stack.getDamage() >= stack.getMaxDamage()) {
            stack.damage(5, world, user instanceof ServerPlayerEntity serverPlayerEntity ? serverPlayerEntity : null, context.onBreak());
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
