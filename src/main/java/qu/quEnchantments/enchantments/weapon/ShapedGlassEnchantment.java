package qu.quEnchantments.enchantments.weapon;

import net.minecraft.entity.EntityType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ShapedGlassEnchantment extends CorruptedEnchantment {

    private static final ModConfig.ShapedGlassOptions CONFIG = QuEnchantments.getConfig().shapedGlassOptions;

    public ShapedGlassEnchantment(Properties properties) {
        super(EnchantmentType.DAMAGE, properties);
    }

    @Override
    public float getAttackDamage(int level, @Nullable EntityType<?> entityType) {
        return level * CONFIG.damageMultiplier;
    }

//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 5 : 0;
//    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.isAcceptableItem(stack);
    }
}
