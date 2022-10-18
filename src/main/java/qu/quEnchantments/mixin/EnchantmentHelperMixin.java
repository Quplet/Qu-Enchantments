package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getPossibleEntries", at = @At("TAIL"))
    private static void quEnchantments$addQuEnchantmentCondition(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        if (treasureAllowed) return;
        List<EnchantmentLevelEntry> list = cir.getReturnValue();
        List<QuEnchantment> quEnchantments = new ArrayList<>(ModEnchantments.QU_ENCHANTMENTS);
        for (QuEnchantment enchantment : ModEnchantments.QU_ENCHANTMENTS) {
            for (EnchantmentLevelEntry entry : list) {
                if (enchantment != entry.enchantment) continue;
                quEnchantments.remove(enchantment);
                if (!enchantment.isAvailableForEnchantingTable()) list.remove(entry);
            }
        }
        for (QuEnchantment enchantment : quEnchantments) {
            if (!enchantment.isAvailableForEnchantingTable() || enchantment.isAvailableForRandomSelection()) continue;
            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
                list.add(new EnchantmentLevelEntry(enchantment, i));
                break;
            }
        }
    }
}
