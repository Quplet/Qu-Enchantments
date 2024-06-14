package qu.quEnchantments.enchantments.armor;

import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class FidelityEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public FidelityEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.fidelityRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.fidelityBookOffer;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.fidelityEnchantingTable;
    }
}
