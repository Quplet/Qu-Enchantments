package qu.quEnchantments.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import qu.quEnchantments.util.interfaces.IEntity;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {

    @Shadow
    @Final
    protected DataTracker dataTracker;

    @Shadow public abstract World getWorld();

    @Unique
    private static final TrackedData<Integer> INANE_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;initDataTracker(Lnet/minecraft/entity/data/DataTracker$Builder;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void qu_Enchantments$trackInaneTicks(EntityType<?> type, World world, CallbackInfo ci, DataTracker.Builder builder) {
        builder.add(INANE_TICKS, 0);
    }

    @Override
    public int quEnchantments$getInaneTicks() {
        return this.dataTracker.get(INANE_TICKS);
    }

    @Override
    public void quEnchantments$setInaneTicks(int value) {
        this.dataTracker.set(INANE_TICKS, value);
    }

    @Inject(method = "writeNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    private void quEnchantments$writeInaneTicksToNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        int inaneTicks;
        if ((inaneTicks = this.quEnchantments$getInaneTicks()) > 0) {
            nbt.putInt("TicksInane", inaneTicks);
        }
    }

    @Inject(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFrozenTicks(I)V"))
    private void quEnchantments$readInaneTicksFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.quEnchantments$setInaneTicks(nbt.getInt("TicksInane"));
    }
}
