package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.enchantments.shield.ReflectionEnchantment;
import qu.quEnchantments.util.interfaces.IPersistentProjectileEntity;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity implements IPersistentProjectileEntity {

    private ItemStack shotFromStack = null;

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFireTicks(I)V"), cancellable = true)
    private void quEnchantments$reflect(EntityHitResult entityHitResult, CallbackInfo ci) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity)(Object)this;
        if (ReflectionEnchantment.reflect(projectile, entityHitResult)) {
            // cancels to prevent the default velocity modifications
            ci.cancel();
        }
    }

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void quEnchantments$injectOnTargetDamage(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity attacker) {
            QuEnchantmentHelper.onTargetDamaged(attacker, shotFromStack == null ? attacker.getMainHandStack() : shotFromStack, entityHitResult.getEntity());
        }
    }

    public void setShotFromStack(ItemStack stack) {
        this.shotFromStack = stack;
    }

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
