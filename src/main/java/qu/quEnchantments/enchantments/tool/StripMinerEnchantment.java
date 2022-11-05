package qu.quEnchantments.enchantments.tool;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CorruptedEnchantment;
import qu.quEnchantments.util.config.ModConfig;
import qu.quEnchantments.world.ModWorldEvents;

import java.util.ArrayList;
import java.util.List;

public class StripMinerEnchantment extends CorruptedEnchantment {

    private static final ModConfig.StripMinerOptions CONFIG = QuEnchantments.getConfig().stripMinerOptions;

    public StripMinerEnchantment(EnchantmentType enchantmentType, Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(enchantmentType, weight, type, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 2 : 0;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public void onBlockBreak(PlayerEntity player, BlockPos pos, ItemStack stack, int level) {
        if (player.world.isClient) return;
        Iterable<BlockPos> iterable;
        if (level == 1) {
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
            int radius = CONFIG.radius;
            iterable = BlockPos.iterate(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius));
        }
        for (BlockPos blockPos : iterable) {
            BlockState blockState = player.world.getBlockState(blockPos);
            if (!blockState.isSolidBlock(player.world, blockPos)) continue;
            if (!stack.isSuitableFor(blockState)) continue;
            player.world.syncWorldEvent(ModWorldEvents.STRIP_MINER_DESTROY_BLOCK, blockPos, 0);
            if (pos.equals(blockPos)) continue;
            if (blockState.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
                PiglinBrain.onGuardedBlockInteracted(player, false);
            }
            player.world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, blockState));
            if (player.world.removeBlock(blockPos, false)) {
                blockState.getBlock().onBroken(player.world, blockPos, blockState);
            }
        }
    }
}
