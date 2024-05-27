package qu.quEnchantments;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.particle.ModParticles;
import qu.quEnchantments.util.ModEvents;
import qu.quEnchantments.util.ModLootTableModifier;
import qu.quEnchantments.util.ModTradeRegistry;
import qu.quEnchantments.util.config.ModConfig;

/**
 *
 * @author Qu
 */
public class QuEnchantments implements ModInitializer {
	public static final String MOD_ID = "qu-enchantments";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static ModConfig config;

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		ModEnchantments.registerModEnchantments();
		ModBlocks.registerModBlocks();
		ModItems.initializeModItems();
		ModParticles.registerModParticles();
		ModEvents.RegisterModEvents();
		ModLootTableModifier.ModifyLootTables();
		ModTradeRegistry.initializeModTrades();

		LOGGER.info("Finished Initializing " + MOD_ID);
	}

	public static ModConfig getConfig() {
		return config;
	}
}
