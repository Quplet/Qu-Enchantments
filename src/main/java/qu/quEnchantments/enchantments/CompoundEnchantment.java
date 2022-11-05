package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
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
    public Text getName(int level) {
        MutableText mutableText = (MutableText) super.getName(level);
        int rg = Math.max(0, 169 - (int)(level * 1.69));
        int b = Math.min(255, 167 + level);
        mutableText.styled(style -> style.withColor(MathHelper.packRgb(rg, rg, b)));
        if (level >= 50) mutableText.formatted(Formatting.BOLD);
        if (level >= 90) mutableText.formatted(Formatting.ITALIC);
        return mutableText;
    }

    public boolean passed(int luck, Random random, Predicate<Double> predicate) {
        boolean result = false;
        for (int i = 0; i <= Math.abs(luck); i++) {
            result = predicate.test(random.nextDouble());
            if (luck > 0 && result) return true;
            if (luck < 0 && !result) return false;
        }
        return result;
    }
}
