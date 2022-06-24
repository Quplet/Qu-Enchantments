package qu.quEnchantments.mixin;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net/minecraft/enchantment/EnchantmentTarget$1", "net/minecraft/enchantment/EnchantmentTarget$9"})
public class EnchantmentTargetMixin {

    @Inject(method = "isAcceptableItem", at = @At(value = "TAIL"), cancellable = true)
    private void addHorseArmor(Item other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof HorseArmorItem) cir.setReturnValue(true);
    }
}
