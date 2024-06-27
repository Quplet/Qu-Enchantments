package qu.quEnchantments.enchantments.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import qu.quEnchantments.util.interfaces.IEntity;

public record AgitationCurseEffect(float radius) implements EnchantmentEntityEffect {

    public static final MapCodec<AgitationCurseEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.FLOAT.fieldOf("radius").forGetter(AgitationCurseEffect::radius))
            .apply(instance, AgitationCurseEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user.age % 20 != 0 || !(user instanceof LivingEntity livingEntity) || IEntity.isCreativePlayer(livingEntity)) return;

        world.getEntitiesByType(
                TypeFilter.instanceOf(MobEntity.class),
                user.getBoundingBox().expand(radius),
                entity -> entity.isAlive() && !entity.isTeammate(user) && entity != user
        ).forEach(mobEntity -> mobEntity.setTarget(livingEntity));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
