package qu.quEnchantments.util.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import qu.quEnchantments.QuEnchantments;

@Config(name = QuEnchantments.MOD_ID)
//@Config.Gui.Background("qu-enchantments:textures/block/hot_obsidian_2.png")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.TransitiveObject
    public final RuneOptions runeOptions = new RuneOptions();

    @ConfigEntry.Gui.CollapsibleObject
    public final AggressionBlessingOptions aggressionBlessingOptions = new AggressionBlessingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AgitationCurseOptions agitationCurseOptions = new AgitationCurseOptions();

    public static class RuneOptions {
        public boolean runesEnabled = true;
        public boolean breakOnNoDurability = false;
    }

    public static class AggressionBlessingOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean isAvailableForRandomSelection = true;
        public boolean isAvailableForBookOffer = true;
        public int minPower = 15;
        public int maxPower = 50;
        public double attackSpeed = 0.8;
    }
    
    public static class AgitationCurseOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean isAvailableForRandomSelection = false;
        public boolean isAvailableForBookOffer = false;
        public int minPower = 25;
        public int maxPower = 50;
        @ConfigEntry.BoundedDiscrete(min = 8, max = 64)
        public int radius = 16;
    }
}
