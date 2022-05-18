package qu.quEnchantments.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getEnchantability", cancellable = true)
    private void getEnchantability(CallbackInfoReturnable<Integer> cir) {
        Item item = (Item) (Object) this;
        if (item instanceof ShieldItem) cir.setReturnValue(1);
    }

    @Inject(at = @At("HEAD"), method = "isEnchantable", cancellable = true)
    private void isEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item item = (Item) (Object) this;
        if (item instanceof ShieldItem) cir.setReturnValue(true);
    }
}
