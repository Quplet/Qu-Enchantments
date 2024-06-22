package qu.quEnchantments;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.ModEnchantmentEffects;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.particle.ModParticles;
import qu.quEnchantments.util.ModLootTableModifier;
import qu.quEnchantments.util.ModTradeRegistry;

/**
 *
 * @author Qu
 */
public class QuEnchantments implements ModInitializer {
	public static final String MOD_ID = "qu-enchantments";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEnchantments.registerModEnchantments();
		ModEnchantmentEffects.registerEnchantmentEffects();
		ModBlocks.registerModBlocks();
		ModItems.initializeModItems();
		ModParticles.registerModParticles();
		ModLootTableModifier.ModifyLootTables();
		ModTradeRegistry.initializeModTrades();

		LOGGER.info("Finished initializing " + MOD_ID);
	}
}
