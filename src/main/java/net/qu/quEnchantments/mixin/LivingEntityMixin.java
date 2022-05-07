package net.qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.qu.quEnchantments.ApplyMovementEffectsCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("TAIL"), method = "applyMovementEffects", cancellable = true)
    private void onApplyMovementEffects(BlockPos pos, CallbackInfo info) {
        ActionResult result = ApplyMovementEffectsCallback.EVENT.invoker().interact((LivingEntity) (Object) this, pos);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
