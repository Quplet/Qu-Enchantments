package qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface EntityEvents {

    Event<OnEntityJoinWorld> ENTITY_JOIN_WORLD_EVENT = EventFactory.createArrayBacked(OnEntityJoinWorld.class,
            (listeners) -> (entity, world) -> {
                for (OnEntityJoinWorld listener : listeners) {
                    listener.onEntityJoinWorld(entity, world);
                }
            });

    interface OnEntityJoinWorld {
        void onEntityJoinWorld(Entity entity, World world);
    }
}
