package qu.quEnchantments.asm;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

public class HorseArmor extends HorseArmorTargetEnchantmentMixin {

    @Override
    public boolean isAcceptableItem(Item other) {
        return other instanceof HorseArmorItem;
    }
}

@Mixin(EnchantmentTarget.class)
abstract class HorseArmorTargetEnchantmentMixin {
    @Shadow
    abstract boolean isAcceptableItem(Item other);
}
