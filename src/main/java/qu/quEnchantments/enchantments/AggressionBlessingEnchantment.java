package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.UUID;

public class AggressionBlessingEnchantment extends Enchantment {

    public static EntityAttributeModifier ATTACK_BOOST = new EntityAttributeModifier(UUID.fromString("75924c77-91f8-4db6-b604-0e7ebaf9c429"), "enchantment attack boost", 1.0, EntityAttributeModifier.Operation.ADDITION);

    public AggressionBlessingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
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
