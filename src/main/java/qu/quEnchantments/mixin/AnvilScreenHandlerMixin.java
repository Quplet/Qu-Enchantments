package qu.quEnchantments.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import qu.quEnchantments.enchantments.CompoundEnchantment;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.util.interfaces.IItemStack;
import qu.quEnchantments.world.ModWorldEvents;

@Debug(export = true)
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Inject(at = @At("HEAD"), method = "onTakeOutput")
    private void quEnchantments$breakShapedGlassOnAnvilTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo info) {
        if (player.getAbilities().creativeMode) return;
        boolean bl = false;
        for (int i = 0; i < this.input.size(); i++) {
            ItemStack inputStack = this.input.getStack(i);
            if (EnchantmentHelper.getLevel(ModEnchantments.SHAPED_GLASS, inputStack) <= 0 || inputStack.isOf(Items.ENCHANTED_BOOK)) continue;
            bl = true;
            break;
        }
        // bl is true if one of the input stacks is not an enchanted book and has Shaped Glass
        if (!bl) return;
        stack.setDamage(stack.getMaxDamage() - 1);
        stack.damage(100, player, Entity::toString);
        if (player.world.isClient) {
            player.playSound(SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 5.0f, 1.0f);
        }
        player.world.syncWorldEvent(player, ModWorldEvents.SHAPED_GLASS_BREAK, player.getBlockPos(), 0);
    }

    @Inject(at = @At("TAIL"), method = "updateResult")
    private void quEnchantments$corruptOnUpdate(CallbackInfo info) {
        // Output stack needs to be manually corrupted
        ItemStack stack = this.output.getStack(0);
        if (stack.hasEnchantments()) {
            ((IItemStack)(Object)stack).setEnchantmentsDirty(true);
            CorruptedEnchantment.corruptEnchantments(stack);
        }
    }

    @ModifyArgs(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private void quEnchantments$combineLevelForCompound(Args args) {
        Enchantment enchantment = args.get(0);
        if (!(enchantment instanceof CompoundEnchantment)) return;

        ItemStack itemStack1 = this.input.getStack(0);
        ItemStack itemStack2 = this.input.getStack(1);

        int level = EnchantmentHelper.getLevel(enchantment, itemStack1) + EnchantmentHelper.getLevel(enchantment, itemStack2);

        args.set(1, Math.min(level, enchantment.getMaxLevel()));
    }

    // Ignore
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
}
