package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.enchantments.AggressionBlessingEnchantment;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.SpeedBlessingEnchantment;
import qu.quEnchantments.items.RuneItem;
import qu.quEnchantments.util.IEntity;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Inject(at = @At("TAIL"), method = "applyMovementEffects")
    private void quEnchantments$onApplyMovementEffects(BlockPos pos, CallbackInfo info) {
        LivingEntityEvents.ON_MOVEMENT_EFFECTS_EVENT.invoker().onAffect((LivingEntity) (Object) this, pos);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void quEnchantments$onTick(CallbackInfo info) {
        LivingEntityEvents.ON_TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"), method = "damage")
    private void quEnchantments$isBlocked(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityEvents.ON_BLOCK_EVENT.invoker().onBlock(source, (LivingEntity) (Object) this);
    }

    @Inject(at = @At(value = "HEAD"), method = "tickMovement")
    private void quEnchantments$applySpeedBlessing(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!entity.world.isClient) {
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (instance == null) return;
            boolean boost = false;
            for (Hand hand : Hand.values()) {
                ItemStack stack = this.getStackInHand(hand);
                if (EnchantmentHelper.getLevel(ModEnchantments.SPEED_BLESSING, stack) > 0 && stack.getDamage() < stack.getMaxDamage()) {
                    boost = true;
                }
            }
            if (instance.getModifier(SpeedBlessingEnchantment.BLESSING_BOOST.getId()) != null) {
                instance.removeModifier(SpeedBlessingEnchantment.BLESSING_BOOST);
            }
            if (boost && ((IEntity)entity).getInaneTicks() <= 0) {
                instance.addTemporaryModifier(SpeedBlessingEnchantment.BLESSING_BOOST);
            }
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getFrozenTicks()I"))
    private void quEnchantments$tickInaneReduction(CallbackInfo ci) {
        int m = this.getInaneTicks();
        this.setInaneTicks(Math.max(0, m - 1));
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/AttributeContainer;addTemporaryModifiers(Lcom/google/common/collect/Multimap;)V", shift = At.Shift.AFTER), method = "getEquipmentChanges")
    private void quEnchantments$onEquipmentChanges(CallbackInfoReturnable<@Nullable Map<EquipmentSlot, ItemStack>> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!entity.world.isClient) {
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
            if (instance == null) return;
            boolean aggression = false;
            for (Hand hand : Hand.values()) {
                ItemStack stack = this.getStackInHand(hand);
                if (EnchantmentHelper.getLevel(ModEnchantments.AGGRESSION_BLESSING, stack) > 0 && stack.getDamage() < stack.getMaxDamage()) {
                    aggression = true;
                }
            }
            if (instance.getModifier(AggressionBlessingEnchantment.ATTACK_BOOST.getId()) != null) {
                instance.removeModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
            }
            if (aggression && ((IEntity)entity).getInaneTicks() <= 0) {
                instance.addTemporaryModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
            }
        }
    }

    @Inject(method = "getPreferredEquipmentSlot", at = @At("TAIL"), cancellable = true)
    private static void quEnchantments$runePreferredSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.getItem() instanceof RuneItem) {
            cir.setReturnValue(EquipmentSlot.OFFHAND);
        }
    }
}
