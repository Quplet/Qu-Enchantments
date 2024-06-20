package qu.quEnchantments.util.interfaces;

import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;

public interface IEnchantment {

    void qu_Enchantments$onTargetBlockDamage(ServerWorld world, int level, EnchantmentEffectContext context, EnchantmentEffectTarget target, LivingEntity user, DamageSource damageSource);

    void qu_Enchantments$modifyImmunity(LivingEntity user, int level, MutableFloat mutableFloat);
}
