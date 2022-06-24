package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.callbacks.EntityEvents;

@Mixin(ServerEntityManager.class)
public class ServerEntityManagerMixin<T extends EntityLike> {

    @Inject(method = "addEntity(Lnet/minecraft/world/entity/EntityLike;Z)Z", at = @At(value = "HEAD"))
    private void entityJoinWorldEvent(T entity, boolean existing, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Entity entity2) {
            EntityEvents.ENTITY_JOIN_WORLD_EVENT.invoker().onEntityJoinWorld(entity2, entity2.world);
        }
    }
}
