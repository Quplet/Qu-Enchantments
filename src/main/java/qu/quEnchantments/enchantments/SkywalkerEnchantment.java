package qu.quEnchantments.enchantments;

import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.world.ModWorldEvents;

public class SkywalkerEnchantment extends CorruptedEnchantment {
    public SkywalkerEnchantment(EnchantmentType enchantmentType, Rarity weight, EquipmentSlot... slotTypes) {
        super(enchantmentType, weight, EnchantmentTarget.ARMOR_FEET, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static void condenseCloud(LivingEntity entity, World world, int level) {
        if (!entity.world.isClient) {
            if (!entity.isOnGround()) {
                return;
            }
            BlockState blockState = ModBlocks.CLOUD.getDefaultState();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            BlockPos blockPos2 = new BlockPos(entity.getX(), entity.getY() - 0.875, entity.getZ());

            if (!world.getBlockState(blockPos2).equals(Blocks.AIR.getDefaultState()) || !blockPos2.isWithinDistance(entity.getPos(), 1))
                return;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent()))
                return;
            world.setBlockState(blockPos2, blockState);
            world.createAndScheduleBlockTick(blockPos2, ModBlocks.CLOUD, MathHelper.nextInt(entity.getRandom(), 50 * level, 100 * level));
            world.syncWorldEvent(ModWorldEvents.CLOUD_BLOCK_CREATION, blockPos2, 0);
        }
    }
}
