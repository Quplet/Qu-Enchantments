package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import qu.quEnchantments.util.interfaces.IEnchantment;

public class QuEnchantmentHelper {

    public static void onTargetBlockDamage(ServerWorld world, LivingEntity target, DamageSource damageSource) {
        EnchantmentHelper.forEachEnchantment(target, (RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context) -> ((IEnchantment)(Object)enchantment.value()).qu_Enchantments$onTargetBlockDamage(world, level, context, EnchantmentEffectTarget.VICTIM, target, damageSource));
    }

    public static int omenImmunityLevel(LivingEntity user) {
        MutableFloat ret = new MutableFloat();
        EnchantmentHelper.forEachEnchantment(user, (enchantment, level, context) -> ((IEnchantment)(Object)enchantment.value()).qu_Enchantments$modifyImmunity(user, level, ret));

        return MathHelper.floor(ret.floatValue());
    }

    public static boolean hasFidelity(LivingEntity user) {
        MutableFloat mutableFloat = new MutableFloat();
        EnchantmentHelper.forEachEnchantment(user, (enchantment, level, context) -> ((IEnchantment)(Object)enchantment.value()).qu_Enchantments$modifyFidelity(user, level, mutableFloat));

        return mutableFloat.floatValue() > 0.0f;
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

    public static boolean hasAnyEnchantmentsIn(LivingEntity entity, TagKey<Enchantment> tag) {
        boolean bl = false;

        for (ItemStack stack : entity.getEquippedItems()) {
            if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, tag)) bl = true;
        }

        return bl;
    }
}
