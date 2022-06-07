package qu.quEnchantments.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private @Nullable ClientWorld world;

    @Inject(at = @At("TAIL"), method = "processWorldEvent")
    private void processEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        Random random = world.random;
        switch (eventId) {
            case 14001 -> {
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + 1.2, (double) pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                this.world.playSound(pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
            }
            case 14002 -> this.world.playSound(pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 5.0f, 1.0f, true);
            case 14003 -> this.world.playSound(pos, SoundEvents.BLOCK_POWDER_SNOW_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            case 14004 -> {
                for (int i = 0; i < 3; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                this.world.playSound(pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
            }
        }
    }
}
