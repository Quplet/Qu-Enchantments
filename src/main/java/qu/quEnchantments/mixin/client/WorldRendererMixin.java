package qu.quEnchantments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow private @Nullable ClientWorld world;

    @Inject(at = @At("TAIL"), method = "processWorldEvent")
    private void processEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (this.world == null) return;
        Random random = this.world.random;
        switch (eventId) {
            case 14001 -> {
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + random.nextDouble(), pos.getY() + 1.2, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
            }
            case 14002 -> this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 5.0f, 1.0f, true);
            case 14003 -> this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_POWDER_SNOW_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
            case 14004 -> {
                for (int i = 0; i < 3; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
                this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.0f, true);
            }
            case 14005 -> {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(i, j, k, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.3f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f, true);
                for (int l = 0; l < 4; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, i + Math.random(), j + Math.random(), k + Math.random(), 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
