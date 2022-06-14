package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
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
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.items.RuneItem;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At("TAIL"), method = "applyMovementEffects")
    private void onApplyMovementEffects(BlockPos pos, CallbackInfo info) {
        LivingEntityEvents.ON_MOVEMENT_EFFECTS_EVENT.invoker().onAffect((LivingEntity) (Object) this, pos);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        LivingEntityEvents.ON_TICK_EVENT.invoker().onTick((LivingEntity) (Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"), method = "damage")
    private void isBlocked(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityEvents.ON_BLOCK_EVENT.invoker().onBlock(source, (LivingEntity) (Object) this);
    }

    @Inject(at = @At(value = "HEAD"), method = "tickMovement")
    private void onTickMovement(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!entity.world.isClient) {
            boolean boost = false;
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (instance == null) return;
            ItemStack stack;
            if (((stack = entity.getMainHandStack()).getDamage() < stack.getMaxDamage() && stack.getItem() instanceof RuneItem && EnchantmentHelper.getLevel(ModEnchantments.SPEED_BLESSING, stack) > 0)
                    || ((stack = entity.getOffHandStack()).getDamage() < stack.getMaxDamage() && stack.getItem() instanceof RuneItem && EnchantmentHelper.getLevel(ModEnchantments.SPEED_BLESSING, stack) > 0)) {
                boost = true;
            }
            if (instance.getModifier(SpeedBlessingEnchantment.BLESSING_BOOST.getId()) != null) {
                instance.removeModifier(SpeedBlessingEnchantment.BLESSING_BOOST);
            }
            if (boost) {
                instance.addTemporaryModifier(SpeedBlessingEnchantment.BLESSING_BOOST);
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/AttributeContainer;addTemporaryModifiers(Lcom/google/common/collect/Multimap;)V", shift = At.Shift.AFTER), method = "getEquipmentChanges")
    private void onEquipmentChanges(CallbackInfoReturnable<@Nullable Map<EquipmentSlot, ItemStack>> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!entity.world.isClient) {
            boolean aggression = false;
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
            if (instance == null) return;
            ItemStack stack;
            if (((stack = entity.getMainHandStack()).getDamage() < stack.getMaxDamage() && stack.getItem() instanceof RuneItem && EnchantmentHelper.getLevel(ModEnchantments.AGGRESSION_BLESSING, stack) > 0)
                    || ((stack = entity.getOffHandStack()).getDamage() < stack.getMaxDamage() && stack.getItem() instanceof RuneItem && EnchantmentHelper.getLevel(ModEnchantments.AGGRESSION_BLESSING, stack) > 0)) {
                aggression = true;
            }
            if (instance.getModifier(AggressionBlessingEnchantment.ATTACK_BOOST.getId()) != null) {
                instance.removeModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
            }
            if (aggression) {
                instance.addTemporaryModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
            }
        }
    }

    @Inject(method = "getPreferredEquipmentSlot", at = @At("TAIL"), cancellable = true)
    private static void runePreferredSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.getItem() instanceof RuneItem) {
            cir.setReturnValue(EquipmentSlot.OFFHAND);
        }
    }
}
