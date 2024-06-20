package qu.quEnchantments.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.effect.entity.BashingEnchantmentEffect;
import qu.quEnchantments.enchantments.effect.entity.FreezeEnchantmentEffect;
import qu.quEnchantments.enchantments.effect.entity.InaneEnchantmentEffect;
import qu.quEnchantments.enchantments.effect.entity.LeechingEnchantmentEffect;

public class ModEnchantmentEffects {

    private static void registerEntityEffect(String id, MapCodec<? extends EnchantmentEntityEffect> codec) {
        registerLocationBasedEffect(id, codec);
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(QuEnchantments.MOD_ID, id), codec);
    }

    private static void registerLocationBasedEffect(String id, MapCodec<? extends EnchantmentLocationBasedEffect> codec) {
        Registry.register(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, Identifier.of(QuEnchantments.MOD_ID, id), codec);
    }

    public static void registerEnchantmentEffects() {
        registerEntityEffect("freeze_effect", FreezeEnchantmentEffect.CODEC);
        registerEntityEffect("inane_effect", InaneEnchantmentEffect.CODEC);
        registerEntityEffect("leeching_effect", LeechingEnchantmentEffect.CODEC);
        registerEntityEffect("bashing_effect", BashingEnchantmentEffect.CODEC);

        QuEnchantments.LOGGER.info("Registering mod enchantment effects");
    }
}
