package qu.quEnchantments.asm;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import qu.quEnchantments.items.RuneItem;

public class Rune extends RuneTargetEnchantmentMixin {

    @Override
    public boolean isAcceptableItem(Item other) {
        return other instanceof RuneItem;
    }
}

@Mixin(EnchantmentTarget.class)
abstract class RuneTargetEnchantmentMixin {
    @Shadow
    abstract boolean isAcceptableItem(Item other);
}