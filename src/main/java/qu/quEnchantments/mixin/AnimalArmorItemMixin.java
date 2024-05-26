package qu.quEnchantments.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.AnimalArmorItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnimalArmorItem.class)
public class AnimalArmorItemMixin {

    @Shadow @Final private AnimalArmorItem.Type type;

    @ModifyReturnValue(method = "isEnchantable", at = @At("RETURN"))
    public boolean qu_Enchantments$modifyIsEnchantable(boolean original) {
        if (type == AnimalArmorItem.Type.EQUESTRIAN) return true;
        return original;
    }
}
