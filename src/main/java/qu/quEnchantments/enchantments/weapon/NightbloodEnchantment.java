package qu.quEnchantments.enchantments.weapon;

import com.google.common.collect.ImmutableMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import java.util.ArrayList;
import java.util.Collections;

public class NightbloodEnchantment extends CorruptedEnchantment {

    private static final ModConfig.NightbloodOptions CONFIG = QuEnchantments.getConfig().nightbloodOptions;
    private static final ImmutableMap<ArmorItem.Type, Float> ARMOR_CHANCE_MAP = ImmutableMap.of(
            ArmorItem.Type.HELMET, 0.2f,
            ArmorItem.Type.CHESTPLATE, 0.4f,
            ArmorItem.Type.LEGGINGS, 0.3f,
            ArmorItem.Type.BOOTS, 0.1f
    );

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
        World world;
        if ((world = target.getWorld()).isClient ||
                !(target instanceof LivingEntity livingEntity) ||
                EnchantmentHelper.getEquipmentLevel(ModEnchantments.OMEN_OF_IMMUNITY, livingEntity) > 0) return 0.0f;

        Random random = world.getRandom();
        for (int x = 0; x < 25; x++) {
            double deltaX = random.nextGaussian() * 0.02;
            double deltaY = random.nextGaussian() * 0.02;
            double deltaZ = random.nextGaussian() * 0.02;
            ((ServerWorld) world).spawnParticles(ParticleTypes.LARGE_SMOKE, target.getParticleX(1.0), target.getRandomBodyY(), target.getParticleZ(1.0), 1, deltaX, deltaY, deltaZ, 0.0);
        }

        Optional<RegistryKey<EntityType<?>>> key;
        Optional<RegistryEntry.Reference<EntityType<?>>> entry;
        if ((key = Registries.ENTITY_TYPE.getKey(target.getType())).isPresent() &&
                (entry = Registries.ENTITY_TYPE.getEntry(key.get())).isPresent() &&
                entry.get().isIn(ModTags.NIGHTBLOOD_IMMUNE_ENTITIES)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, CONFIG.witherDuration, CONFIG.witherAmplifier, false, true), livingEntity.getAttacker());
            return 0.0f;
        }

        ArrayList<ItemStack> armorItems = new ArrayList<>();
        livingEntity.getArmorItems().forEach(armorItems::add);
        ArrayList<ItemStack> shuffledArmorItems = new ArrayList<>(armorItems);
        Collections.shuffle(shuffledArmorItems);
        for (ItemStack armorItemStack : shuffledArmorItems) {
            if (armorItemStack == ItemStack.EMPTY || armorItemStack.isOf(Items.AIR)) continue;
            ArmorItem.Type armorItemType = ((ArmorItem) armorItemStack.getItem()).getType();
            //noinspection DataFlowIssue
            if (random.nextFloat() < ARMOR_CHANCE_MAP.get(armorItemType)) {
                armorItemStack.damage(1000000, livingEntity, livingEntityLambda -> livingEntityLambda.sendEquipmentBreakStatus(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, armorItems.indexOf(armorItemStack))));
                return 0.0f;
            }
        }

        if (CONFIG.disablesExperience) livingEntity.disableExperienceDropping();
        return 1000000.0f;
    }

    @Override
    public void tickWhileEquipped(LivingEntity wearer, ItemStack stack, int level) {
        World world;
        if ((world = wearer.getWorld()).isClient) return;

        if (wearer instanceof PlayerEntity player) {
            if (player.getAbilities().creativeMode) return;

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
