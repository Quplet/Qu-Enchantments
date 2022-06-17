package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.RuneItem;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    @Shadow
    private int foodTickTimer;

    @Inject(at = @At(value = "TAIL"), method = "update")
    private void onUpdate(PlayerEntity player, CallbackInfo ci) {
        HungerManager manager = (HungerManager) (Object) this;
        if (player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && player.canFoodHeal()) {
            if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.REGENERATION_BLESSING, player) > 0) {
                if (foodTickTimer == 5 && manager.getSaturationLevel() > 0.0f && manager.getFoodLevel() >= 20) {
                    float f = Math.min(manager.getSaturationLevel(), 6.0f);
                    if (player.getHealth() >= 19.0f) {
                        player.addExhaustion(f);
                    }
                    player.heal(f / 6.0f);
                } else if (foodTickTimer == 40 && manager.getFoodLevel() >= 18) {
                    player.heal(1.0f);
                }
            }
        }
    }
}
