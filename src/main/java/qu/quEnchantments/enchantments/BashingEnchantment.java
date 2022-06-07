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
        return 15;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    public static void bash(LivingEntity entity, LivingEntity attacker) {
        if (!entity.world.isClient) {
            double d = entity.getX() - attacker.getX();
            double e = entity.getZ() - attacker.getZ();
            while (d * d + e * e < 1.0E-4) {
                d = (Math.random() - Math.random()) * 0.01;
                e = (Math.random() - Math.random()) * 0.01;
            }
            attacker.knockbackVelocity = (float) (MathHelper.atan2(e, d) * 57.2957763671875 - (double) attacker.getYaw());
            attacker.takeKnockback(0.6f, d, e);
        }
    }
}
