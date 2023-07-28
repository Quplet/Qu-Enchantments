package qu.quEnchantments.enchantments.rune;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

import java.util.UUID;

public class SpeedBlessingEnchantment extends QuEnchantment {

    private static final ModConfig.SpeedBlessingOptions CONFIG = QuEnchantments.getConfig().speedBlessingOptions;
    public static final EntityAttributeModifier SPEED_BOOST = new EntityAttributeModifier(UUID.fromString("8d32ac69-5bac-4e72-856f-998074238b0d"), "enchantment speed boost", 0.1 * CONFIG.speedBoost, EntityAttributeModifier.Operation.ADDITION);

    public SpeedBlessingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 5;
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
        if (!(wearer instanceof PlayerEntity player && player.getAbilities().creativeMode) && wearer.age % 20 == 0)
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 1));

        if (wearer.getWorld().isClient) return;

        EntityAttributeInstance instance = wearer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if (instance == null || stack.getDamage() >= stack.getMaxDamage() || ((IEntity)wearer).getInaneTicks() > 0) return;

        instance.addTemporaryModifier(SPEED_BOOST);

    }
}
