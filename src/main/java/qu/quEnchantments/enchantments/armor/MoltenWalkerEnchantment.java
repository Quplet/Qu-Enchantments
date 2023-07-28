package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
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
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.world.ModWorldEvents;

public class MoltenWalkerEnchantment extends QuEnchantment {

    private static final ModConfig.MoltenWalkerOptions CONFIG = QuEnchantments.getConfig().moltenWalkerOptions;

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
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 2 : 0;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (other instanceof FrostWalkerEnchantment || other instanceof DepthStriderEnchantment)
            return false;
        return super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        World world = entity.getWorld();
        if (world.isClient || !entity.isOnGround()) return;

        BlockState hotObsidianDefaultState = ModBlocks.HOT_OBSIDIAN.getDefaultState();
        int radius = Math.min(16, CONFIG.radius + level - 1);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPosItr : BlockPos.iterate(pos.add(-radius, -1, -radius), pos.add(radius, -1, radius))) {
            if (!blockPosItr.isWithinDistance(entity.getPos(), radius)) continue;

            mutable.set(blockPosItr.getX(), blockPosItr.getY() + 1, blockPosItr.getZ());
            BlockState itrBlockState = world.getBlockState(blockPosItr);

            if (!world.getBlockState(blockPosItr.up()).isAir() || !itrBlockState.isOf(Blocks.LAVA) ||
                    itrBlockState.get(FluidBlock.LEVEL) != 0 || !hotObsidianDefaultState.canPlaceAt(world, blockPosItr) ||
                    !world.canPlace(hotObsidianDefaultState, blockPosItr, ShapeContext.absent())) continue;

            world.setBlockState(blockPosItr, hotObsidianDefaultState);
            world.scheduleBlockTick(blockPosItr, ModBlocks.HOT_OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
            world.syncWorldEvent(ModWorldEvents.HOT_OBSIDIAN_CREATION, blockPosItr, 0);
        }
    }
}
