package qu.quEnchantments.enchantments.rune;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.mixin.HungerManagerAccessor;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

public class RegenerationBlessingEnchantment extends QuEnchantment {

    private static final ModConfig.RegenerationBlessingOptions CONFIG = QuEnchantments.getConfig().regenerationBlessingOptions;
    public RegenerationBlessingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 1 : 0;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public void tickWhileEquipped(LivingEntity wearer, ItemStack stack, int level) {
        if (!(wearer instanceof PlayerEntity player && player.getAbilities().creativeMode) && wearer.age % 20 == 0) stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 1));
        if (wearer instanceof PlayerEntity player && player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION) && player.canFoodHeal() && stack.getDamage() < stack.getMaxDamage() && ((IEntity)player).getInaneTicks() <= 0) {
            int foodTickTimer = ((HungerManagerAccessor) player.getHungerManager()).getFoodTickTimer();
            if (foodTickTimer == 5 && player.getHungerManager().getSaturationLevel() > 0.0f && player.getHungerManager().getFoodLevel() >= 20) {
                float f = Math.min(player.getHungerManager().getSaturationLevel(), 6.0f);
                if (player.getHealth() >= 19.0f) {
                    player.addExhaustion(f);
                }
                player.heal(f / 6.0f);
            } else if (foodTickTimer == 40 && player.getHungerManager().getFoodLevel() >= 18) {
                player.heal(1.0f);
            }
        }
    }
}
