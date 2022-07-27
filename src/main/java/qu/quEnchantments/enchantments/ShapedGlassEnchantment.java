package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class ShapedGlassEnchantment extends CorruptedEnchantment {

    protected ShapedGlassEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return level * 2;
    }

    @Override
    public int getMaxLevel() {
        return 5;
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
        if (stack.getItem() instanceof AxeItem) {
            return true;
        }
        return super.isAcceptableItem(stack);
    }
}
