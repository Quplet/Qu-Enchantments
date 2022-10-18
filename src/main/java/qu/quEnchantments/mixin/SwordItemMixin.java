package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @ModifyArgs(method = "postHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private void quEnchantments$setItemPostHitDamageForShapedGlass(Args args, ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int i;
        if ((i = EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, stack)) > 0) {
            args.set(0, (int)((20 + (i - 1) * 2) * QuEnchantments.getConfig().shapedGlassOptions.itemDamage));
        }
    }
}
