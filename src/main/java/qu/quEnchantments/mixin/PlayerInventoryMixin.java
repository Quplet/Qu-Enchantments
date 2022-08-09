package qu.quEnchantments.mixin;

import com.google.common.collect.Iterables;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow @Final public PlayerEntity player;

    @Shadow @Final public DefaultedList<ItemStack> main;

    @Shadow @Final public DefaultedList<ItemStack> offHand;

    @Shadow @Final public DefaultedList<ItemStack> armor;

    @Inject(method = "updateItems", at = @At("TAIL"))
    private void quEnchantments$QuEnchantmentsTick(CallbackInfo ci) {
        Iterable<ItemStack> inventory = Iterables.concat(this.main, this.offHand, this.armor);
        QuEnchantmentHelper.tick(this.player, inventory);
        QuEnchantmentHelper.tickWhileEquipped(this.player);
    }
}
