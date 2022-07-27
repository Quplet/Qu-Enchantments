package qu.quEnchantments.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.util.IEntity;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getEquipmentLevel", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$setZeroIfInaneEquipment(Enchantment enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (((IEntity)entity).getInaneTicks() > 0) cir.setReturnValue(0);
    }

    @Inject(method = "getLevel", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$setZeroIfInaneStack(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        Entity entity = stack.getHolder();
        if (entity != null && ((IEntity)entity).getInaneTicks() > 0) cir.setReturnValue(0);
    }

    @Inject(method = "getProtectionAmount", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$setZeroIfInaneProtection(Iterable<ItemStack> equipment, DamageSource source, CallbackInfoReturnable<Integer> cir) {
        Entity entity = equipment.iterator().next().getHolder();
        if (entity != null && ((IEntity)entity).getInaneTicks() > 0) cir.setReturnValue(0);
    }

    @Inject(method = "getAttackDamage", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$setZeroIfInaneAttackDamage(ItemStack stack, EntityGroup group, CallbackInfoReturnable<Float> cir) {
        Entity entity = stack.getHolder();
        if (entity != null && ((IEntity)entity).getInaneTicks() > 0) cir.setReturnValue(0.0f);
    }

    @Inject(method = "onUserDamaged", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$stopIfInaneUserDamaged(LivingEntity user, Entity attacker, CallbackInfo ci) {
        if (((IEntity)user).getInaneTicks() > 0) ci.cancel();

    }

    @Inject(method = "onTargetDamaged", at = @At("HEAD"), cancellable = true)
    private static void quEnchantments$stopIfInaneTargetDamaged(LivingEntity user, Entity target, CallbackInfo ci) {
        if (((IEntity)user).getInaneTicks() > 0) ci.cancel();
    }
}
