package qu.quEnchantments.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.items.RuneItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void quEnchantments$onTick(CallbackInfo info) {
        LivingEntityEvents.ON_TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);
    }

    // Suppressed (LivingEntity)source.getSource() possibly not being a LivingEntity. Where this is injected it always will be.
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.BEFORE))
    private void quEnchantments$injectOnBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getWorld().isClient) return;

        QuEnchantmentHelper.onTargetBlockDamage((ServerWorld) this.getWorld(), (LivingEntity)(Object)this, source);

        //QuEnchantmentHelper.onBlock((LivingEntity)(Object)this, (LivingEntity)source.getSource());
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getFrozenTicks()I"))
    private void quEnchantments$tickInaneReduction(CallbackInfo ci) {
        this.setInaneTicks(Math.max(0, this.getInaneTicks() - 1));
    }

    @Inject(method = "getPreferredEquipmentSlot", at = @At("TAIL"), cancellable = true)
    private void quEnchantments$runePreferredSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.getItem() instanceof RuneItem) {
            cir.setReturnValue(EquipmentSlot.OFFHAND);
        }
    }

    @ModifyExpressionValue(method = "modifyAppliedDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 2))
    private boolean quEnchantments$setBypassIfInane(boolean original) {
        return original || (this.getInaneTicks() > 0 && !QuEnchantmentHelper.hasOmenImmunity((LivingEntity)(Object)this));
    }

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "tryUseTotem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean quEnchantments$setBypassTotemIfInane(boolean original) {
        return quEnchantments$setBypassIfInane(original);
    }
}
