package qu.quEnchantments.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.ReflectionEnchantment;

@Mixin(TridentEntity.class)
public class TridentEntityMixin {

    @Shadow
    private boolean dealtDamage;

    @Inject(method = "onEntityHit", at  = @At(value = "INVOKE",
                target = "Lnet/minecraft/entity/projectile/TridentEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
                shift = At.Shift.BEFORE), cancellable = true)
    private void reflect(EntityHitResult entityHitResult, CallbackInfo ci) {
        TridentEntity projectile = (TridentEntity)(Object)this;
        if (ReflectionEnchantment.reflect(projectile, entityHitResult)) {
            dealtDamage = false;
            ci.cancel();
        }
    }
}
