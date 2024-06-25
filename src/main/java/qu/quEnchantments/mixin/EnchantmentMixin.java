package qu.quEnchantments.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.enchantment.effect.TargetedEnchantmentEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import qu.quEnchantments.component.ModEnchantmentEffectComponentTypes;
import qu.quEnchantments.util.ModTags;
import qu.quEnchantments.util.interfaces.IEnchantment;

import java.util.List;
import java.util.Random;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements IEnchantment {

    @Shadow
    public abstract <T> List<T> getEffect(ComponentType<List<T>> type);

    @Shadow
    public abstract void modifyValue(ComponentType<EnchantmentValueEffect> type, net.minecraft.util.math.random.Random random, int level, MutableFloat value);

    @Unique
    private static final Random random = new Random();

    @Override
    public void quEnchantments$onTargetBlockDamage(ServerWorld world, int level, EnchantmentEffectContext context, EnchantmentEffectTarget target, LivingEntity user, DamageSource damageSource) {
        for (TargetedEnchantmentEffect<EnchantmentEntityEffect> targetedEnchantmentEffect : getEffect(ModEnchantmentEffectComponentTypes.ON_BLOCK)) {
            if (target != targetedEnchantmentEffect.enchanted()) continue;
            System.out.println("Reached");
            Enchantment.applyTargetedEffect(targetedEnchantmentEffect, world, level, context, user, damageSource);
        }
    }

    @Override
    public void quEnchantments$modifyImmunity(LivingEntity user, int level, MutableFloat mutableFloat) {
        this.modifyValue(ModEnchantmentEffectComponentTypes.OMEN_IMMUNITY, user.getRandom(), level, mutableFloat);
    }

    @Override
    public void quEnchantments$modifyFidelity(LivingEntity user, int level, MutableFloat mutableFloat) {
        this.modifyValue(ModEnchantmentEffectComponentTypes.FIDELITY, user.getRandom(), level, mutableFloat);
    }

    @Override
    public void quEnchantments$modifyRegenerationBlessing(LivingEntity user, int level, MutableFloat mutableFloat) {
        this.modifyValue(ModEnchantmentEffectComponentTypes.REGENERATION_BLESSING, user.getRandom(), level, mutableFloat);
    }

    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    private static Text quEnchantments$modifyName(Text original, RegistryEntry<Enchantment> enchantment, int level) {
        if (!(original instanceof MutableText text)) return original;

        if (enchantment.isIn(ModTags.CORRUPTED)) {
            text.setStyle(Style.EMPTY.withColor(Formatting.LIGHT_PURPLE).withObfuscated(random.nextFloat() < 0.02f * level));
        } else if (enchantment.isIn(ModTags.COMPOUND)) {
            int rg = Math.max(0, 169 - (int)(level * 1.69));
            int b = Math.min(255, 167 + level);
            text.setStyle(Style.EMPTY.withColor(rg << 16 | rg << 8 | b).withBold(level >= 50).withItalic(level >= 90));
        }

        return text;
    }
}