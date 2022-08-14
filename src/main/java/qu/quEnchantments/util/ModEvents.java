package qu.quEnchantments.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.particle.ParticleTypes;
import qu.quEnchantments.callbacks.EntityEvents;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.entity.ai.goals.FidelityFollowOwnerGoal;
import qu.quEnchantments.mixin.MobEntityAccessor;
import qu.quEnchantments.particle.ModParticles;

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
                // Essence of Ender particle logic
                if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.ESSENCE_OF_ENDER, livingEntity) > 0) {
                    livingEntity.world.addParticle(ParticleTypes.PORTAL, livingEntity.getParticleX(0.5),
                            livingEntity.getRandomBodyY() - 0.1, livingEntity.getParticleZ(0.5),(livingEntity.getRandom().nextDouble() - 0.5) * 2.0, -livingEntity.getRandom().nextDouble(),(livingEntity.getRandom().nextDouble() - 0.5) * 2.0);
                }
            }
        });

        EntityEvents.ENTITY_JOIN_WORLD_EVENT.register((entity, world) -> {
            if (!world.isClient) {
                if (entity instanceof HorseEntity horse) {
                    ((MobEntityAccessor) horse).getGoalSelector().add(2, new FidelityFollowOwnerGoal(horse, 1.3, 5.0f, 10.0f, false));
                }
            }
        });
    }
}
