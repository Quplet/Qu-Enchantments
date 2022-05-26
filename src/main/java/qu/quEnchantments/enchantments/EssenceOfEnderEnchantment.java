package qu.quEnchantments.enchantments;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class EssenceOfEnderEnchantment extends CorruptedEnchantment {
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
    public int getMaxLevel() {
        return 3;
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
        if (attacker != null) {
            if (attacker instanceof LivingEntity livingEntity && user.getRandom().nextFloat() < 0.25f + 0.05f * level) {
                for (int i = 0; i < 4; i++) {
                    double d = attacker.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    double e = attacker.getY() + (double) (user.getRandom().nextInt(32) - 16);
                    double f = attacker.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    if (teleportTo(livingEntity, d, e, f)) break;
                }
            }
        }
    }

    // Functionally equivalent to the Enderman's teleport
    public static boolean teleportTo(LivingEntity subject, double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > subject.world.getBottomY() && !subject.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = subject.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = subject.getPos();
        boolean bl3 = subject.teleport(x, y, z, true);
        if (bl3) {
            subject.world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(subject));
            if (!subject.isSilent()) {
                if (subject instanceof PlayerEntity) {
                    subject.world.playSound((PlayerEntity) subject, subject.prevX, subject.prevY, subject.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, subject.getSoundCategory(), 1.0f, 1.0f);
                } else {
                    subject.world.playSound(null, subject.prevX, subject.prevY, subject.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, subject.getSoundCategory(), 1.0f, 1.0f);
                }
                subject.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return bl3;
    }
}
