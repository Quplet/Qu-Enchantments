package qu.quEnchantments.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class LeechingAspectEnchantment extends FireAspectEnchantment {
    public LeechingAspectEnchantment(Rarity weight, EquipmentSlot ... slotTypes) {
        super(weight, slotTypes);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof FireAspectEnchantment) && super.canAccept(other);
    }
}
