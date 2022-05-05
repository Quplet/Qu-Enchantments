package net.qu.quEnchantments.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.ActionResult;
import net.qu.quEnchantments.AnvilUpdateResultCallback;
import net.qu.quEnchantments.AnvilUsedCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilMixin {

    @Inject(at = @At("TAIL"), method = "onTakeOutput", cancellable = true)
    private void onAnvilUse(PlayerEntity player, ItemStack stack, CallbackInfo info) {
        ActionResult result = AnvilUsedCallback.EVENT.invoker().interact(player, stack, (AnvilScreenHandler) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "updateResult", cancellable = true)
    private void onAnvilUpdate(CallbackInfo info) {
        ActionResult result = AnvilUpdateResultCallback.EVENT.invoker().interact((AnvilScreenHandler) (Object) this);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
