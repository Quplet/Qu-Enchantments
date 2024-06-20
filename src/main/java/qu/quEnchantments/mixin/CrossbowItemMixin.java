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
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @ModifyArgs(method = "use", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/CrossbowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FFLnet/minecraft/entity/LivingEntity;)V"))
    private void quEnchantments$setDivergence(Args args, World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int lvl;
        if ((lvl = QuEnchantmentHelper.getAccuracyLevel(stack, user)) > 0) {
            // 6th arg is the divergence variable
            args.set(6, Math.max(0.0f, ((float)args.get(6) - (0.5f * lvl))));
        }
    }
}
