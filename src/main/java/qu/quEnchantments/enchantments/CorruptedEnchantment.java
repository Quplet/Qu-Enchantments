package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import qu.quEnchantments.util.interfaces.IItemStack;
import qu.quEnchantments.util.ModTags;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * A Corrupted Enchantment abstraction with a custom getName(int level) method implementation.
 */
public abstract class CorruptedEnchantment extends QuEnchantment {

    private final CorruptedEnchantment.EnchantmentType enchantmentType;

    public CorruptedEnchantment(CorruptedEnchantment.EnchantmentType enchantmentType, Rarity weight,
                                EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
        this.enchantmentType = enchantmentType;
    }


    /**
     * Will return the formatted name of the enchantment. Corrupted Enchantments will have a light purple color and will
     * not display the enchantment level if the level is 1.
     *
     * @param level The level of the enchantment.
     * @return The formatted name of the enchantment.
     */
    @Override
    public Text getName(int level) {
        Random random = new Random();
        MutableText mutableText = Text.translatable(this.getTranslationKey());
        mutableText.formatted(Formatting.LIGHT_PURPLE);
        if (random.nextFloat() < 0.02f * level) mutableText.formatted(Formatting.OBFUSCATED);
        if (level != 1) {
            mutableText.append(" ").append(Text.translatable("enchantment.level." + level));
        }
        return mutableText;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof CorruptedEnchantment) && super.canAccept(other);
    }

    // If you override this, make sure you call super.tickAlways(...)
    @Override
    public void tickAlways(LivingEntity wearer, ItemStack stack, int level) {
        corruptEnchantments(stack);
    }

    /**
     * Accepts an ItemStack and, if the stack contains a Corrupted Enchantment, will corrupt all other enchantments of
     * the same type. The Corrupted Enchantment's level will match the highest consumed enchantment's level.
     *
     * @param stack The {@link ItemStack} to corrupt.
     */
    public static void corruptEnchantments(ItemStack stack) {
        if (stack == null) return;
        if (!stack.hasEnchantments() && !stack.isOf(Items.ENCHANTED_BOOK)) return;
        if (!((IItemStack)(Object)stack).isEnchantmentsDirty()) return;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        CorruptedEnchantment corruptedEnchantment = null;
        int cLevel = 0;
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (entry.getKey() instanceof CorruptedEnchantment) {
                corruptedEnchantment = (CorruptedEnchantment) entry.getKey();
                cLevel = entry.getValue();
                break;
            }
        }
        ((IItemStack)(Object)stack).setEnchantmentsDirty(false);
        if (corruptedEnchantment == null) return;
        int levels = 0;
        Set<Enchantment> newSet = Set.copyOf(enchantments.keySet());
        for (Enchantment enchantment : newSet) {
            Optional<RegistryKey<Enchantment>> key;
            Optional<RegistryEntry.Reference<Enchantment>> entry;
            if (enchantment.isCursed() || (key = Registries.ENCHANTMENT.getKey(enchantment)).isPresent() &&
                    (entry = Registries.ENCHANTMENT.getEntry(key.get())).isPresent() &&
                    entry.get().isIn(corruptedEnchantment.enchantmentType.corruptible)) {
                levels += enchantments.remove(enchantment);
            }
        }
        if (levels > 0) {
            if (levels == cLevel) levels++;
            levels = Math.min(Math.max(cLevel, levels), corruptedEnchantment.getMaxLevel());
            enchantments.put(corruptedEnchantment, levels);
            if (stack.isOf(Items.ENCHANTED_BOOK)) stack.removeSubNbt("StoredEnchantments");
            EnchantmentHelper.set(enchantments, stack);
        }
    }

    /**
     * Contains a record of enchantment types currently in use by Corrupted Enchantments.
     */
    public enum EnchantmentType {
        DAMAGE(ModTags.WEAPON_DAMAGE_ENCHANTMENTS),
        ASPECT(ModTags.WEAPON_ASPECT_ENCHANTMENTS),
        WALKER(ModTags.ARMOR_FEET_WALKER_ENCHANTMENTS),
        THORNS(ModTags.ARMOR_THORNS_ENCHANTMENTS),
        RUNE(ModTags.RUNE_ENCHANTMENTS),
        PICKAXE_DROP(ModTags.MINING_TOOL_DROP_ENCHANTMENTS);

        private final TagKey<Enchantment> corruptible;

        EnchantmentType(TagKey<Enchantment> tag) {
            corruptible = tag;
        }
    }
}
