package qu.quEnchantments.enchantments.armor;

import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class FidelityEnchantment extends QuEnchantment {

    private static final ModConfig.FidelityOptions CONFIG = QuEnchantments.getConfig().fidelityOptions;

    public FidelityEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 1 : 0;
//    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }
}
