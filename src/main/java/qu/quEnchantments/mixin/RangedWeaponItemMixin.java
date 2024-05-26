package qu.quEnchantments.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import qu.quEnchantments.util.interfaces.IPersistentProjectileEntity;

import java.util.List;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {

    @Inject(method = "shootAll",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void quEnchantment$setShotFromStack(World world, LivingEntity shooter, Hand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target, CallbackInfo ci, float f, float g, float h, float i, int j, ItemStack itemStack, float k, ProjectileEntity projectileEntity) {
        if (projectileEntity instanceof PersistentProjectileEntity persistentProjectileEntity) {
            ((IPersistentProjectileEntity)persistentProjectileEntity).setShotFromStack(stack);
        }
    }
}
