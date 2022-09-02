package qu.quEnchantments.util.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import qu.quEnchantments.QuEnchantments;

@Config(name = QuEnchantments.MOD_ID)
//@Config.Gui.Background("qu-enchantments:textures/block/hot_obsidian_2.png")
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("corrupted enchantment settings")
    public boolean IS_TREASURE = true;
    @ConfigEntry.Category("corrupted enchantment settings")
    public boolean AVAILABLE_FOR_RANDOM_SELECTION = false;
    @ConfigEntry.Category("corrupted enchantment settings")
    public boolean AVAILABLE_FOR_BOOK_OFFER = false;

}
