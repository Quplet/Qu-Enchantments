package qu.quEnchantments.entity.ai.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import qu.quEnchantments.enchantments.ModEnchantments;

import java.util.EnumSet;

/**
 * I would just make an extension of the {@link net.minecraft.entity.ai.goal.FollowOwnerGoal} instead of completely remaking it,
 * but that goal requires the entity to be a {@link net.minecraft.entity.passive.TameableEntity}, which the horse is not.
 */
public class FidelityFollowOwnerGoal extends Goal {

    private HorseEntity horse;
    private LivingEntity owner;
    private final WorldView world;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;
    private final boolean leavesAllowed;

    public FidelityFollowOwnerGoal(HorseEntity horse, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.horse = horse;
        this.world = horse.world;
        this.speed = speed;
        this.navigation = horse.getNavigation();
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.horse.isTame()) {
            return false;
        }
        if (this.horse.isLeashed()) {
            return false;
        }
        if (this.horse.getOwnerUuid() == null) {
            return false;
        }
        LivingEntity livingEntity = this.horse.world.getPlayerByUuid(this.horse.getOwnerUuid());
        if (livingEntity == null) {
            return false;
        }
        if (livingEntity.isSpectator()) {
            return false;
        }
        if (this.horse.squaredDistanceTo(livingEntity) < (this.maxDistance * this.maxDistance)) {
            return false;
        }
        if (EnchantmentHelper.getLevel(ModEnchantments.FIDELITY, this.horse.getArmorType()) == 0) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }
        if (this.horse.squaredDistanceTo(this.owner) <= (this.minDistance * this.minDistance)) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.horse.getPathfindingPenalty(PathNodeType.WATER);
        this.horse.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.horse.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.horse.getLookControl().lookAt(this.owner, 10.0f, this.horse.getMaxLookPitchChange());
        if (this.horse.getPrimaryPassenger() != null && !this.horse.getPrimaryPassenger().getUuid().equals(owner.getUuid())) {
            horse.removeAllPassengers();
            this.horse.playAngrySound();
            this.horse.world.sendEntityStatus(this.horse, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        if (this.horse.isLeashed() || this.horse.hasVehicle()) {
            return;
        }
        if (this.horse.squaredDistanceTo(this.owner) >= 256.0) {
            this.tryTeleport();
        } else {
            this.navigation.startMovingTo(this.owner, this.speed);
        }
    }


    private void tryTeleport() {
        BlockPos blockPos = this.owner.getBlockPos();
        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (!bl) continue;
            return;
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.getX()) < 2.0 && Math.abs((double)z - this.owner.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.horse.refreshPositionAndAngles((double)x + 0.5, y, (double)z + 0.5, this.horse.getYaw(), this.horse.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockState blockState = this.world.getBlockState(pos.down());
        if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(this.horse.getBlockPos());
        return this.world.isSpaceEmpty(this.horse, this.horse.getBoundingBox().offset(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.horse.getRandom().nextInt(max - min + 1) + min;
    }
}
