package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ShapedGlassEnchantment extends CorruptedEnchantment {

    private static final ModConfig.ShapedGlassOptions CONFIG = QuEnchantments.getConfig().shapedGlassOptions;

    public ShapedGlassEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level * CONFIG.damageMultiplier;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 5 : 0;
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
    public int getMinPower(int level) {
        return 10 + 20 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.isAcceptableItem(stack);
    }
}
