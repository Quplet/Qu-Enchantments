package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.NightbloodEnchantment;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void quEnchantments$triggerOnAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        LivingEntityEvents.ON_ATTACK_EVENT.invoker().onAttack(target, player);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSprinting()Z", ordinal = 0), ordinal = 0)
    private float quEnchantments$modifyDamageVariableForNightblood(float value, Entity target) {
        if (EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, this.getMainHandStack()) > 0) {
            value += NightbloodEnchantment.calculateDamage(target, this);
        }
        return value;
    }
}
