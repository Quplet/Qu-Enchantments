package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
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
    private static void quEnchantments$addQuEnchantmentCondition(FeatureSet enabledFeatures, int level, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        if (treasureAllowed) return;

        List<EnchantmentLevelEntry> list = cir.getReturnValue();
        List<QuEnchantment> quEnchantments = new ArrayList<>(ModEnchantments.QU_ENCHANTMENTS);

        list.removeIf(enchantmentLevelEntry -> {
            if (enchantmentLevelEntry.enchantment instanceof QuEnchantment quEnchantment) {
                quEnchantments.remove(quEnchantment);
                return !quEnchantment.isAvailableForEnchantingTable();
            }
            return false;
        });

        for (QuEnchantment enchantment : quEnchantments) {
            if (!enchantment.isAvailableForEnchantingTable() || enchantment.isAvailableForRandomSelection()) continue;
            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (level < enchantment.getMinPower(i) || level > enchantment.getMaxPower(i)) continue;
                list.add(new EnchantmentLevelEntry(enchantment, i));
                break;
            }
        }
    }
}
