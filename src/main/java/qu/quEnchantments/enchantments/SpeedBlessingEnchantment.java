package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.UUID;

public class SpeedBlessingEnchantment extends Enchantment {

    public static EntityAttributeModifier BLESSING_BOOST = new EntityAttributeModifier(UUID.fromString("8d32ac69-5bac-4e72-856f-998074238b0d"), "enchantment speed boost", 0.2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    public SpeedBlessingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }
}
