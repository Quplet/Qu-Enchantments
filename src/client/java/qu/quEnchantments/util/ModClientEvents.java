package qu.quEnchantments.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.particle.ModParticles;
import qu.quEnchantments.util.interfaces.IEntity;

public class ModClientEvents {
    public static void registerClientEvents() {
        LivingEntityEvents.ON_TICK_EVENT.register(livingEntity -> {
            World world = livingEntity.getWorld();

            if (!world.isClient) return;
            // Inane effect client particle logic
            if (((IEntity)livingEntity).getInaneTicks() > 0 && livingEntity != MinecraftClient.getInstance().player) {
                double px = livingEntity.getParticleX(1.0);
                double py = livingEntity.getRandomBodyY();
                double pz = livingEntity.getParticleZ(1.0);
                world.addParticle(ModParticles.INANE_PARTICLE, px, py, pz, (livingEntity.getX() - px) * 0.05, (py - livingEntity.getY()) * 0.05, (livingEntity.getZ() - pz) * 0.05);
            }
        });
    }
}
