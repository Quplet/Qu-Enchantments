package qu.quEnchantments.enchantments.weapon;

import net.minecraft.entity.EntityType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ShapedGlassEnchantment extends CorruptedEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public ShapedGlassEnchantment(Properties properties) {
        super(EnchantmentType.DAMAGE, properties);
    }

    @Override
    public float getAttackDamage(int level, @Nullable EntityType<?> entityType) {
        return level * CONFIG.shapedGlassDamageMultiplier;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.shapedGlassBookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.shapedGlassRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.shapedGlassEnchantingTable;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.isAcceptableItem(stack);
    }
}
