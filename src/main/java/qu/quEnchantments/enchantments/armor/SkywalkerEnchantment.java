package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.DepthStriderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.world.ModWorldEvents;

public class SkywalkerEnchantment extends CorruptedEnchantment {

    public static final float SINK_DISTANCE = 0.875f;
    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public SkywalkerEnchantment(Properties properties) {
        super(EnchantmentType.WALKER, properties);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.skywalkerBookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.skywalkerRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.skywalkerEnchantingTable;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof DepthStriderEnchantment) && super.canAccept(other);
    }

    @Override
    public void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos, ItemStack stack, int level) {
        World world;

        if ((world = entity.getWorld()).isClient || !entity.isOnGround() || !entity.isSneaking()) return;

        final BlockState cloudDefaultState = ModBlocks.CLOUD.getDefaultState();
        final int radius = Math.min(16, CONFIG.skywalkerRadius);

        for (BlockPos blockPosItr : BlockPos.iterate(
                new BlockPos(entity.getBlockX() - radius, Math.round((float)entity.getY() - SINK_DISTANCE), entity.getBlockZ() - radius),
                new BlockPos(entity.getBlockX() + radius, Math.round((float)entity.getY() - SINK_DISTANCE), entity.getBlockZ() + radius)
        )) {
            if (!world.getBlockState(blockPosItr).equals(Blocks.AIR.getDefaultState()) ||
                    !blockPosItr.isWithinDistance(entity.getPos(), Math.max(radius, 1)) ||
                    !world.getBlockState(blockPosItr.up()).isAir() ||
                    !cloudDefaultState.canPlaceAt(world, blockPosItr) ||
                    !world.canPlace(cloudDefaultState, blockPosItr, ShapeContext.absent())) continue;

            world.setBlockState(blockPosItr, cloudDefaultState);
            int overworldMultiplier = world.getDimension().ultrawarm() ? 1 : 2;
            int duration = Math.max(1, CONFIG.skywalkerCloudDuration) * overworldMultiplier * level;
            world.scheduleBlockTick(
                    blockPosItr,
                    ModBlocks.CLOUD,
                    MathHelper.nextInt(entity.getRandom(), duration, duration * 2)
            );
            world.syncWorldEvent(ModWorldEvents.CLOUD_BLOCK_CREATION, blockPosItr, 0);
        }
    }
}
