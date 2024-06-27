package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.util.interfaces.IEntity;

public record NightbloodDrainEnchantmentEffect(float drainRate) implements EnchantmentEntityEffect {

    public static final MapCodec<NightbloodDrainEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.FLOAT.fieldOf("drainRate").forGetter(NightbloodDrainEnchantmentEffect::drainRate))
            .apply(instance, NightbloodDrainEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user instanceof PlayerEntity player) {
            if (IEntity.isCreativePlayer(player)) return;

            if (player.experienceLevel > 0 || player.experienceProgress > 0) {
                player.addExperience(MathHelper.floor(-4 * drainRate / level));
                return;
            }

            HungerManager hm;
            if (world.getDifficulty().getId() != 0 && (hm = player.getHungerManager()).getFoodLevel() > 0) {
                hm.addExhaustion(1.5f * drainRate / level);
                return;
            }
        }

        user.damage(world.getDamageSources().magic(), 2.0f * drainRate / level);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
