package net.qu.quEnchantments;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.ActionResult;

public interface AnvilUsedCallback {
    /**
     * Called at the end of an Anvil usage.
     */
    Event<AnvilUsedCallback> EVENT = EventFactory.createArrayBacked(AnvilUsedCallback.class,
            (listeners) -> (player, stack, handler) -> {
                for (AnvilUsedCallback listener : listeners) {
                    ActionResult result = listener.interact(player, stack, handler);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, ItemStack stack, AnvilScreenHandler handler);
}
