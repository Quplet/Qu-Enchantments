package qu.quEnchantments;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.render.RenderLayer;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.particle.InaneParticle;
import qu.quEnchantments.particle.ModParticles;
import qu.quEnchantments.util.ModEvents;
import qu.quEnchantments.util.ModLootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qu.quEnchantments.util.ModTradeRegistry;

/**
 *
 * @author Qu
 */
public class QuEnchantments implements ModInitializer, ClientModInitializer, PreLaunchEntrypoint {
	public static final String MOD_ID = "qu-enchantments";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModEnchantments.registerModEnchantments();
		ModBlocks.registerModBlocks();
		ModItems.initializeModItems();
		ModParticles.registerModParticles();
		ModEvents.RegisterModEvents();
		ModLootTableModifier.ModifyLootTables();
		ModTradeRegistry.initializeModTrades();

		LOGGER.info("Finished Initializing " + MOD_ID);
	}

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOUD, RenderLayer.getTranslucent());

		ParticleFactoryRegistry.getInstance().register(ModParticles.INANE_PARTICLE, InaneParticle.Factory::new);
	}

	@Override
	public void onPreLaunch() {
		MixinExtrasBootstrap.init();
	}
}
