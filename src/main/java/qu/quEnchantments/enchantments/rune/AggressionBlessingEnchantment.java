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

public class AggressionBlessingEnchantment extends QuEnchantment {

    private static final ModConfig.AggressionBlessingOptions CONFIG = QuEnchantments.getConfig().aggressionBlessingOptions;
    public static final EntityAttributeModifier ATTACK_BOOST = new EntityAttributeModifier(
            UUID.fromString("75924c77-91f8-4db6-b604-0e7ebaf9c429"),
            "enchantment attack boost", CONFIG.attackSpeed,
            EntityAttributeModifier.Operation.ADDITION
    );

    public AggressionBlessingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
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

        EntityAttributeInstance instance = wearer.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);

        if (instance == null || stack.getDamage() >= stack.getMaxDamage() || ((IEntity)wearer).getInaneTicks() > 0) return;

        instance.addTemporaryModifier(ATTACK_BOOST);
    }
}
