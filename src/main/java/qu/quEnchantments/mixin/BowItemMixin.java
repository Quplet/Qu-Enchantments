package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.config.ModConfig;

@Mixin(BowItem.class)
public class BowItemMixin {

    @ModifyArgs(method = "onStoppedUsing",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/BowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Ljava/util/List;FFZLnet/minecraft/entity/LivingEntity;)V"))
    private void quEnchantments$setSpeed(Args args, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.ARROWS_FLIGHT, stack)) > 0) {
            // the 6th arg is the speed variable
            args.set(6, (float)args.get(6) + lvl * 0.5f * ModConfig.CONFIG_HANDLER.instance().arrowsFlightArrowSpeed);
        }
    }
}
