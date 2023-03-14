package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.ModTags;
import qu.quEnchantments.util.config.ModConfig;

import java.util.Optional;

public class NightbloodEnchantment extends CorruptedEnchantment {

    private static final ModConfig.NightbloodOptions CONFIG = QuEnchantments.getConfig().nightbloodOptions;

    public NightbloodEnchantment(EnchantmentType enchantmentType, Rarity weight, EquipmentSlot... slotTypes) {
        super(enchantmentType, weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 2 : 0;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public float getAttackDamage(Entity target, ItemStack stack, int level) {
        if (!target.world.isClient) {
            Random random = target.world.getRandom();
            for (int x = 0; x < 25; x++) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                ((ServerWorld) target.world).spawnParticles(ParticleTypes.LARGE_SMOKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, d, e, f, 0.0);
            }
            Optional<RegistryKey<EntityType<?>>> key;
            Optional<RegistryEntry.Reference<EntityType<?>>> entry;
            if ((key = Registries.ENTITY_TYPE.getKey(target.getType())).isPresent() &&
                    (entry = Registries.ENTITY_TYPE.getEntry(key.get())).isPresent() &&
                    !entry.get().isIn(ModTags.NIGHTBLOOD_IMMUNE_ENTITIES)) {
                if (target instanceof LivingEntity livingEntity) {
                    if (EnchantmentHelper.getEquipmentLevel(ModEnchantments.OMEN_OF_IMMUNITY, livingEntity) > 0) return 0.0f;
                    if (CONFIG.disablesExperience) livingEntity.disableExperienceDropping();
                }
                return 1000000.0f;
            } else if (target instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, CONFIG.witherDuration, CONFIG.witherAmplifier, false, true), livingEntity.getAttacker());
            }
        }
        return 0.0f;
    }

    @Override
    public void tickWhileEquipped(LivingEntity wearer, ItemStack stack, int level) {
        World world = wearer.world;
        if (world.isClient) return;
        if (wearer instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            if (player.experienceLevel > 0 || player.experienceProgress > 0) {
                player.addExperience((int) (-4 * CONFIG.drainRate / level));
                return;
            }
            if (world.getDifficulty().getId() != 0 && player.getHungerManager().getFoodLevel() > 0) {
                player.getHungerManager().addExhaustion(1.5f * CONFIG.drainRate / level);
                return;
            }
        }
        wearer.damage(world.getDamageSources().magic(), 2.0f * CONFIG.drainRate / level);
    }
}
