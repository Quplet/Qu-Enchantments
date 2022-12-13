package qu.quEnchantments.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
    @Unique
    private int compoundLevel = 0;

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

        ItemStack stack1 = this.input.getStack(0);
        ItemStack stack2 = this.input.getStack(1);
        Integer level1, level2;
        level1 = EnchantmentHelper.get(stack1).get(enchantment);
        if (level1 == null) level1 = 0;
        level2 = EnchantmentHelper.get(stack2).get(enchantment);
        if (level2 == null) level2 = 0;
        int level = Math.min(level1 + level2, 100);

        compoundLevel = level;

        args.set(1, level);
    }

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER), ordinal = 4)
    private int quEnchantments$modifyR(int original) {
        if (compoundLevel > 0) {
            int value = (int) (compoundLevel * 0.3);
            compoundLevel = 0;
            return value;
        }
        return original;
    }

    /*
    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V", ordinal = 5, shift = At.Shift.AFTER))
    private void quEnchantments$updateLevelCost(CallbackInfo ci) {
        System.out.println("Original " + this.levelCost.get());
        System.out.println("Subtracting: " + subtractLevel);
        this.levelCost.set((int) (this.levelCost.get() - subtractLevel * 0.9));
        System.out.println("New: " + this.levelCost.get());
        subtractLevel = 0;
    }
     */

    // Ignore
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
}
