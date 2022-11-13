package qu.quEnchantments.enchantments.tool;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof LuckEnchantment) && super.canAccept(other);
    }

    @Override
    public void onBlockBreak(PlayerEntity player, BlockPos pos, ItemStack stack, int level) {
        if (player.world.isClient || player.getAbilities().creativeMode || !player.canHarvest(player.world.getBlockState(pos))) return;

        if (!passed(getLuck(player), player.world.random, aDouble -> aDouble * 20 >= 20.0 - Math.log(level + 1))) return;

        LootContext.Builder builder = new LootContext.Builder((ServerWorld) player.world).parameter(LootContextParameters.BLOCK_STATE, player.world.getBlockState(pos)).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, stack);
        LootTable lootTable = player.world.getServer().getLootManager().getTable(ModLootTableModifier.LUCKY_MINER_GAMEPLAY);
        ObjectArrayList<ItemStack> list = lootTable.generateLoot(builder.build(LootContextTypes.BLOCK));
        Block.dropStack(player.world, pos, list.get(0));
    }
}
