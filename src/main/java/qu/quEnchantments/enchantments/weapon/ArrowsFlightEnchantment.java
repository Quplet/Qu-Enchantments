package qu.quEnchantments.enchantments.weapon;

import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ArrowsFlightEnchantment extends QuEnchantment {

    public static final ModConfig.ArrowsFlightOptions CONFIG = QuEnchantments.getConfig().arrowsFlightOptions;

    public ArrowsFlightEnchantment(Properties properties) {
        super(properties);
    }

//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 2 : 0;
//    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }
}
