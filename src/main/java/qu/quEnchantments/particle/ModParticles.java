package qu.quEnchantments.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

public class ModParticles {

    public static final SimpleParticleType INANE_PARTICLE = particleOf("inane_particle");

    public static void registerModParticles() {
        QuEnchantments.LOGGER.info("Finished registering Mod Particles for " + QuEnchantments.MOD_ID);
    }

    private static SimpleParticleType particleOf(String id) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(QuEnchantments.MOD_ID, id), FabricParticleTypes.simple());
    }
}
