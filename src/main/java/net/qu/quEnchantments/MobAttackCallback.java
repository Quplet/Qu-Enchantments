package net.qu.quEnchantments;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

public interface MobAttackCallback {
    /**
     * Callback for a mob attacking an {@link Entity}. Called at the beginning.
     */
    Event<MobAttackCallback> EVENT = EventFactory.createArrayBacked(MobAttackCallback.class,
            (listeners) -> (user, target) -> {
                for (MobAttackCallback listener : listeners) {
                    ActionResult result = listener.interact(user, target);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(MobEntity user, Entity target);
}
