package qu.quEnchantments.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.entity.ai.goals.FidelityFollowOwnerGoal;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {

    @Inject(method = "initCustomGoals", at = @At("TAIL"))
    private void quEnchantments$addFidelityGoal(CallbackInfo ci) {
        this.goalSelector.add(2, new FidelityFollowOwnerGoal((AbstractHorseEntity)(Object)this, 1.3, 5.0f, 10.0f, false));
    }

    // Ignore
    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
}
