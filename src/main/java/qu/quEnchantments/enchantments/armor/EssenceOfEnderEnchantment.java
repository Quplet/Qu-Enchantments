package qu.quEnchantments.enchantments.armor;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class EssenceOfEnderEnchantment extends CorruptedEnchantment {

    private static final ModConfig.EssenceOfEnderOptions CONFIG = QuEnchantments.getConfig().essenceOfEnderOptions;

    public EssenceOfEnderEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
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
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 3 : 0;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem) {
            return true;
        }
        return super.isAcceptableItem(stack);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (user.getWorld().isClient || !(attacker instanceof LivingEntity livingEntity) || (attacker instanceof PlayerEntity player && player.getAbilities().creativeMode)) return;

        for (int i = 0; i < 7; i++) {
            double d = attacker.getX() + (user.getRandom().nextDouble() * clampEither(-0.5, 0.5, attacker.getX() - user.getX())) * CONFIG.entityTeleportDistance * level;
            double e = attacker.getY() + (double) (user.getRandom().nextInt(CONFIG.entityTeleportDistance * 2 * level) - (CONFIG.entityTeleportDistance * level));
            double f = attacker.getZ() + (user.getRandom().nextDouble() * clampEither(-0.5, 0.5, attacker.getZ() - user.getZ())) * CONFIG.entityTeleportDistance * level;
            if (teleportTo(livingEntity, d, e, f)) break;
        }
    }

    @Override
    public void tickWhileEquipped(LivingEntity entity, ItemStack stack, int level) {
        World world = entity.getWorld();
        if (world.isClient) {
            world.addParticle(ParticleTypes.PORTAL, entity.getParticleX(0.5), entity.getRandomBodyY() - 0.1, entity.getParticleZ(0.5), (entity.getRandom().nextDouble() - 0.5) * 2.0, -entity.getRandom().nextDouble(), (entity.getRandom().nextDouble() - 0.5) * 2.0);
            return;
        }

        if (entity.isWet() && entity.getRandom().nextFloat() < 0.05f && !(entity instanceof PlayerEntity player && player.getAbilities().creativeMode)) {
            entity.removeAllPassengers();
            for (int j = 0; j < 5; j++) {
                double d = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                double e = entity.getY() + (double) (entity.getRandom().nextInt(32) - 16);
                double f = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                if (EssenceOfEnderEnchantment.teleportTo(entity, d, e, f)) break;
            }
            entity.damage(world.getDamageSources().magic(), 1);
        }
    }

    private static double clampEither(double min, double max, double value) {
        if (value < min) return min;
        if (value > max) return max;
        if (Math.abs(value - min) > Math.abs(value - max)) return max;
        else return min;
    }

    // Functionally equivalent to the Enderman's teleport
    public static boolean teleportTo(LivingEntity subject, double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > subject.getWorld().getBottomY() && !subject.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = subject.getWorld().getBlockState(mutable);
        if (!blockState.blocksMovement() || blockState.getFluidState().isIn(FluidTags.WATER)) return false;

        Vec3d vec3d = subject.getPos();
        boolean teleportSucceeded = subject.teleport(x, y, z, true);
        if (teleportSucceeded) {
            subject.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(subject));
            if (!subject.isSilent()) {
                subject.getWorld().playSound(subject instanceof PlayerEntity player ? player : null, subject.prevX, subject.prevY, subject.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, subject.getSoundCategory(), 1.0f, 1.0f);
                subject.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return teleportSucceeded;
    }
}
