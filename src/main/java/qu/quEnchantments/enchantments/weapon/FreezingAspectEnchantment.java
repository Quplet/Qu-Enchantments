package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class FreezingAspectEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public FreezingAspectEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other == Enchantments.FIRE_ASPECT || other instanceof InaneAspectEnchantment || other instanceof LeechingAspectEnchantment) && super.canAccept(other);
    }

//    @Override
//    public int getMaxLevel() {
//        return CONFIG.isEnabled ? 2 : 0;
//    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.freezingAspectRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.freezingAspectBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.freezingAspectEnchantingTable;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {
        World world;
        if ((world = user.getWorld()).isClient) return;

        target.extinguish();
        if (target.canFreeze()) {
            target.setFrozenTicks(target.getMinFreezeDamageTicks() + CONFIG.freezingAspectDuration * level);
        }

        Random random = world.getRandom();
        for (int x = 0; x < 20; ++x) {
            double d = random.nextGaussian() * 0.02;
            double e = random.nextGaussian() * 0.02;
            double f = random.nextGaussian() * 0.02;
            ((ServerWorld) world).spawnParticles(ParticleTypes.SNOWFLAKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, d, e, f, 0.0);
        }
    }
}
