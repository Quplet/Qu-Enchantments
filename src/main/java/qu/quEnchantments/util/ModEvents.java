package qu.quEnchantments.util;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import qu.quEnchantments.enchantments.*;
import qu.quEnchantments.world.ModWorldEvents;
import qu.quEnchantments.callbacks.AnvilEvents;
import qu.quEnchantments.callbacks.ApplyMovementEffectsCallback;
import qu.quEnchantments.callbacks.LivingEntityTickCallback;
import qu.quEnchantments.callbacks.MobAttackCallback;

public class ModEvents {

    public static void RegisterModEvents() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!player.world.isClient) {
                if (!player.isSpectator() && entity instanceof LivingEntity) {
                    // Player logic for Freezing Aspect enchantment
                    int freeze = EnchantmentHelper.getLevel(ModEnchantments.FREEZING_ASPECT, player.getMainHandStack());
                    if (freeze > 0) FreezingAspectEnchantment.freeze((LivingEntity) entity, freeze);
                    // Player logic for Leeching Aspect enchantment
                    int leech = EnchantmentHelper.getLevel(ModEnchantments.LEECHING_ASPECT, player.getMainHandStack());
                    if (leech > 0) LeechingAspectEnchantment.leech(player, leech);
                }
            }
            return ActionResult.PASS;
        });

        MobAttackCallback.EVENT.register((user, target) -> {
            if (!user.world.isClient) {
                if (target instanceof LivingEntity) {
                    // Mob logic for Freezing Aspect enchantment
                    int freeze = EnchantmentHelper.getLevel(ModEnchantments.FREEZING_ASPECT, user.getMainHandStack());
                    if (freeze > 0) FreezingAspectEnchantment.freeze((LivingEntity) target, freeze);
                    // Mob logic for Leeching Aspect enchantment
                    int leech = EnchantmentHelper.getLevel(ModEnchantments.LEECHING_ASPECT, user.getMainHandStack());
                    if (leech > 0) LeechingAspectEnchantment.leech(user, leech);
                }
            }
            return ActionResult.PASS;
        });

        AnvilEvents.ANVIL_USED.register((player, stack, handler) -> {
            // Logic for Shaped Glass enchantment. Causes item to break upon anvil use.
            if (!player.getAbilities().creativeMode) {
                if ((handler.getSlot(0).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(0).getStack()) > 0) ||
                        (handler.getSlot(1).getStack().getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, handler.getSlot(1).getStack()) > 0)) {
                    stack.damage(Integer.MAX_VALUE, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    player.world.syncWorldEvent(ModWorldEvents.SHAPED_GLASS_BREAK, player.getBlockPos(), 0);
                }
            }
        });

        AnvilEvents.ANVIL_UPDATE.register(handler -> CorruptedEnchantment.corruptEnchantments(handler.getSlot(2).getStack()));

        LivingEntityTickCallback.EVENT.register(livingEntity -> {
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
                if ((i = EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, livingEntity.getMainHandStack())) > 0) {
                    NightbloodEnchantment.drain(livingEntity, i);
                }
            }
        });

        ApplyMovementEffectsCallback.EVENT.register((entity, blockPos) -> {
            int i;
            if ((i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.MOLTEN_WALKER, entity)) > 0) {
                MoltenWalkerEnchantment.hardenLava(entity, entity.world, blockPos, i);
            }
        });


    }
}
