package qu.quEnchantments.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModBlocks {

    public static Block HOT_OBSIDIAN = register("hot_obsidian", new HotObsidianBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(50.0f, 1200.0f).luminance(state -> 7)));

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(QuEnchantments.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        QuEnchantments.LOGGER.info("Registering ModBlocks for " + QuEnchantments.MOD_ID);
    }
}
