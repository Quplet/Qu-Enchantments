package qu.quEnchantments.blocks;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import qu.quEnchantments.QuEnchantments;

public class ModBlocks {

    public static final Block HOT_OBSIDIAN = register("hot_obsidian", new HotObsidianBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(35.0f, 800.0f).luminance(state -> 7)));
    public static final Block CLOUD = register("cloud", new CloudBlock(AbstractBlock.Settings.of(Material.POWDER_SNOW).sounds(BlockSoundGroup.SNOW).breakInstantly().nonOpaque().suffocates(ModBlocks::never)));

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(QuEnchantments.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        QuEnchantments.LOGGER.info("Registering ModBlocks for " + QuEnchantments.MOD_ID);
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
