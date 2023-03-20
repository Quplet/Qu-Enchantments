package qu.quEnchantments.enchantments.tool;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CompoundEnchantment;
import qu.quEnchantments.util.ModLootTableModifier;
import qu.quEnchantments.util.config.ModConfig;

public class LuckyMinerEnchantment extends CompoundEnchantment {

    private static final ModConfig.LuckyMinerOptions CONFIG = QuEnchantments.getConfig().luckyMinerOptions;
    public LuckyMinerEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? super.getMaxLevel() : 0;
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
    public boolean isTreasure() {
        return true;
    }

    @Override
    public void onBlockBreak(PlayerEntity player, BlockPos pos, ItemStack stack, int level) {
        World world = player.world;
        if (world.isClient || player.getAbilities().creativeMode || !player.canHarvest(player.world.getBlockState(pos))) return;
        if (!passed(getLuck(player), world.random, aDouble -> aDouble * 20 >= 20.0 - Math.log(level + 1))) return;

        boolean isOverworld = world.getDimension().natural();
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) player.world).parameter(LootContextParameters.BLOCK_STATE, player.world.getBlockState(pos)).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, stack);
        LootTable lootTable = player.world.getServer().getLootManager().getTable(isOverworld ? ModLootTableModifier.LUCKY_MINER_OVERWORLD : ModLootTableModifier.LUCKY_MINER_NETHER);
        ObjectArrayList<ItemStack> list = lootTable.generateLoot(builder.build(LootContextTypes.BLOCK));

        if (list.get(0).getItem() instanceof BlockItem blockItem) {
            BlockState state = blockItem.getBlock().getDefaultState();
            Random random = world.random;
            Iterable<BlockPos> iterable = BlockPos.iterateRandomly(random, random.nextInt(3) + 1, pos, 2);
            for (BlockPos pos2 : iterable) {
                if (!world.canPlayerModifyAt(player, pos2) || !world.getBlockState(pos2).isOf(isOverworld ? Blocks.STONE : Blocks.NETHERRACK)) continue;
                world.setBlockState(pos2, state);
                System.out.println("Placed " + state.getBlock() + " at " + pos2);
            }
        }
    }
}
