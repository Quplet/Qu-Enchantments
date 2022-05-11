package qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public interface ApplyMovementEffectsCallback {

    /**
     * Called at the end of {@link LivingEntity}'s applyMovementEffects method. Ie, at the end of each tick the
     * LivingEntity is moving (not while crouched).
     */
    Event<ApplyMovementEffectsCallback> EVENT = EventFactory.createArrayBacked(ApplyMovementEffectsCallback.class,
            (listeners) -> (entity, blockPos) -> {
                for (ApplyMovementEffectsCallback listener : listeners) {
                    listener.interact(entity, blockPos);
                }
            });

    void interact(LivingEntity entity, BlockPos blockPos);
}
