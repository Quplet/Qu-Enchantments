package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.interfaces.IPersistentProjectileEntity;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @ModifyArgs(method = "use", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/CrossbowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FF)V"))
    private void quEnchantments$setDivergence(Args args, World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.ACCURACY, stack)) > 0) {
            // 5th arg is the divergence variable
            args.set(5, Math.max(0.0f, (1.0f - (0.5f * lvl))));
        }
    }

    @Inject(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void quEnchantments$setShotFromStack(
            World world,
            LivingEntity
                    shooter,
            Hand hand,
            ItemStack crossbow,
            ItemStack projectile,
            float soundPitch,
            boolean creative,
            float speed,
            float divergence,
            float simulated,
            CallbackInfo ci,
            boolean bl,
            ProjectileEntity projectileEntity
    ) {
        if (projectileEntity instanceof PersistentProjectileEntity) {
            ((IPersistentProjectileEntity)projectileEntity).setShotFromStack(crossbow);
        }
    }
}
