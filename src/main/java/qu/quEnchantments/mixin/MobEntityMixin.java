package qu.quEnchantments.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "getArmorItems", at = @At("TAIL"), cancellable = true)
    private void horseCondition(CallbackInfoReturnable<Iterable<ItemStack>> cir) {
        MobEntity entity = (MobEntity)(Object)this;
        if (entity instanceof HorseEntity) cir.setReturnValue(Collections.singleton(entity.getEquippedStack(EquipmentSlot.CHEST)));
    }
}
