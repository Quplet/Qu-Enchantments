package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
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

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DepthStriderEnchantment) && super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        if (entity.world.isClient || !entity.isOnGround()) return;
        BlockState blockState = ModBlocks.CLOUD.getDefaultState();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos blockPos2 = new BlockPos(entity.getX(), entity.getY() - 0.875, entity.getZ());
        if (!entity.world.getBlockState(blockPos2).equals(Blocks.AIR.getDefaultState()) || !blockPos2.isWithinDistance(entity.getPos(), 1))
            return;
        mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
        BlockState blockState2 = entity.world.getBlockState(mutable);
        if (!blockState2.isAir() || !blockState.canPlaceAt(entity.world, blockPos2) || !entity.world.canPlace(blockState, blockPos2, ShapeContext.absent()))
            return;
        entity.world.setBlockState(blockPos2, blockState);
        int bl = entity.world.getDimension().ultrawarm() ? 1 : 2;
        entity.world.createAndScheduleBlockTick(blockPos2, ModBlocks.CLOUD, MathHelper.nextInt(entity.getRandom(), 25 * bl * level, 50 * bl * level));
        entity.world.syncWorldEvent(ModWorldEvents.CLOUD_BLOCK_CREATION, blockPos2, 0);
    }
}
