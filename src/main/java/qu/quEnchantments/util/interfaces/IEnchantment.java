package qu.quEnchantments.util.interfaces;

import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface IEnchantment {

    void quEnchantments$onTargetBlockDamage(ServerWorld world, int level, EnchantmentEffectContext context, EnchantmentEffectTarget target, LivingEntity user, DamageSource damageSource);

    void quEnchantments$modifyImmunity(LivingEntity user, int level, MutableFloat mutableFloat);

    void quEnchantments$modifyFidelity(LivingEntity user, int level, MutableFloat mutableFloat);
}
