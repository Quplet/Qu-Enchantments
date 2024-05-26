package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IEntity;

public class InaneAspectEnchantment extends QuEnchantment {

    private static final ModConfig.InaneAspectOptions CONFIG = QuEnchantments.getConfig().inaneAspectOptions;

    public InaneAspectEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other == Enchantments.FIRE_ASPECT || other instanceof FreezingAspectEnchantment || other instanceof LeechingAspectEnchantment) && super.canAccept(other);
    }


//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 2 : 0;
//    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {
        if (user.getWorld().isClient) return;
        ((IEntity)target).setInaneTicks(40 + CONFIG.duration * level);
    }
}
