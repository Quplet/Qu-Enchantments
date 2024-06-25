package qu.quEnchantments.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Shadow
    private int foodTickTimer;

    @Inject(method = "update", at = @At("HEAD"))
    private void modifyFoodTimer(PlayerEntity player, CallbackInfo ci) {
        if (QuEnchantmentHelper.hasRegenerationBlessing(player)) foodTickTimer++;
    }

    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V"))
    private void modifyExhaustion(Args args, PlayerEntity player) {
        if (QuEnchantmentHelper.hasRegenerationBlessing(player)) args.set(0, ((float)args.get(0))/2);
    }
}
