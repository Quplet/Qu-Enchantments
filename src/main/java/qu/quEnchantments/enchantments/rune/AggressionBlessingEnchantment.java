package qu.quEnchantments.enchantments.rune;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

import java.util.UUID;

public class AggressionBlessingEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public static final EntityAttributeModifier ATTACK_BOOST = new EntityAttributeModifier(
            UUID.fromString("75924c77-91f8-4db6-b604-0e7ebaf9c429"),
            "enchantment attack boost", CONFIG.aggressionBlessingAttackSpeed,
            EntityAttributeModifier.Operation.ADD_VALUE
    );

    public AggressionBlessingEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.agitationCurseRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.agitationCurseBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.aggressionBlessingEnchantingTable;
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
