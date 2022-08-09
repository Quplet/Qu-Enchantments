package qu.quEnchantments.enchantments.armor;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import qu.quEnchantments.enchantments.QuEnchantment;

import java.util.List;

public class AgitationCurseEnchantment extends QuEnchantment {
    public AgitationCurseEnchantment(Rarity weight, EquipmentSlot ... slotTypes) {
        super(weight, EnchantmentTarget.WEARABLE, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 25;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public void tickWhileEquipped(LivingEntity livingEntity, ItemStack stack, int level) {
        if (livingEntity.world.isClient || (livingEntity instanceof PlayerEntity player && player.getAbilities().creativeMode) || livingEntity.age % 20 != 10) return;
        List<Entity> mobs = livingEntity.world.getOtherEntities(livingEntity, livingEntity.getBoundingBox().expand(16), entity -> entity.isAlive() && !entity.isTeammate(livingEntity) && entity instanceof MobEntity mob && mob.getTarget() == null);
        for (Entity mob : mobs) {
            ((MobEntity)mob).setTarget(livingEntity);
        }
    }
}
