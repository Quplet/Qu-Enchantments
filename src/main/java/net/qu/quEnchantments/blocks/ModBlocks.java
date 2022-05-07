package net.qu.quEnchantments.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.qu.quEnchantments.QuEnchantments;

public class ModBlocks {

    public static Block HOT_OBSIDIAN = register("hot_obsidian", new HotObsidianBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(50.0f, 1200.0f).luminance(state -> 10)));

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(QuEnchantments.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        QuEnchantments.LOGGER.info("Registering ModBlocks for " + QuEnchantments.MOD_ID);
    }
}
