package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;

import java.util.function.Predicate;

/**
 * An abstract enchantment designed to stack to high levels.
 */
public abstract class CompoundEnchantment extends QuEnchantment {

    public CompoundEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 100;
    }

    @Override
    public int getMinPower(int level) {
        return level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 5;
    }

    @Override
    public Text getName(int level) {
        MutableText mutableText = (MutableText) super.getName(level);
        int rg = Math.max(0, 169 - (int)(level * 1.69));
        int b = Math.min(255, 167 + level);
        mutableText.styled(style -> style.withColor(rg << 16 | rg << 8 | b));
        if (level >= 50) mutableText.formatted(Formatting.BOLD);
        if (level >= 90) mutableText.formatted(Formatting.ITALIC);
        return mutableText;
    }

    public boolean passed(int luck, Random random, Predicate<Double> predicate) {
        boolean result = predicate.test(random.nextDouble());
        for (int i = 0; i < Math.abs(luck); i++) {
            if (luck > 0 && result) return true;
            if (luck < 0 && !result) return false;
            result = predicate.test(random.nextDouble());
        }
        return result;
    }

    protected int getLuck(LivingEntity livingEntity) {
        int luck = 0;
        StatusEffectInstance statusEffect;
        if ((statusEffect = livingEntity.getStatusEffect(StatusEffects.LUCK)) != null) luck += statusEffect.getAmplifier();
        if ((statusEffect = livingEntity.getStatusEffect(StatusEffects.UNLUCK)) != null) luck -= statusEffect.getAmplifier();
        return luck;
    }
}
