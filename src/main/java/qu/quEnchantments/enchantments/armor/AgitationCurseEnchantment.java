package qu.quEnchantments.enchantments.armor;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

import java.util.List;

public class AgitationCurseEnchantment extends QuEnchantment {

    private static final ModConfig.AgitationCurseOptions CONFIG = QuEnchantments.getConfig().agitationCurseOptions;

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
        return CONFIG.isEnabled ? 1 : 0;
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
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return false;
    }

    @Override
    public void tickWhileEquipped(LivingEntity livingEntity, ItemStack stack, int level) {
        World world;
        if ((world = livingEntity.getWorld()).isClient ||
                (livingEntity instanceof PlayerEntity player && player.getAbilities().creativeMode) ||
                livingEntity.age % 20 != 10) return;

        List<Entity> mobs = world.getOtherEntities(
                livingEntity,
                livingEntity.getBoundingBox().expand(CONFIG.radius),
                entity -> entity.isAlive() && !entity.isTeammate(livingEntity) && entity instanceof MobEntity
        );

        mobs.forEach(entity -> ((MobEntity)entity).setTarget(livingEntity));
    }
}
