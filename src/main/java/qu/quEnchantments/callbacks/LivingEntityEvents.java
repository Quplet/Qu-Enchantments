package qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface LivingEntityEvents {
    Event<OnLivingEntityTick> ON_TICK_EVENT = EventFactory.createArrayBacked(OnLivingEntityTick.class,
            (listeners) -> livingEntity -> {
                for (OnLivingEntityTick listener : listeners) {
                    listener.onTick(livingEntity);
                }
            });


    interface OnLivingEntityTick {
        void onTick(LivingEntity livingEntity);
    }
}
