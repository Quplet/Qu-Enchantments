package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.enchantments.QuEnchantmentHelper;
import qu.quEnchantments.util.interfaces.IEntity;

@Mixin(Entity.class)
public class EntityMixin implements IEntity {

    @Shadow
    @Final
    protected DataTracker dataTracker;

    @Shadow public World world;
    @Unique
    private static final TrackedData<Integer> INANE_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void quEnchantments$trackInaneTicks(EntityType<?> type, World world, CallbackInfo ci) {
        dataTracker.startTracking(INANE_TICKS, 0);
    }

    @Override
    @Unique
    public int getInaneTicks() {
        return this.dataTracker.get(INANE_TICKS);
    }

    @Override
    @Unique
    public void setInaneTicks(int value) {
        this.dataTracker.set(INANE_TICKS, value);
    }

    @Inject(method = "writeNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    private void quEnchantments$writeInaneTicksToNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        int i;
        if ((i = this.getInaneTicks()) > 0) {
            nbt.putInt("TicksInane", i);
        }
    }

    @Inject(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFrozenTicks(I)V"))
    private void quEnchantments$readInaneTicksFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.setInaneTicks(nbt.getInt("TicksInane"));
    }

    @Inject(method = "applyDamageEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void quEnchantments$injectOnTargetDamage(LivingEntity attacker, Entity target, CallbackInfo ci) {
        if (attacker != null) {
            QuEnchantmentHelper.onTargetDamaged(attacker, attacker.getMainHandStack(), target);
        }
    }
}
