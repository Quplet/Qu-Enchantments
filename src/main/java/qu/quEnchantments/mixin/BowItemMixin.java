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

@Mixin(BowItem.class)
public class BowItemMixin {

    @ModifyArgs(method = "onStoppedUsing",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"))
    private void speed(Args args, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.ARROWS_FLIGHT, stack)) > 0) {
            args.set(4, (float)args.get(4) + lvl * 0.5f);
        }
    }
}
