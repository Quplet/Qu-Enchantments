package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import qu.quEnchantments.util.IEntity;

public class InaneAspectEnchantment extends FireAspectEnchantment {
    public InaneAspectEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, slotTypes);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof FireAspectEnchantment) && super.canAccept(other);
    }

    public static int getMinInaneTicks(int level) {
        return 80 + 40 * (level - 1);
    }
}
