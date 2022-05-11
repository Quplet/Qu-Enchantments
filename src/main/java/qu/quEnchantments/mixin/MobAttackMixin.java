package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import qu.quEnchantments.callbacks.MobAttackCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobAttackMixin {
	@Inject(at = @At(value = "HEAD"), method = "tryAttack")
	private void onAttack(Entity target, CallbackInfoReturnable<Boolean> info) {
		MobAttackCallback.EVENT.invoker().interact((MobEntity) (Object) this, target);
	}
}
