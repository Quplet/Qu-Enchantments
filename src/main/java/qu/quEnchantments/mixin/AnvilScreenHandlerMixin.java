package qu.quEnchantments.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
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

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Unique
    private int compoundLevel = 0;

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
        stack.damage(100, player, EquipmentSlot.MAINHAND);

        World world;
        if ((world = player.getWorld()).isClient) {
            player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 5.0f, 1.0f);
        }
        world.syncWorldEvent(player, ModWorldEvents.SHAPED_GLASS_BREAK, player.getBlockPos(), 0);
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sendContentUpdates()V"))
    private void quEnchantments$corruptOnUpdate(CallbackInfo info) {
        // Output stack needs to be manually corrupted
        ItemStack stack = this.output.getStack(0);
        if (stack.hasEnchantments() || stack.isOf(Items.ENCHANTED_BOOK)) {
            ((IItemStack)(Object)stack).qu_Enchantments$setEnchantmentsDirty(true);
            CorruptedEnchantment.corruptEnchantments(stack);
        }
    }

    @ModifyArgs(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ItemEnchantmentsComponent$Builder;set(Lnet/minecraft/enchantment/Enchantment;I)V"))
    private void quEnchantments$combineLevelForCompound(Args args) {
        Enchantment enchantment = args.get(0);
        if (!(enchantment instanceof CompoundEnchantment)) return;

        ItemStack stack1 = this.input.getStack(0);
        ItemStack stack2 = this.input.getStack(1);
        int level1, level2;
        level1 = EnchantmentHelper.getLevel(enchantment, stack1);
        level2 = EnchantmentHelper.getLevel(enchantment, stack2);
        int level = Math.min(level1 + level2, 100);

        compoundLevel = level;

        args.set(1, level);
    }

    @ModifyVariable(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ItemEnchantmentsComponent$Builder;set(Lnet/minecraft/enchantment/Enchantment;I)V", shift = At.Shift.AFTER), ordinal = 3)
    private int quEnchantments$modifyR(int original) {
        if (compoundLevel > 0) {
            int value = (int) (compoundLevel * 0.3);
            compoundLevel = 0;
            return Math.max(value, 1);
        }
        return original;
    }

    // Ignore
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
}
