package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import qu.quEnchantments.util.interfaces.IEnchantment;

public class QuEnchantmentHelper {

//    public static float getAttackDamage(ItemStack weapon, Entity target) {
//        MutableFloat mutableFloat = new MutableFloat();
//        forEachQuEnchantment(((enchantment, stack, level) -> mutableFloat.add(enchantment.getAttackDamage(target, weapon, level))), weapon);
//        return mutableFloat.floatValue();
//    }
//
//    public static void onTargetDamaged(LivingEntity user, ItemStack weapon, Entity target) {
//        Consumer consumer = (enchantment, stack, level) -> enchantment.onTargetDamaged(user, stack, target, level);
//        if (user != null) forEachQuEnchantment(consumer, weapon);
//    }
//
//    public static void onBlock(LivingEntity defender, LivingEntity attacker) {
//        forEachQuEnchantment((enchantment, stack, level) -> enchantment.onBlock(defender, attacker, attacker.getMainHandStack(), level), defender.getActiveItem());
//    }
//
//    // This is called regardless of if the entity is a player in creative mode or not.
//    public static void onBlockBroken(PlayerEntity player, BlockPos pos) {
//        forEachQuEnchantment((enchantment, stack, level) -> enchantment.onBlockBreak(player, pos, stack, level), player.getMainHandStack());
//    }
//
//    /*
//     * This is called after tick, and does not take into account whether the enchantment was ticked from there.
//     * If you use both, both will be ticked, assuming the enchanted item is equipped.
//     */
//    public static void tickWhileEquipped(LivingEntity entity) {
//        List<QuEnchantment> ticked = new ArrayList<>();
//
//        forEachQuEnchantment((enchantment, stack, level) -> {
//            if (!enchantment.getEquipment(entity).containsValue(stack) || ticked.contains(enchantment)) return;
//            ticked.add(enchantment);
//            enchantment.tickWhileEquipped(entity, stack, level);
//        }, entity.getEquippedItems());
//
//    }
//
//    public static void tickEquippedWhileMoving(LivingEntity entity, BlockPos pos) {
//        List<QuEnchantment> ticked = new ArrayList<>();
//        forEachQuEnchantment(((enchantment, stack, level) -> {
//            if (!enchantment.getEquipment(entity).containsValue(stack) || ticked.contains(enchantment)) return;
//            ticked.add(enchantment);
//            enchantment.tickEquippedWhileMoving(entity, pos, stack, level);
//        }), entity.getEquippedItems());
//    }
//
//    public static void tick(LivingEntity holder, Iterable<ItemStack> stacks) {
//        List<QuEnchantment> ticked = new ArrayList<>();
//        forEachQuEnchantment((enchantment, stack, level) -> {
//            enchantment.tickAlways(holder, stack, level);
//            if (ticked.contains(enchantment)) return;
//            ticked.add(enchantment);
//            enchantment.tickOnce(holder, stack, level);
//        }, stacks);
//    }
//
//    private static void forEachQuEnchantment(Consumer consumer, ItemStack stack) {
//        if (stack == null || stack.isEmpty()) return;
//        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
//        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentsMap()) {
//            Enchantment enchantment = entry.getKey().value();
//            if (enchantment instanceof QuEnchantment quEnchantment) {
//                consumer.accept(quEnchantment, stack, entry.getIntValue());
//            }
//        }
//    }
//
//    private static void forEachQuEnchantment(Consumer consumer, Iterable<ItemStack> stacks) {
//        for (ItemStack stack : stacks) {
//            forEachQuEnchantment(consumer, stack);
//        }
//    }

    public static void onTargetBlockDamage(ServerWorld world, LivingEntity target, DamageSource damageSource) {
        EnchantmentHelper.forEachEnchantment(target, (RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> ((IEnchantment)(Object)enchantment.value()).qu_Enchantments$onTargetBlockDamage(world, level, context, EnchantmentEffectTarget.VICTIM, target, damageSource));
    }

    public static boolean hasOmenImmunity(LivingEntity user) {
        MutableFloat ret = new MutableFloat();
        EnchantmentHelper.forEachEnchantment(user, (enchantment, level, context) -> ((IEnchantment)(Object)enchantment.value()).qu_Enchantments$modifyImmunity(user, level, ret));

        return ret.floatValue() > 0.0f;
    }

    public static int getAccuracyLevel(ItemStack stack, LivingEntity user) {
        MutableInt ret = new MutableInt();
        EnchantmentHelper.forEachEnchantment(user, (enchantment, level, context) -> enchantment.getKey().ifPresent(key -> {
            if (key == ModEnchantments.ACCURACY && level > ret.getValue()) {
                ret.setValue(level);
            }
        }));

        return ret.getValue();
    }

    public static float getProjectileDivergence(float base, ServerWorld world, ItemStack stack) {
        MutableFloat ret = new MutableFloat(base);
        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {});
        return ret.floatValue();
    }
}
