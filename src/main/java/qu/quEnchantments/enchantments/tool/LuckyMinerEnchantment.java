package qu.quEnchantments.enchantments.tool;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.annotation.Debug;
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
        // TODO: Rewrite
        World world;
        if ((world = player.getWorld()).isClient || player.getAbilities().creativeMode || !player.canHarvest(world.getBlockState(pos))) return;
        // Should fall roughly in the 1% (min) to 24% (max) range, logarithmically
        if (!passed(getLuck(player), world.random, aDouble -> aDouble < Math.log1p(level/10.0) / 10)) return;

        boolean isOverworld = world.getDimension().natural();
        LootContextParameterSet parameterSet = new LootContextParameterSet.Builder((ServerWorld) world).add(LootContextParameters.BLOCK_STATE, world.getBlockState(pos)).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).add(LootContextParameters.TOOL, stack).build(LootContextTypes.BLOCK);
        LootTable lootTable = world.getServer().getLootManager().getLootTable(isOverworld ? ModLootTableModifier.LUCKY_MINER_OVERWORLD : ModLootTableModifier.LUCKY_MINER_NETHER);
        ObjectArrayList<ItemStack> list = lootTable.generateLoot(parameterSet);

        if (list.get(0) == null) return;

        if (list.get(0).getItem() instanceof BlockItem blockItem) {
            BlockState rolledState = blockItem.getBlock().getDefaultState();
            Random random = world.random;
            Iterable<BlockPos> iterable = BlockPos.iterateRandomly(random, random.nextInt(3) + 1, pos, 3);
            for (BlockPos pos2 : iterable) {
                if (!world.canPlayerModifyAt(player, pos2)) continue;
                BlockState tempState = world.getBlockState(pos2);
                BlockState newState = rolledState;

                if (isOverworld) {
                    if (tempState.isOf(Blocks.DEEPSLATE)) {
                        String newID = "deepslate_" + Registries.BLOCK.getId(newState.getBlock()).getPath();
                        System.out.println(newID);
                        newState = Registries.BLOCK.get(new Identifier(newID)).getDefaultState();
                    } else if (!tempState.isOf(Blocks.STONE)) {
                        continue;
                    }
                } else if (!tempState.isOf(Blocks.NETHERRACK)) {
                    continue;
                }

                world.setBlockState(pos2, newState);
            }
        }
    }
}
