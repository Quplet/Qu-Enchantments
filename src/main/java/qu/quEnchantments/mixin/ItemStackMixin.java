package qu.quEnchantments.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.interfaces.IItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IItemStack {

    @Unique
    private boolean isEnchantmentsDirty = false;

    @Inject(method = "addEnchantment", at = @At("TAIL"))
    private void qu_Enchantments$makeStackDirty(Enchantment enchantment, int level, CallbackInfo ci) {
        this.isEnchantmentsDirty = true;
    }

    @Override
    @Unique
    public boolean qu_Enchantments$isEnchantmentsDirty() {
        return this.isEnchantmentsDirty;
    }

    @Override
    @Unique
    public void qu_Enchantments$setEnchantmentsDirty(boolean value) {
        this.isEnchantmentsDirty = value;
    }

    @Override
    @Unique
    public int qu_Enchantments$corruptedLevel() {
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : EnchantmentHelper.getEnchantments((ItemStack)(Object)this).getEnchantmentsMap()) {
            if (entry.getKey().value() instanceof CorruptedEnchantment) return entry.getIntValue();
        }
        return 0;
    }
}
