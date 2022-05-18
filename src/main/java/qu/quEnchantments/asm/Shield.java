package qu.quEnchantments.asm;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

public class Shield extends ShieldTargetEnchantmentMixin {

    @Override
    boolean isAcceptableItem(Item other) {
        return other instanceof ShieldItem;
    }
}

@Mixin(EnchantmentTarget.class)
abstract class ShieldTargetEnchantmentMixin {
    @Shadow
    abstract boolean isAcceptableItem(Item other);
}
