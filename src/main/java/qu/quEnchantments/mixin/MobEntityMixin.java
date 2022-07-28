package qu.quEnchantments.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.NightbloodEnchantment;

import java.util.Collections;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    @Inject(method = "getArmorItems", at = @At("TAIL"), cancellable = true)
    private void quEnchantments$horseCondition(CallbackInfoReturnable<Iterable<ItemStack>> cir) {
        MobEntity entity = (MobEntity)(Object)this;
        if (entity instanceof HorseEntity) cir.setReturnValue(Collections.singleton(entity.getEquippedStack(EquipmentSlot.CHEST)));
    }

    @Inject(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I"))
    private void quEnchantments$triggerOnAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityEvents.ON_ATTACK_EVENT.invoker().onAttack(target, this);
    }

    @ModifyVariable(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I"), ordinal = 0)
    private float quEnchantments$modifyDamageVariableForNightblood(float value, Entity target) {
        if (EnchantmentHelper.getLevel(ModEnchantments.NIGHTBLOOD, this.getMainHandStack()) > 0) {
            value += NightbloodEnchantment.calculateDamage(target, this);
        }
        return value;
    }

    // Ignore
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
}
