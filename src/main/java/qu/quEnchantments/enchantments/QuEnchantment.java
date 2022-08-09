package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public abstract class QuEnchantment extends Enchantment {
    public QuEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public float getAttackDamage(Entity target, ItemStack stack, int level) {
        return 0.0f;
    }

    public void tickOnce(LivingEntity entity, ItemStack stack, int level) {}

    public void tickAlways(LivingEntity entity, ItemStack stack, int level) {}

    public void tickWhileEquipped(LivingEntity entity, ItemStack stack, int level) {}

    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {}

    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {}

    public void onBlock(LivingEntity user, LivingEntity attacker, ItemStack stack, int level) {}

    public void onBlockBreak(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {}
}
