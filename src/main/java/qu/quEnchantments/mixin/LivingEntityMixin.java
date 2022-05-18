package qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At("TAIL"), method = "applyMovementEffects")
    private void onApplyMovementEffects(BlockPos pos, CallbackInfo info) {
        LivingEntityEvents.ON_MOVEMENT_EFFECTS_EVENT.invoker().onAffect((LivingEntity) (Object) this, pos);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        LivingEntityEvents.ON_TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "blockedByShield")
    private void isBlocked(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            LivingEntityEvents.ON_BLOCK_EVENT.invoker().onBlock(source, (LivingEntity) (Object) this);
        }
    }
}
