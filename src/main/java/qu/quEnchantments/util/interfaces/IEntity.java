package qu.quEnchantments.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public interface IEntity {

    int quEnchantments$getInaneTicks();

    void quEnchantments$setInaneTicks(int value);

    static boolean isCreativePlayer(Entity entity) {
        return entity instanceof PlayerEntity player && player.getAbilities().creativeMode;
    }

    static boolean teleportTo(LivingEntity livingEntity, double x, double y, double z, int ySearchRange, boolean playSounds) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        for (int i = 0; mutable.getY() > livingEntity.getWorld().getBottomY() && !livingEntity.getWorld().getBlockState(mutable).blocksMovement() && i < ySearchRange; i++) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = livingEntity.getWorld().getBlockState(mutable);
        if (!blockState.blocksMovement() || blockState.getFluidState().isIn(FluidTags.WATER)) return false;

        Vec3d entityPos = livingEntity.getPos();
        boolean success = livingEntity.teleport(x, y, z, true);
        if (success) {
            livingEntity.getWorld().emitGameEvent(GameEvent.TELEPORT, entityPos, GameEvent.Emitter.of(livingEntity));
            if (!livingEntity.isSilent() && playSounds) {
                livingEntity.getWorld().playSound(null, livingEntity.prevX, livingEntity.prevY, livingEntity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, livingEntity.getSoundCategory(), 1.0F, 1.0F);
                livingEntity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }

        return success;
    }

}
