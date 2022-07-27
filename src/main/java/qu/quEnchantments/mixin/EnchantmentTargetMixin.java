package qu.quEnchantments.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net/minecraft/enchantment/EnchantmentTarget$1", "net/minecraft/enchantment/EnchantmentTarget$9"})
public class EnchantmentTargetMixin {

    @ModifyReturnValue(method = "isAcceptableItem", at = @At(value = "RETURN"))
    private boolean quEnchantments$horseArmorCondition(boolean original, Item item) {
        return item instanceof HorseArmorItem || original;
    }
}
