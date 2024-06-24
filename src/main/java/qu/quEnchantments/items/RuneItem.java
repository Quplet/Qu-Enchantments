package qu.quEnchantments.items;

import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.util.interfaces.IEntity;

public class RuneItem extends Item implements Equipment {

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
        if (!stack.hasEnchantments() || entity.age % 20 != 0) return;

        int numEnchantments = EnchantmentHelper.getEnchantments(stack).getSize();
        int corruptedlevel;
        boolean beingUsed = isBeingUsed(stack, entity);
        if ((corruptedlevel = QuEnchantmentHelper.getCorruptedLevel(stack)) > 0) {
            if (beingUsed && !IEntity.isCreativePlayer(entity)) {
                stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + 6 - Math.min(5, corruptedlevel)));
            }
        } else {
            if (beingUsed && !IEntity.isCreativePlayer(entity)) {
                stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + numEnchantments));
            } else {
                stack.setDamage(Math.max(0, stack.getDamage() - 1));
            }
        }
    }

    private boolean isBeingUsed(ItemStack stack, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return false;

        return livingEntity.getOffHandStack() == stack || livingEntity.getMainHandStack() == stack;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.OFFHAND;
    }
}
