package qu.quEnchantments.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModParticles {

    public static final DefaultParticleType INANE_PARTICLE = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(QuEnchantments.MOD_ID, "inane_particle"), INANE_PARTICLE);
        QuEnchantments.LOGGER.info("Finished registering Mod Particles for " + QuEnchantments.MOD_ID);
    }
}
