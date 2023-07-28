package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.ModEnchantments;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity {

    @Shadow public abstract void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence);

    @Shadow private @Nullable Entity owner;

    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V"), cancellable = true)
    private void quEnchantments$injectOnBlock(HitResult hitResult, CallbackInfo ci) {
        EntityHitResult result = (EntityHitResult) hitResult;
        int reflectionLevel;
        if (result.getEntity() instanceof LivingEntity livingEntity &&
                livingEntity.blockedByShield(livingEntity.getWorld().getDamageSources().thrown(this, this.owner)) &&
                (reflectionLevel = EnchantmentHelper.getEquipmentLevel(ModEnchantments.REFLECTION, livingEntity)) > 0) {
            this.setVelocity(livingEntity, livingEntity.getPitch() - 1.0f, livingEntity.getYaw(), 0.0f, (float) this.getVelocity().length(), 25.0f / reflectionLevel);
            ci.cancel();
        }
    }

    // Ignore
    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
}
//DamageSource.thrownProjectile(this, this.getOwner())
