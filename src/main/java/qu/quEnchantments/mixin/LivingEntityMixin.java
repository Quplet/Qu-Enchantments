package qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    private void quEnchantments$onTick(CallbackInfo info) {
        LivingEntityEvents.ON_TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getFrozenTicks()I"))
    private void quEnchantments$tickInaneReduction(CallbackInfo ci) {
        this.quEnchantments$setInaneTicks(Math.max(0, this.quEnchantments$getInaneTicks() - 1));
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V"))
    private void quEnchantments$injectOnBlockDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        World world;
        if ((world = this.getWorld()).isClient) return;
        QuEnchantmentHelper.onTargetBlockDamage((ServerWorld) world, (LivingEntity)(Object)this, source);
    }
}
