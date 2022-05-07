package net.qu.quEnchantments;

import net.fabricmc.api.ModInitializer;
import net.qu.quEnchantments.blocks.ModBlocks;
import net.qu.quEnchantments.enchantments.ModEnchantments;
import net.qu.quEnchantments.util.ModLootTableModifier;
import net.qu.quEnchantments.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Qu, FabricMC
 */
public class QuEnchantments implements ModInitializer {
	public static final String MOD_ID = "qu-enchantments";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModEnchantments.registerModEnchantments();

		ModBlocks.registerModBlocks();

		ModRegistries.RegisterModEvents();

		ModLootTableModifier.ModifyLootTables();

		LOGGER.info("Hello Fabric world!");
	}
}
