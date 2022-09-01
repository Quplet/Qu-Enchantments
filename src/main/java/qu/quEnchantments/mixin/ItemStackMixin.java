package qu.quEnchantments.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.interfaces.IItemStack;

import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IItemStack {

    @Unique
    private boolean isEnchantmentsDirty = false;

    @Inject(method = "addEnchantment", at = @At("TAIL"))
    private void quEnchantments$makeStackDirty(Enchantment enchantment, int level, CallbackInfo ci) {
        this.isEnchantmentsDirty = true;
    }

    @Override
    @Unique
    public boolean isEnchantmentsDirty() {
        return this.isEnchantmentsDirty;
    }

    @Override
    @Unique
    public void setEnchantmentsDirty(boolean value) {
        this.isEnchantmentsDirty = value;
    }

    @Override
    @Unique
    public int corruptedLevel() {
        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.get((ItemStack)(Object)this).entrySet()) {
            if (entry.getKey() instanceof CorruptedEnchantment) return entry.getValue();
        }
        return 0;
    }
}
