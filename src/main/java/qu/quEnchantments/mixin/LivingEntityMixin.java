package qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import qu.quEnchantments.callbacks.ApplyMovementEffectsCallback;
import qu.quEnchantments.callbacks.LivingEntityTickCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("TAIL"), method = "applyMovementEffects")
    private void onApplyMovementEffects(BlockPos pos, CallbackInfo info) {
        ApplyMovementEffectsCallback.EVENT.invoker().interact((LivingEntity) (Object) this, pos);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        LivingEntityTickCallback.EVENT.invoker().interact((LivingEntity) (Object) this);
    }
}
