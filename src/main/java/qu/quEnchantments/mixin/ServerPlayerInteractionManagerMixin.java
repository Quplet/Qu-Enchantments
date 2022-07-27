package qu.quEnchantments.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.quEnchantments.enchantments.ModEnchantments;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Final
    @Shadow
    protected ServerPlayerEntity player;
    @Shadow
    protected ServerWorld world;

    @Inject(method = "tryBreakBlock", at = @At(value = "INVOKE",
                target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z"))
    private void quEnchantments$mineRing(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.player.getMainHandStack();
        int lvl;
        if ((lvl = EnchantmentHelper.getLevel(ModEnchantments.STRIP_MINER, itemStack)) > 0) {
            Iterable<BlockPos> iterable;
            if (lvl == 1) {
                List<BlockPos> temp = new ArrayList<>(2);
                temp.add(pos);
                boolean aligned = pos.getX() == player.getBlockX() && pos.getZ() == player.getBlockZ();
                if (pos.getY() >= player.getBlockY() + 1) {
                    if (aligned) temp.add(pos.up());
                    else temp.add(pos.down());
                } else {
                    if (aligned) temp.add(pos.down());
                    else temp.add(pos.up());
                }
                iterable = temp;
            } else {
                iterable = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
            }
            for (BlockPos blockPos : iterable) {
                BlockState blockState = this.world.getBlockState(blockPos);
                if (!blockState.isSolidBlock(this.world, blockPos)) continue;
                if (!itemStack.isSuitableFor(blockState)) continue;
                world.syncWorldEvent(14004, blockPos, 0);
                if (pos.equals(blockPos)) continue;
                if (blockState.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
                    PiglinBrain.onGuardedBlockInteracted(player, false);
                }
                world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, blockState));
                if (this.world.removeBlock(blockPos, false)) {
                    blockState.getBlock().onBroken(this.world, blockPos, blockState);
                }
            }
        }
    }
}
