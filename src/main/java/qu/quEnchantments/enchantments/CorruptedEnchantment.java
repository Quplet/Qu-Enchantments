package qu.quEnchantments.enchantments;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import qu.quEnchantments.util.interfaces.IItemStack;
import qu.quEnchantments.util.ModTags;

import java.util.Random;
import java.util.Set;

/**
 * A Corrupted Enchantment abstraction with a custom getName(int level) method implementation.
 */
public abstract class CorruptedEnchantment extends QuEnchantment {

    private final CorruptedEnchantment.EnchantmentType enchantmentType;

    public CorruptedEnchantment(CorruptedEnchantment.EnchantmentType enchantmentType, Properties properties) {
        super(properties);
        this.enchantmentType = enchantmentType;
    }


    /**
     * Will return the formatted name of the enchantment. Corrupted Enchantments will have a light purple color and will
     *     not display the enchantment level if the level is 1.
     *
     * @param level The level of the enchantment
     * @return The formatted name of the enchantment
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
     *     the same type. The Corrupted Enchantment's level will match the highest consumed enchantment's level.
     *
     * @param stack The {@link ItemStack} to corrupt
     */
    public static void corruptEnchantments(ItemStack stack) {
        if (stack == null ||
                (!stack.hasEnchantments() && !stack.isOf(Items.ENCHANTED_BOOK)) ||
                !((IItemStack)(Object)stack).qu_Enchantments$isEnchantmentsDirty()) return;

        CorruptedEnchantment corruptedEnchantment = null;
        int corruptedLevel = 0;
        int newLevel = 0;

        Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> enchantmentsMap = EnchantmentHelper.getEnchantments(stack).getEnchantmentsMap();

        if (enchantmentsMap.size() < 2) {
            ((IItemStack)(Object)stack).qu_Enchantments$setEnchantmentsDirty(false);
            return;
        }

        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : enchantmentsMap) {
            Enchantment enchantment = entry.getKey().value();
            if (enchantment instanceof CorruptedEnchantment) {
                corruptedEnchantment = (CorruptedEnchantment) enchantment;
                corruptedLevel = entry.getIntValue();
                break;
            }
        }

        ((IItemStack)(Object)stack).qu_Enchantments$setEnchantmentsDirty(false);
        if (corruptedEnchantment == null) return;

        Object2IntMap<Enchantment> newMap = new Object2IntOpenHashMap<>();

        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : enchantmentsMap) {
            Enchantment enchantment = entry.getKey().value();
            int level = entry.getIntValue();
            if (enchantment.isCursed() || entry.getKey().isIn(corruptedEnchantment.enchantmentType.corruptible)) {
                if (enchantment instanceof CompoundEnchantment) level /= 5;
                newLevel += level;
            } else {
                newMap.put(enchantment, level);
            }
        }

        if (newLevel == corruptedLevel) newLevel++;
        newLevel = Math.clamp(newLevel, corruptedLevel, corruptedEnchantment.getMaxLevel());
        newMap.put(corruptedEnchantment, newLevel);

        EnchantmentHelper.apply(stack, components -> {
            components.remove(e -> !newMap.containsKey(e));
            for (Object2IntMap.Entry<Enchantment> entry : newMap.object2IntEntrySet()) {
                components.set(entry.getKey(), entry.getIntValue());
            }
        });



//        if (stack == null ||
//                (!stack.hasEnchantments() && !stack.isOf(Items.ENCHANTED_BOOK)) ||
//                !((IItemStack)(Object)stack).isEnchantmentsDirty()) return;
//
//        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
//        CorruptedEnchantment corruptedEnchantment = null;
//        int cLevel = 0;
//
//        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
//            if (entry.getKey() instanceof CorruptedEnchantment) {
//                corruptedEnchantment = (CorruptedEnchantment) entry.getKey();
//                cLevel = entry.getValue();
//                break;
//            }
//        }
//        ((IItemStack)(Object)stack).setEnchantmentsDirty(false);
//        if (corruptedEnchantment == null) return;
//
//        int levels = 0;
//        Set<Enchantment> newSet = Set.copyOf(enchantments.keySet());
//
//        for (Enchantment enchantment : newSet) {
//            Optional<RegistryKey<Enchantment>> key;
//            Optional<RegistryEntry.Reference<Enchantment>> entry;
//            if (enchantment.isCursed() || (key = Registries.ENCHANTMENT.getKey(enchantment)).isPresent() &&
//                    (entry = Registries.ENCHANTMENT.getEntry(key.get())).isPresent() &&
//                    entry.get().isIn(corruptedEnchantment.enchantmentType.corruptible)) {
//                int level = enchantments.remove(enchantment);
//                if (enchantment instanceof CompoundEnchantment) level /= 5;
//                levels += level;
//            }
//        }
//
//        if (levels == cLevel) levels++;
//        levels = Math.min(Math.max(cLevel, levels), corruptedEnchantment.getMaxLevel());
//        enchantments.put(corruptedEnchantment, levels);
//        if (stack.isOf(Items.ENCHANTED_BOOK)) stack.removeSubNbt(EnchantedBookItem.STORED_ENCHANTMENTS_KEY);
//        EnchantmentHelper.set(enchantments, stack);
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
