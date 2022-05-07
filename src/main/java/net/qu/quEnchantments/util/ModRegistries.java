package net.qu.quEnchantments.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypeFilter;
import net.qu.quEnchantments.AnvilUpdateResultCallback;
import net.qu.quEnchantments.AnvilUsedCallback;
import net.qu.quEnchantments.ApplyMovementEffectsCallback;
import net.qu.quEnchantments.MobAttackCallback;
import net.qu.quEnchantments.enchantments.*;

public class ModRegistries {

    public static void RegisterModEvents() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!player.isSpectator() && entity instanceof LivingEntity) {
                // Player logic for Freezing Aspect enchantment
                int freeze = EnchantmentHelper.getLevel(ModEnchantments.FREEZING_ASPECT, player.getMainHandStack());
                if (freeze > 0) FreezingAspectEnchantment.freeze((LivingEntity) entity, freeze);
                // Player logic for Leeching Aspect enchantment
                int leech = EnchantmentHelper.getLevel(ModEnchantments.LEECHING_ASPECT, player.getMainHandStack());
                if (leech > 0) LeechingAspectEnchantment.leech(player, leech);
                // Player logic for Nightblood enchantment
                int nightblood = EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, player.getMainHandStack());
                if (nightblood > 0) NightbloodEnchantment.onTargetHit(player, entity);
            }
            return ActionResult.PASS;
        });

        MobAttackCallback.EVENT.register((user, target) -> {
            if (target instanceof LivingEntity) {
                // Mob logic for Freezing Aspect enchantment
                int freeze = EnchantmentHelper.getLevel(ModEnchantments.FREEZING_ASPECT, user.getMainHandStack());
                if (freeze > 0) FreezingAspectEnchantment.freeze((LivingEntity) target, freeze);
                // Mob logic for Leeching Aspect enchantment
                int leech = EnchantmentHelper.getLevel(ModEnchantments.LEECHING_ASPECT, user.getMainHandStack());
                if (leech > 0) LeechingAspectEnchantment.leech(user, leech);
                // Mob logic for Nightblood enchantment
                int nightblood = EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, user.getMainHandStack());
                if (nightblood > 0) NightbloodEnchantment.onTargetHit(user, target);
            }
            return ActionResult.PASS;
        });

        AnvilUsedCallback.EVENT.register((player, stack, handler) -> {
            // Logic for Shaped Glass enchantment. Causes item to break upon anvil use.
            if (!player.getAbilities().creativeMode) {
                if ((handler.getSlot(0).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(0).getStack()) > 0) ||
                        (handler.getSlot(1).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(1).getStack()) > 0)) {
                    if (!player.world.isClient()) {
                        stack.damage(Integer.MAX_VALUE, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    }
                    player.world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 5.0f, 1.0f);
                }
            }

            return ActionResult.PASS;
        });

        AnvilUpdateResultCallback.EVENT.register((handler) -> {
            // Logic for Shaped Glass Enchantment
            ItemStack result = handler.getSlot(2).getStack();
            CorruptedEnchantment.corruptEnchantments(result);
            return ActionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                int i;
                if (!player.getAbilities().creativeMode && (i = EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, player.getMainHandStack())) > 0) {
                    NightbloodEnchantment.drain(player, i);
                }
            }

            for (ServerWorld world : server.getWorlds()) {
                for (MobEntity mob : world.getEntitiesByType(TypeFilter.instanceOf(MobEntity.class), MobEntity::canPickUpLoot)) {
                    int i;
                    if ((i = EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, mob.getMainHandStack())) > 0) {
                        NightbloodEnchantment.drain(mob, i);
                    }
                }
            }
        });

        ApplyMovementEffectsCallback.EVENT.register((entity, blockPos) -> {
            if (!entity.world.isClient) {
                int i;
                if ((i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.MOLTEN_WALKER, entity)) > 0) {
                    MoltenWalkerEnchantment.hardenLava(entity, entity.world, blockPos, i);
                }
            }
            return ActionResult.PASS;
        });
    }
}
