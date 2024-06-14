package qu.quEnchantments.enchantments.weapon;

import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class AccuracyEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public AccuracyEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.accuracyRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.accuracyBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.accuracyEnchantingTable;
    }
}
