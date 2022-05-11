package qu.quEnchantments.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import qu.quEnchantments.callbacks.AnvilEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilMixin {

    @Inject(at = @At("HEAD"), method = "onTakeOutput")
    private void onAnvilUse(PlayerEntity player, ItemStack stack, CallbackInfo info) {
        AnvilEvents.ANVIL_USED.invoker().onUse(player, stack, (AnvilScreenHandler) (Object) this);
    }

    @Inject(at = @At("TAIL"), method = "updateResult", cancellable = true)
    private void onAnvilUpdate(CallbackInfo info) {
        AnvilEvents.ANVIL_UPDATE.invoker().onUpdate((AnvilScreenHandler) (Object) this);
    }
}
