package qu.quEnchantments.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.armor.SkywalkerEnchantment;

public class CloudBlock extends Block {

    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public CloudBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(3) == 0 || world.hasRain(pos.up())) {
            world.removeBlock(pos, false);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.updateNeighbor(pos, Blocks.AIR, pos);
            return;
        }
        double d = (double)pos.getX() + random.nextDouble();
        double e = (double)pos.getY() - 0.05;
        double f = (double)pos.getZ() + random.nextDouble();
        world.spawnParticles(world.getDimension().ultrawarm() ? ParticleTypes.SMOKE : ParticleTypes.DRIPPING_WATER, d, e, f, 4, 0.0, 0.0, 0.0, 0.0);
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, 50, 100));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Entity entity;
        if (context instanceof EntityShapeContext && (entity = ((EntityShapeContext)context).getEntity()) != null) {
            if (entity.isSneaking() || entity instanceof SpiderEntity || entity instanceof SilverfishEntity || entity instanceof EndermiteEntity) {
                if (entity.getY() >= pos.getY() + SkywalkerEnchantment.SINK_DISTANCE && entity.fallDistance <= 1.0f) {
                    return COLLISION_SHAPE;
                }
            }
        }
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        double a = entity.getVelocity().getX();
        double b = entity.getVelocity().getY();
        double c = entity.getVelocity().getZ();
        if (b <= 0) {
            b *= 0.2;
            if (entity.getY() < pos.getY() + 0.875) {
                entity.fallDistance = 1.0000001f;
                a *= 0.5;
                c *= 0.5;
            }
        } else {
            entity.fallDistance = 0;
        }
        entity.setVelocity(a, b, c);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            return true;
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }
}
