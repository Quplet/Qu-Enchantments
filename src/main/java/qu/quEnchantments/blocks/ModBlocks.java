package qu.quEnchantments.blocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import qu.quEnchantments.QuEnchantments;

public class ModBlocks {

    public static final Block HOT_OBSIDIAN = register(
            "hot_obsidian",
            new HotObsidianBlock(AbstractBlock.Settings.copy(Blocks.OBSIDIAN).luminance(state -> 7))
    );

    //public static final Block CLOUD = register("cloud", new CloudBlock(AbstractBlock.Settings.of(Material.POWDER_SNOW).sounds(BlockSoundGroup.SNOW).breakInstantly().nonOpaque().suffocates(ModBlocks::never)));
    public static final Block CLOUD = register(
            "cloud",
            new CloudBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.CLEAR)
                            .sounds(BlockSoundGroup.SNOW)
                            .breakInstantly()
                            .nonOpaque()
                            .suffocates(ModBlocks::never)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    private static Block register(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(QuEnchantments.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        QuEnchantments.LOGGER.info("Registering ModBlocks for " + QuEnchantments.MOD_ID);
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
