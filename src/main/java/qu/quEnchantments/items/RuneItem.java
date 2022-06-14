package qu.quEnchantments.items;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.ModEnchantments;

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
        if (entity instanceof LivingEntity livingEntity) {
            if (entity.age % 20 == 0 && stack.hasEnchantments()) {
                if ((selected || livingEntity.getOffHandStack() == stack)
                        && !(entity instanceof PlayerEntity player && player.getAbilities().creativeMode)) {
                    if (stack.getDamage() < stack.getMaxDamage()) {
                        if (stack.getOrCreateNbt().contains("Corrupted")) {
                            int i = EnchantmentHelper.getLevel(ModEnchantments.OMEN_OF_IMMUNITY, stack);
                            if (stack.getDamage() + 6 - i < stack.getMaxDamage()) {
                                stack.setDamage(Math.min(stack.getDamage() + 6 - i, stack.getMaxDamage()));
                            } else {
                                stack.damage(6 - i, livingEntity, e -> e.sendEquipmentBreakStatus(selected ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
                            }
                        } else {
                            stack.setDamage(Math.min(stack.getDamage() + stack.getEnchantments().size(), stack.getMaxDamage()));
                        }
                    }
                } else if (stack.getDamage() > 0 && !stack.getOrCreateNbt().contains("Corrupted")) {
                    stack.setDamage(Math.max(0, stack.getDamage() - 1));
                }
            }
        }
    }
}
