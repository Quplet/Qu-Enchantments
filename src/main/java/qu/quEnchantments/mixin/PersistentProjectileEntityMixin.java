package qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.enchantments.shield.ReflectionEnchantment;
import qu.quEnchantments.util.IPersistentProjectileEntity;

@Debug(export = true)
@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin implements IPersistentProjectileEntity {

    private ItemStack shotFromStack = null;

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFireTicks(I)V"), cancellable = true)
    private void quEnchantments$reflect(EntityHitResult entityHitResult, CallbackInfo ci) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity)(Object)this;
        if (ReflectionEnchantment.reflect(projectile, entityHitResult)) {
            // cancels to prevent the default velocity modifications
            ci.cancel();
        }
    }

    // Suppressing attacker possibly being null. Where this is injected it never will be.
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void quEnchantments$injectOnTargetDamage(EntityHitResult entityHitResult, CallbackInfo ci) {
        LivingEntity attacker = (LivingEntity)((PersistentProjectileEntity)(Object)(this)).getOwner();
        QuEnchantmentHelper.onTargetDamaged(attacker, shotFromStack == null ? attacker.getMainHandStack() : shotFromStack, entityHitResult.getEntity());
    }

    public void setShotFromStack(ItemStack stack) {
        this.shotFromStack = stack;
    }
}
