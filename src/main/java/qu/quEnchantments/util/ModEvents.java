package qu.quEnchantments.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import qu.quEnchantments.callbacks.EntityEvents;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.*;
import qu.quEnchantments.entity.ai.goals.FidelityFollowOwnerGoal;
import qu.quEnchantments.mixin.MobEntityAccessor;
import qu.quEnchantments.particle.ModParticles;

import java.util.List;

public class ModEvents {

    public static void RegisterModEvents() {

        LivingEntityEvents.ON_TICK_EVENT.register(livingEntity -> {
            if (!livingEntity.world.isClient) {
                // Enchantment corruption + Skywalker logic
                if (livingEntity instanceof PlayerEntity player) {
                    for (ItemStack stack : player.getInventory().main) {
                        CorruptedEnchantment.corruptEnchantments(stack);
                    }
                    int i;
                    if (player.isSneaking() && (i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.SKYWALKER, player)) > 0) {
                        SkywalkerEnchantment.condenseCloud(player, player.world, i);
                    }
                } else {
                    for (ItemStack stack : livingEntity.getItemsEquipped()) {
                        CorruptedEnchantment.corruptEnchantments(stack);
                    }
                }
                int i;
                if ((i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.NIGHTBLOOD, livingEntity)) > 0) {
                    NightbloodEnchantment.drain(livingEntity, i);
                }

                // Omen of Immunity logic.
                if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.OMEN_OF_IMMUNITY, livingEntity) > 0) {
                    livingEntity.extinguish();
                    livingEntity.setFrozenTicks(0);
                    livingEntity.clearStatusEffects();
                    ((IEntity)livingEntity).setInaneTicks(0);
                }

                // Curse of Agitation logic
                if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.AGITATION_CURSE, livingEntity) > 0 && !(livingEntity instanceof PlayerEntity player && player.getAbilities().creativeMode) && livingEntity.age % 20 == 10) {
                    List<Entity> mobs = livingEntity.world.getOtherEntities(livingEntity, livingEntity.getBoundingBox().expand(16), entity -> entity.isAlive() && !entity.isTeammate(livingEntity) && entity instanceof MobEntity mob && mob.getTarget() == null);
                    for (Entity mob : mobs) {
                        ((MobEntity)mob).setTarget(livingEntity);
                    }
                }
            } else {
                // Inane effect client particle logic
                if (((IEntity)livingEntity).getInaneTicks() > 0 && livingEntity != MinecraftClient.getInstance().player) {
                    double px = livingEntity.getParticleX(1.0);
                    double py = livingEntity.getRandomBodyY();
                    double pz = livingEntity.getParticleZ(1.0);
                    livingEntity.world.addParticle(ModParticles.INANE_PARTICLE, px, py, pz, (livingEntity.getX() - px) * 0.05, (py - livingEntity.getY()) * 0.05, (livingEntity.getZ() - pz) * 0.05);
                }
            }

            // Essence of Ender logic
            if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.ESSENCE_OF_ENDER, livingEntity) > 0) {
                if (!livingEntity.world.isClient) {
                    if (livingEntity.isWet() && livingEntity.getRandom().nextFloat() < 0.05f) {
                        if (livingEntity instanceof HorseEntity horse) {
                            horse.removeAllPassengers();
                        }
                        for (int j = 0; j < 5; j++) {
                            double d = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;
                            double e = livingEntity.getY() + (double) (livingEntity.getRandom().nextInt(32) - 16);
                            double f = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * 16.0;
                            if (EssenceOfEnderEnchantment.teleportTo(livingEntity, d, e, f)) break;
                        }
                        livingEntity.damage(DamageSource.MAGIC, 1);
                    }
                } else {
                    livingEntity.world.addParticle(ParticleTypes.PORTAL, livingEntity.getParticleX(0.5),
                            livingEntity.getRandomBodyY() - 0.1, livingEntity.getParticleZ(0.5),
                            (livingEntity.getRandom().nextDouble() - 0.5) * 2.0, -livingEntity.getRandom().nextDouble(),
                            (livingEntity.getRandom().nextDouble() - 0.5) * 2.0);
                }
            }
        });

        LivingEntityEvents.ON_BLOCK_EVENT.register((source, entity) -> {
            if (!entity.world.isClient) {
                Entity source2 = source.getSource();
                ItemStack shield = entity.getActiveItem();
                if (source2 instanceof LivingEntity livingAttacker) {
                    if (EnchantmentHelper.getLevel(ModEnchantments.BASHING, shield) > 0) {
                        BashingEnchantment.bash(entity, livingAttacker);
                    }
                    if (EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, livingAttacker.getMainHandStack()) > 0) {
                        entity.getActiveItem().setDamage(entity.getActiveItem().getMaxDamage() - 1);
                        entity.getActiveItem().damage(50, entity, e -> e.sendToolBreakStatus(entity.getActiveHand()));
                        entity.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8f, 0.8f + entity.world.random.nextFloat() * 0.4f);
                        Random random = entity.world.getRandom();
                        for (int i = 0; i < 10; ++i) {
                            double d = random.nextGaussian() * 0.02;
                            double e = random.nextGaussian() * 0.02;
                            double f = random.nextGaussian() * 0.02;
                            ((ServerWorld) entity.world).spawnParticles(ParticleTypes.LARGE_SMOKE, entity.getParticleX(1.0), entity.getRandomBodyY(), entity.getParticleZ(1.0), 1, d, e, f, 0.0);
                        }
                    }
                }
            }
        });

        LivingEntityEvents.ON_MOVEMENT_EFFECTS_EVENT.register((entity, blockPos) -> {
            if (!entity.world.isClient) {
                int i;
                if ((i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.MOLTEN_WALKER, entity)) > 0) {
                    MoltenWalkerEnchantment.hardenLava(entity, entity.world, blockPos, i);
                }
            }
        });

        // Is triggered before the damage is actually applied
        LivingEntityEvents.ON_ATTACK_EVENT.register((target, attacker) -> {
            if (!target.world.isClient && !(target instanceof PlayerEntity player && player.getAbilities().creativeMode)) {
                int i;
                if ((i = EnchantmentHelper.getLevel(ModEnchantments.INANE_ASPECT, attacker.getMainHandStack())) > 0) {
                    ((IEntity) target).setInaneTicks(InaneAspectEnchantment.getMinInaneTicks(i));
                }
                if ((i = EnchantmentHelper.getLevel(ModEnchantments.FREEZING_ASPECT, attacker.getMainHandStack())) > 0) {
                    target.extinguish();
                    if (target.canFreeze()) {
                        target.setFrozenTicks(target.getMinFreezeDamageTicks() + 75 * i);
                    }
                    Random random = target.world.getRandom();
                    for (int x = 0; x < 20; ++x) {
                        double d = random.nextGaussian() * 0.02;
                        double e = random.nextGaussian() * 0.02;
                        double f = random.nextGaussian() * 0.02;
                        ((ServerWorld) target.world).spawnParticles(ParticleTypes.SNOWFLAKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, d, e, f, 0.0);
                    }
                }
                if ((i = EnchantmentHelper.getLevel(ModEnchantments.LEECHING_ASPECT, attacker.getMainHandStack())) > 0) {
                    attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, false, false, false));
                    attacker.heal(0.25f * i);
                    Random random = attacker.world.getRandom();
                    double d = random.nextGaussian() * 0.02;
                    double e = random.nextGaussian() * 0.02;
                    double f = random.nextGaussian() * 0.02;
                    ((ServerWorld) attacker.world).spawnParticles(ParticleTypes.HEART, attacker.getParticleX(1.0), attacker.getRandomBodyY(), attacker.getParticleZ(1.0), 1, d, e, f, 0.0);
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
