package qu.quEnchantments.items;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import qu.quEnchantments.util.IItemStack;

public class RuneItem extends Item {

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
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasEnchantments() || entity.age % 20 != 0) return;
        int bl = ((IItemStack)(Object)stack).corruptedLevel();
        if (!selected && !(entity instanceof LivingEntity livingEntity && livingEntity.getOffHandStack() == stack) || (entity instanceof PlayerEntity player && player.getAbilities().creativeMode)) {
            if (bl == 0) {
                stack.setDamage(Math.max(0, stack.getDamage() - 1));
            }
            return;
        }
        if (bl == 0) {
            stack.setDamage(Math.min(stack.getMaxDamage(), stack.getDamage() + stack.getEnchantments().size()));
        } else {
            stack.setDamage(Math.min(stack.getMaxDamage() - 1, stack.getDamage() + 6 - bl));
            if (stack.getDamage() == stack.getMaxDamage() - 1) {
                stack.damage(500, (LivingEntity) entity, e -> e.sendEquipmentBreakStatus(selected ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
            }
        }
    }
}
