package net.qu.quEnchantments;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.ActionResult;

public interface AnvilUpdateResultCallback {
    Event<AnvilUpdateResultCallback> EVENT = EventFactory.createArrayBacked(AnvilUpdateResultCallback.class,
            (listeners) -> (handler) -> {
                for (AnvilUpdateResultCallback listener : listeners) {
                    ActionResult result = listener.interact(handler);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });
    ActionResult interact(AnvilScreenHandler handler);
}
