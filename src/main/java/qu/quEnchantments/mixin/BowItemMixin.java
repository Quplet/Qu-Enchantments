package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.interfaces.IPersistentProjectileEntity;

@Mixin(BowItem.class)
public class BowItemMixin {

    @ModifyArgs(method = "onStoppedUsing",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"))
    private void quEnchantments$setSpeed(Args args, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.ARROWS_FLIGHT, stack)) > 0) {
            // the 4th arg is the speed variable
            args.set(4, (float)args.get(4) + lvl * 0.5f * QuEnchantments.getConfig().arrowsFlightOptions.arrowSpeed);
        }
    }


    @Inject(method = "onStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void quEnchantment$setShotFromStack(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci, PlayerEntity player, boolean bl, ItemStack stack1, int i, float f, boolean bl2, ArrowItem arrowItem, PersistentProjectileEntity persistentProjectileEntity) {
        ((IPersistentProjectileEntity)persistentProjectileEntity).setShotFromStack(stack);
    }
}
