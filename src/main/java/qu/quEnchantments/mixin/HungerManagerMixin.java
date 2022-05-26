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
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.items.RuneItem;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    @Shadow
    int foodTickTimer;

    @Inject(at = @At(value = "TAIL"), method = "update")
    private void onUpdate(PlayerEntity player, CallbackInfo ci) {
        if (player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && player.canFoodHeal()) {
            ItemStack stack;
            if (((stack = player.getMainHandStack()).getDamage() < stack.getMaxDamage() && stack.isOf(ModItems.RUNE) && EnchantmentHelper.getLevel(ModEnchantments.REGENERATION_BLESSING, stack) > 0)
                    || ((stack = player.getOffHandStack()).getDamage() < stack.getMaxDamage() && stack.isOf(ModItems.RUNE) && EnchantmentHelper.getLevel(ModEnchantments.REGENERATION_BLESSING, stack) > 0)) {
                if (foodTickTimer == 5 && ((HungerManager) (Object) this).getSaturationLevel() > 0.0f && ((HungerManager) (Object) this).getFoodLevel() >= 20) {
                    float f = Math.min(((HungerManager) (Object) this).getSaturationLevel(), 6.0f);
                    player.heal(f / 6.0f);
                } else if (foodTickTimer == 40 && ((HungerManager) (Object) this).getFoodLevel() >= 18) {
                    player.heal(1.0f);
                }
            }
        }
    }
}
