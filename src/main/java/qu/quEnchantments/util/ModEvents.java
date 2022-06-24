package qu.quEnchantments.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import qu.quEnchantments.callbacks.EntityEvents;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.*;
import qu.quEnchantments.entity.ai.goals.FidelityFollowOwnerGoal;
import qu.quEnchantments.mixin.MobEntityAccessor;
import qu.quEnchantments.world.ModWorldEvents;
import qu.quEnchantments.callbacks.AnvilEvents;

public class ModEvents {

    public static void RegisterModEvents() {

        AnvilEvents.ANVIL_USED.register((player, stack, handler) -> {
            // Logic for Shaped Glass enchantment. Causes item to break upon anvil use.
            if (!player.getAbilities().creativeMode) {
                if ((handler.getSlot(0).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(0).getStack()) > 0) ||
                        (handler.getSlot(1).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(1).getStack()) > 0)) {
                    stack.setDamage(stack.getMaxDamage() - 1);
                    stack.damage(50, player, Entity::toString);
                    player.world.syncWorldEvent(ModWorldEvents.SHAPED_GLASS_BREAK, player.getBlockPos(), 0);
                }
            }
        });

        AnvilEvents.ANVIL_UPDATE.register(handler -> CorruptedEnchantment.corruptEnchantments(handler.getSlot(2).getStack()));

        LivingEntityEvents.ON_TICK_EVENT.register(livingEntity -> {
            if (!livingEntity.world.isClient) {
                if (livingEntity instanceof PlayerEntity player) {
                    for (ItemStack stack : player.getInventory().main) {
                        CorruptedEnchantment.corruptEnchantments(stack);
                    }
                    int i;
                    if (player.world.getRegistryKey() != World.NETHER && player.isSneaking() && (i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.SKYWALKER, player)) > 0) {
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
                if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.OMEN_OF_IMMUNITY, livingEntity) > 0) {
                    livingEntity.extinguish();
                    livingEntity.setFrozenTicks(0);
                    livingEntity.clearStatusEffects();
                }
            }
            if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.ESSENCE_OF_ENDER, livingEntity) > 0) {
                if (!livingEntity.world.isClient) {
                    if (livingEntity.isWet() && livingEntity.getRandom().nextFloat() < 0.05f) {
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

        EntityEvents.ENTITY_JOIN_WORLD_EVENT.register((entity, world) -> {
            if (!world.isClient) {
                if (entity instanceof HorseEntity horse) {
                    ((MobEntityAccessor) horse).getGoalSelector().add(2, new FidelityFollowOwnerGoal(horse, 1.3, 5.0f, 10.0f, false));
                }
            }
        });
    }
}
