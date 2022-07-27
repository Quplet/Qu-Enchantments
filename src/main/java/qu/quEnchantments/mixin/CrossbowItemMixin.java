package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.enchantments.ModEnchantments;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @ModifyArgs(method = "use", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/CrossbowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FF)V"))
    private void quEnchantments$setDivergence(Args args, World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.ACCURACY, stack)) > 0) {
            // 5th arg is the divergence variable
            if (lvl == 1) {
                args.set(5, 0.5f);
            } else if (lvl == 2) {
                args.set(5, 0.0f);
            }
        }
    }
}
