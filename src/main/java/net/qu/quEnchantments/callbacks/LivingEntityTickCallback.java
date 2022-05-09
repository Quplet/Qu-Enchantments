package net.qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface LivingEntityTickCallback {
    Event<LivingEntityTickCallback> EVENT = EventFactory.createArrayBacked(LivingEntityTickCallback.class,
            (listeners) -> livingEntity -> {
                for (LivingEntityTickCallback listener : listeners) {
                    listener.interact(livingEntity);

                }
            });

    void interact(LivingEntity livingEntity);
}
