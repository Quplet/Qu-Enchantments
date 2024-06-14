package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class LeechingAspectEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public LeechingAspectEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other == Enchantments.FIRE_ASPECT || other instanceof FreezingAspectEnchantment || other instanceof InaneAspectEnchantment) && super.canAccept(other);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.leechingAspectRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.leechingAspectBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.leechingAspectEnchantingTable;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {
        World world;
        if ((world = user.getWorld()).isClient) return;

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, false, false, false));
        user.heal(CONFIG.leechingAspectHealing * level);

        Random random = world.getRandom();
        double d = random.nextGaussian() * 0.02;
        double e = random.nextGaussian() * 0.02;
        double f = random.nextGaussian() * 0.02;
        ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, user.getParticleX(1.0), user.getRandomBodyY(), user.getParticleZ(1.0), 1, d, e, f, 0.0);
    }
}
