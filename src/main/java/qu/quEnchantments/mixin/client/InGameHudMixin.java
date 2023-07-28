package qu.quEnchantments.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.util.interfaces.IEntity;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    private static final Identifier INANE_OUTLINE = new Identifier(QuEnchantments.MOD_ID, "textures/misc/inane_outline.png");

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"))
    private void renderInaneLayer(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.client.player == null) return;
        int inaneLevel;
        if ((inaneLevel = ((IEntity)this.client.player).getInaneTicks()) > 0) {
            this.renderOverlay(context, INANE_OUTLINE, Math.min(inaneLevel, 80.0f) / 80.0f);
        }
    }
}
