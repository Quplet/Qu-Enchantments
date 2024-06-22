package qu.quEnchantments;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;
import qu.quEnchantments.blocks.ModBlocks;
import qu.quEnchantments.particle.InaneParticle;
import qu.quEnchantments.particle.ModParticles;

public class QuEnchantmentsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOUD, RenderLayer.getTranslucent());

        ParticleFactoryRegistry.getInstance().register(ModParticles.INANE_PARTICLE, InaneParticle.Factory::new);
    }
}
