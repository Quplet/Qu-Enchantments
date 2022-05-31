package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.ModEnchantments;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @Shadow private @Nullable Entity owner;
    @Shadow private boolean leftOwner;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V", shift = At.Shift.AFTER), method = "onCollision")
    protected void onHitEntity(HitResult hitResult, CallbackInfo ci) {
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;
        if (projectile instanceof PersistentProjectileEntity persistentProjectile) {
            EntityHitResult result = (EntityHitResult) hitResult;
            Entity entity = result.getEntity();
            if (entity != null && !entity.getWorld().isClient() && entity instanceof PlayerEntity player) {
                DamageSource damageSource;
                if (persistentProjectile instanceof TridentEntity) {
                    damageSource = DamageSource.trident(projectile, owner == null ? projectile : owner);
                } else {
                    damageSource = DamageSource.arrow(persistentProjectile, owner == null ? projectile : owner);
                }
                int i;
                if (player.blockedByShield(damageSource) && (i = EnchantmentHelper.getLevel(ModEnchantments.REFLECTION, player.getActiveItem())) > 0) {
                    persistentProjectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, (float) persistentProjectile.getVelocity().normalize().length(), 25.0f / i);
                    leftOwner = true;
                    if (persistentProjectile instanceof TridentEntity) {
                        ((TridentEntityAccessor) projectile).setDealtDamage(false);
                    }
                }
            }
        }
    }
}
