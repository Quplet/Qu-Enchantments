package qu.quEnchantments.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At(value = "INVOKE",
                target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z"))
    private void quEnchantments$injectOnBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        QuEnchantmentHelper.onBlockBroken(this.player, pos);
    }
}
