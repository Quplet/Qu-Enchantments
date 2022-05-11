package qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.ActionResult;

public interface AnvilEvents {
    /**
     * Called at the end of an Anvil usage.
     */
    Event<OnAnvilUse> ANVIL_USED = EventFactory.createArrayBacked(OnAnvilUse.class, listeners -> (player, stack, handler) -> {
                for (OnAnvilUse listener : listeners) {
                    listener.onUse(player, stack, handler);
                }
            });

    Event<OnAnvilUpdate> ANVIL_UPDATE = EventFactory.createArrayBacked(OnAnvilUpdate.class, listeners -> (handler) -> {
                for (OnAnvilUpdate listener : listeners) {
                    listener.onUpdate(handler);
                }
            });

    interface OnAnvilUse {
        void onUse(PlayerEntity player, ItemStack stack, AnvilScreenHandler handler);
    }

    interface OnAnvilUpdate {
        void onUpdate(AnvilScreenHandler handler);
    }
}
