package qu.quEnchantments.mixin;

import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public class ShieldItemMixin extends ItemMixin {

    @Override
    public int getEnchantability() {
        return 1;
    }
}
