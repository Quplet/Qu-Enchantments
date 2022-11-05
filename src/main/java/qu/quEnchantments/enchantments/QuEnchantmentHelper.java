package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.ArrayList;
import java.util.List;

public class QuEnchantmentHelper {

    public static float getAttackDamage(ItemStack weapon, Entity target) {
        MutableFloat mutableFloat = new MutableFloat();
        forEachQuEnchantment(((enchantment, stack, level) -> mutableFloat.add(enchantment.getAttackDamage(target, weapon, level))), weapon);
        return mutableFloat.floatValue();
    }

    public static void onTargetDamaged(LivingEntity user, ItemStack weapon, Entity target) {
        Consumer consumer = (enchantment, stack, level) -> enchantment.onTargetDamaged(user, stack, target, level);
        if (user != null) forEachQuEnchantment(consumer, weapon);
    }

    public static void onBlock(LivingEntity defender, LivingEntity attacker) {
        forEachQuEnchantment((enchantment, stack, level) -> enchantment.onBlock(defender, attacker, attacker.getMainHandStack(), level), defender.getActiveItem());
    }

    // This is called regardless of if the entity is a player in creative mode or not.
    public static void onBlockBroken(PlayerEntity player, BlockPos pos) {
        forEachQuEnchantment((enchantment, stack, level) -> enchantment.onBlockBreak(player, pos, stack, level), player.getMainHandStack());
    }

    /*
     * This is called after tick, and does not take into account whether the enchantment was ticked from there.
     * If you use both, both will be ticked, assuming the enchanted item is equipped.
     */
    public static void tickWhileEquipped(LivingEntity entity) {
        List<QuEnchantment> ticked = new ArrayList<>();
        forEachQuEnchantment((enchantment, stack, level) -> {
            if (!enchantment.getEquipment(entity).containsValue(stack) || ticked.contains(enchantment)) return;
            ticked.add(enchantment);
            enchantment.tickWhileEquipped(entity, stack, level);
        }, entity.getItemsEquipped());
    }

    public static void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos) {
        List<QuEnchantment> ticked = new ArrayList<>();
        forEachQuEnchantment(((enchantment, stack, level) -> {
            if (!enchantment.getEquipment(entity).containsValue(stack) || ticked.contains(enchantment)) return;
            ticked.add(enchantment);
            enchantment.tickEquippedWhileMoving(entity, pos, stack, level);
        }), entity.getItemsEquipped());
    }

    public static void tick(LivingEntity holder, Iterable<ItemStack> stacks) {
        List<QuEnchantment> ticked = new ArrayList<>();
        forEachQuEnchantment((enchantment, stack, level) -> {
            enchantment.tickAlways(holder, stack, level);
            if (ticked.contains(enchantment)) return;
            ticked.add(enchantment);
            enchantment.tickOnce(holder, stack, level);
        }, stacks);
    }

    private static void forEachQuEnchantment(Consumer consumer, ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        NbtList nbtList = stack.getEnchantments();
        for (int i = 0; i < nbtList.size(); i++) {
            NbtCompound compound = nbtList.getCompound(i);
            Registry.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(compound)).ifPresent(enchantment -> {
                if (enchantment instanceof QuEnchantment) {
                    consumer.accept((QuEnchantment) enchantment, stack, EnchantmentHelper.getLevelFromNbt(compound));
                }
            });
        }
    }

    private static void forEachQuEnchantment(Consumer consumer, Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            forEachQuEnchantment(consumer, stack);
        }
    }

    @FunctionalInterface
    interface Consumer {
        void accept(QuEnchantment enchantment, ItemStack stack, int level);
    }
}
