package qu.quEnchantments.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

public class ModParticles {

    public static final SimpleParticleType INANE_PARTICLE = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(QuEnchantments.MOD_ID, "inane_particle"), INANE_PARTICLE);
        QuEnchantments.LOGGER.info("Finished registering Mod Particles for " + QuEnchantments.MOD_ID);
    }
}
