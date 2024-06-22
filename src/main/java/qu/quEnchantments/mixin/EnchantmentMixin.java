package qu.quEnchantments.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.TargetedEnchantmentEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import qu.quEnchantments.component.ModEnchantmentEffectComponentTypes;
import qu.quEnchantments.util.interfaces.IEnchantment;

import java.util.List;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements IEnchantment {

    @Shadow
    public abstract <T> List<T> getEffect(ComponentType<List<T>> type);

    @Override
    public void qu_Enchantments$onTargetBlockDamage(ServerWorld world, int level, EnchantmentEffectContext context, EnchantmentEffectTarget target, LivingEntity user, DamageSource damageSource) {
        for (TargetedEnchantmentEffect<EnchantmentEntityEffect> targetedEnchantmentEffect : getEffect(ModEnchantmentEffectComponentTypes.ON_BLOCK)) {
            if (target != targetedEnchantmentEffect.enchanted()) continue;
            Enchantment.applyTargetedEffect(targetedEnchantmentEffect, world, level, context, user, damageSource);
        }
    }

    @Override
    public void qu_Enchantments$modifyImmunity(LivingEntity user, int level, MutableFloat mutableFloat) {
        ((Enchantment)(Object)this).modifyValue(ModEnchantmentEffectComponentTypes.OMEN_IMMUNITY, user.getRandom(), level, mutableFloat);
    }

    @Override
    public void qu_Enchantments$modifyFidelity(LivingEntity user, int level, MutableFloat mutableFloat) {
        ((Enchantment)(Object)this).modifyValue(ModEnchantmentEffectComponentTypes.FIDELITY, user.getRandom(), level, mutableFloat);
    }


}