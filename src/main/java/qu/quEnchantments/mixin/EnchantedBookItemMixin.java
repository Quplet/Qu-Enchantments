package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.util.interfaces.IItemStack;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @Inject(method = "addEnchantment", at = @At("TAIL"))
    private static void quEnchantments$makeStackDirty(ItemStack stack, EnchantmentLevelEntry entry, CallbackInfo ci) {
        ((IItemStack)(Object)stack).setEnchantmentsDirty(true);
    }
}
