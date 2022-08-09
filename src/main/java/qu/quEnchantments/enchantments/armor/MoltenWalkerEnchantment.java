package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.world.ModWorldEvents;

public class MoltenWalkerEnchantment extends QuEnchantment {
    public MoltenWalkerEnchantment(Rarity weight, EquipmentSlot ... slotTypes) {
        super(weight, EnchantmentTarget.ARMOR_FEET, slotTypes);
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
        if (other instanceof FrostWalkerEnchantment || other instanceof DepthStriderEnchantment)
            return false;
        return super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        if (entity.world.isClient || !entity.isOnGround()) return;
        BlockState blockState = ModBlocks.HOT_OBSIDIAN.getDefaultState();
        float f = Math.min(16, 1 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(pos.add(-f, -1.0, -f), pos.add(f, -1.0, f))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), f)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = entity.world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = entity.world.getBlockState(blockPos2)).getMaterial() != Material.LAVA || blockState3.get(FluidBlock.LEVEL) != 0 || !blockState.canPlaceAt(entity.world, blockPos2) || !entity.world.canPlace(blockState, blockPos2, ShapeContext.absent()))
                continue;
            entity.world.setBlockState(blockPos2, blockState);
            entity.world.createAndScheduleBlockTick(blockPos2, ModBlocks.HOT_OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
            entity.world.syncWorldEvent(ModWorldEvents.HOT_OBSIDIAN_CREATION, blockPos2, 0);
        }
    }
}
