package qu.quEnchantments.mixin;

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
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;

import java.util.Collections;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    @Inject(method = "getArmorItems", at = @At("TAIL"), cancellable = true)
    private void quEnchantments$horseCondition(CallbackInfoReturnable<Iterable<ItemStack>> cir) {
        MobEntity entity = (MobEntity)(Object)this;
        if (entity instanceof HorseEntity) cir.setReturnValue(Collections.singleton(entity.getEquippedStack(EquipmentSlot.CHEST)));
    }

    @ModifyVariable(method = "tryAttack",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F")),
            at = @At(value = "STORE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"),
            ordinal = 0)
    private float quEnchantments$injectGetAttackDamage(float original, Entity target) {
        return original + QuEnchantmentHelper.getAttackDamage(this.getMainHandStack(), target);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    private void quEnchantments$injectTickEnchantments(CallbackInfo ci) {
        QuEnchantmentHelper.tick((MobEntity)(Object)this, this.getItemsEquipped());
        QuEnchantmentHelper.tickWhileEquipped((MobEntity)(Object)this);
    }

    // Ignore
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
}
