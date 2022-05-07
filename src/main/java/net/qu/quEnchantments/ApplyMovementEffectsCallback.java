package net.qu.quEnchantments;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface ApplyMovementEffectsCallback {

    /**
     * Called at the end of {@link LivingEntity}'s applyMovementEffects method. Ie, at the end of each tick the
     * LivingEntity is moving (not while crouched).
     */
    Event<ApplyMovementEffectsCallback> EVENT = EventFactory.createArrayBacked(ApplyMovementEffectsCallback.class,
            (listeners) -> (entity, blockPos) -> {
                for (ApplyMovementEffectsCallback listener : listeners) {
                    ActionResult result = listener.interact(entity, blockPos);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity entity, BlockPos blockPos);
}
