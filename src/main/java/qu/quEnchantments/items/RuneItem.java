package qu.quEnchantments.items;

import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.util.interfaces.IItemStack;

import java.util.Map;

public class RuneItem extends Item {

    private static final ModConfig.RuneOptions CONFIG = QuEnchantments.getConfig().runeOptions;

    public RuneItem(Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasEnchantments() ||
                entity.age % 20 != 0 ||
                ((IItemStack)(Object)stack).qu_Enchantments$corruptedLevel() > 0 ||
                ((selected || (entity instanceof LivingEntity livingEntity && livingEntity.getOffHandStack() == stack)) && !(entity instanceof PlayerEntity player && player.getAbilities().creativeMode))) return;

        stack.setDamage(Math.max(0, stack.getDamage() - 1));
        if (CONFIG.breakOnNoDurability && entity instanceof LivingEntity livingEntity && stack.getDamage() >= stack.getMaxDamage()) {
            Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(ModEnchantments.BASHING, livingEntity);
            if (entry != null) {
                stack.damage(1, livingEntity, entry.getKey());
            }
        }
    }
}
