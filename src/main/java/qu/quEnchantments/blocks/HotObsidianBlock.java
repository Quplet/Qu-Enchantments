package qu.quEnchantments.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

import java.util.Random;
// TODO make texture for this block
public class HotObsidianBlock extends Block {

    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;
    public HotObsidianBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            int i = state.get(AGE);
            if (i > 0 && i < 4) {
                entity.damage(DamageSource.HOT_FLOOR, 1.0f + 0.1f * (i - 1));
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.scheduledTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((random.nextInt(3) == 0 || this.canMelt(world, pos, 4)) && this.increaseAge(state, world, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutable.set(pos, direction);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this) || this.increaseAge(blockState, world, mutable)) continue;
                world.createAndScheduleBlockTick(mutable, this, MathHelper.nextInt(random, 20, 40));
            }
            return;
        }
        world.createAndScheduleBlockTick(pos, this, MathHelper.nextInt(random, 20, 40));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int i = state.get(AGE);
        if (i < MAX_AGE) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            if (i + 1 == 4) {
                BlockPos pos2 = pos.up(1);
                world.setBlockState(pos2, Blocks.FIRE.getDefaultState());
            }
            return false;
        }
        this.melt(world, pos);
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (block.getDefaultState().isOf(this) && this.canMelt(world, pos, 2)) {
            this.melt(world, pos);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction != Direction.DOWN && neighborState.getBlock() == Blocks.WATER) {
            world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), Block.NOTIFY_ALL);
            world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
        }
        return state;
    }

    private boolean canMelt(BlockView world, BlockPos pos, int maxNeighbors) {
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            if (!world.getBlockState(mutable).isOf(this) || ++i < maxNeighbors) continue;
            return false;
        }
        return true;
    }

    protected void melt(World world, BlockPos pos) {
        world.removeBlock(pos, false);
        world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        world.updateNeighbor(pos, Blocks.LAVA, pos);
    }
}
