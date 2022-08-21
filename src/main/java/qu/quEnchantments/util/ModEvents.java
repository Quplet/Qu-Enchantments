package qu.quEnchantments.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.rune.AggressionBlessingEnchantment;
import qu.quEnchantments.enchantments.rune.SpeedBlessingEnchantment;
import qu.quEnchantments.particle.ModParticles;
import qu.quEnchantments.util.interfaces.IEntity;

public class ModEvents {

    public static void RegisterModEvents() {

        LivingEntityEvents.ON_TICK_EVENT.register(livingEntity -> {
            if (livingEntity.world.isClient) {
                // Inane effect client particle logic
                if (((IEntity)livingEntity).getInaneTicks() > 0 && livingEntity != MinecraftClient.getInstance().player) {
                    double px = livingEntity.getParticleX(1.0);
                    double py = livingEntity.getRandomBodyY();
                    double pz = livingEntity.getParticleZ(1.0);
                    livingEntity.world.addParticle(ModParticles.INANE_PARTICLE, px, py, pz, (livingEntity.getX() - px) * 0.05, (py - livingEntity.getY()) * 0.05, (livingEntity.getZ() - pz) * 0.05);
                }
            } else {
                EntityAttributeInstance instance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (instance != null && instance.getModifier(SpeedBlessingEnchantment.BLESSING_BOOST.getId()) != null) {
                    instance.removeModifier(SpeedBlessingEnchantment.BLESSING_BOOST);
                }
                instance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
                if (instance != null && instance.getModifier(AggressionBlessingEnchantment.ATTACK_BOOST.getId()) != null) {
                    instance.removeModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
                }
            }
        });
    }
}
