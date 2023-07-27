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
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.world.ModWorldEvents;

public class SkywalkerEnchantment extends CorruptedEnchantment {

    private static final ModConfig.SkywalkerOptions CONFIG = QuEnchantments.getConfig().skywalkerOptions;

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
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 2 : 0;
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
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DepthStriderEnchantment) && super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        World world;

        if ((world = entity.getWorld()).isClient || !entity.isOnGround() || !entity.isSneaking()) return;

        final float sinkDistance = 0.875f;
        final BlockState cloudDefaultState = ModBlocks.CLOUD.getDefaultState();
        final int radius = Math.min(16, CONFIG.radius);

        for (BlockPos blockPosItr : BlockPos.iterate(
                new BlockPos(entity.getBlockX() - radius, Math.round((float)entity.getY() - sinkDistance), entity.getBlockZ() - radius),
                new BlockPos(entity.getBlockX() + radius, Math.round((float)entity.getY() - sinkDistance), entity.getBlockZ() + radius)
        )) {
            if (!world.getBlockState(blockPosItr).equals(Blocks.AIR.getDefaultState()) ||
                    !blockPosItr.isWithinDistance(entity.getPos(), Math.max(radius, 1)) ||
                    !world.getBlockState(blockPosItr.up()).isAir() ||
                    !cloudDefaultState.canPlaceAt(world, blockPosItr) ||
                    !world.canPlace(cloudDefaultState, blockPosItr, ShapeContext.absent())) continue;

            world.setBlockState(blockPosItr, cloudDefaultState);
            int overworldMultiplier = world.getDimension().ultrawarm() ? 1 : 2;
            int duration = Math.max(1, CONFIG.cloudDuration) * overworldMultiplier * level;
            world.scheduleBlockTick(
                    blockPosItr,
                    ModBlocks.CLOUD,
                    MathHelper.nextInt(entity.getRandom(), duration, duration * 2)
            );
            world.syncWorldEvent(ModWorldEvents.CLOUD_BLOCK_CREATION, blockPosItr, 0);
        }
    }
}
