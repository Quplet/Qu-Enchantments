package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.enchantments.shield.ReflectionEnchantment;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    @Shadow
    private boolean dealtDamage;

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/TridentEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    private void quEnchantments$reflect(EntityHitResult entityHitResult, CallbackInfo ci) {
        TridentEntity projectile = (TridentEntity)(Object)this;
        if (ReflectionEnchantment.reflect(projectile, entityHitResult)) {
            // sets damage dealt to false so that the reflected projectile can harm something
            dealtDamage = false;
            // cancels to prevent the default velocity modifications
            ci.cancel();
        }
    }

    @ModifyVariable(method = "onEntityHit",
    slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F")),
    at = @At(value = "STORE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"),
    ordinal = 0)
    private float quEnchantments$injectGetAttackDamage(float original, EntityHitResult entityHitResult) {
        return original + QuEnchantmentHelper.getAttackDamage(this.getItemStack(), entityHitResult.getEntity());
    }

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void quEnchantments$injectOnTargetDamaged(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity attacker = this.getOwner();
        if (attacker instanceof LivingEntity) {
            QuEnchantmentHelper.onTargetDamaged((LivingEntity) attacker, this.getItemStack(), entityHitResult.getEntity());
        }
    }

    // Ignore
    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world, ItemStack stack) {
        super(entityType, world, stack);
    }
}
