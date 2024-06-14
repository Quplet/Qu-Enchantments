package qu.quEnchantments.enchantments.weapon;

import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ArrowsFlightEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public ArrowsFlightEnchantment(Properties properties) {
        super(properties);
    }


    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.arrowsFlightRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.arrowsFlightBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.arrowsFlightEnchantingTable;
    }
}
