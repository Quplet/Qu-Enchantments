package qu.quEnchantments.component;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.enchantment.effect.TargetedEnchantmentEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModEnchantmentEffectComponentTypes {

    public static ComponentType<List<TargetedEnchantmentEffect<EnchantmentEntityEffect>>> ON_BLOCK = register("on_block", builder -> builder.codec(TargetedEnchantmentEffect.createPostAttackCodec(EnchantmentEntityEffect.CODEC, LootContextTypes.ENCHANTED_DAMAGE).listOf()));
    public static ComponentType<EnchantmentValueEffect> OMEN_IMMUNITY = register("omen_immunity", builder -> builder.codec(EnchantmentValueEffect.CODEC));
    public static ComponentType<EnchantmentValueEffect> FIDELITY = register("fidelity", builder -> builder.codec(EnchantmentValueEffect.CODEC));
    public static ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> PROJECTILE_ACCURACY = register("projectile_accuracy", builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf()));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Identifier.of(QuEnchantments.MOD_ID, id), builderOperator.apply(ComponentType.builder()).build());
    }
}
