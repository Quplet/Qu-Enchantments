package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class BashingEnchantment extends Enchantment {
    public BashingEnchantment(Rarity weight, EnchantmentTarget type,EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 5 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (stack.isOf(Items.SHIELD)) {
            return true;
        }
        return false;
    }

    public static void bash(LivingEntity entity, LivingEntity attacker) {
        double d = entity.getX() - attacker.getX();
        double e = entity.getZ() - attacker.getZ();
        while (d * d + e * e < 1.0E-4) {
            d = (Math.random() - Math.random()) * 0.01;
            e = (Math.random() - Math.random()) * 0.01;
        }
        attacker.knockbackVelocity = (float)(MathHelper.atan2(e, d) * 57.2957763671875 - (double)attacker.getYaw());
        attacker.takeKnockback(0.5f, d, e);
    }
}
