package qu.quEnchantments.enchantments.rune;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

import java.util.Map;

public class OmenOfImmunityEnchantment extends CorruptedEnchantment {

    private static final ModConfig.OmenOfImmunityOptions CONFIG = QuEnchantments.getConfig().omenOfImmunityOptions;

    public OmenOfImmunityEnchantment(Properties properties) {
        super(EnchantmentType.RUNE, properties);
    }

//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 5 : 0;
//    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public void tickWhileEquipped(LivingEntity wearer, ItemStack stack, int level) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(ModEnchantments.OMEN_OF_IMMUNITY, wearer, itemStack -> itemStack == stack);
        if (!(wearer instanceof PlayerEntity player && player.getAbilities().creativeMode) && wearer.age % 20 == 0) {
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 6 - level));
            if (stack.getDamage() >= stack.getMaxDamage() && CONFIG.breakOnNoDurability && entry != null) {
                stack.damage(1, wearer, entry.getKey());
            }
        }
        wearer.clearStatusEffects();
        wearer.extinguish();
        wearer.setFrozenTicks(0);
        ((IEntity)wearer).setInaneTicks(0);
    }
}
