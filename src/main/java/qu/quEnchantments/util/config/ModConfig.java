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
    public final AccuracyOptions accuracyOptions = new AccuracyOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AggressionBlessingOptions aggressionBlessingOptions = new AggressionBlessingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AgitationCurseOptions agitationCurseOptions = new AgitationCurseOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ArrowsFlightOptions arrowsFlightOptions = new ArrowsFlightOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BashingOptions bashingOptions = new BashingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final EssenceOfEnderOptions essenceOfEnderOptions = new EssenceOfEnderOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FidelityOptions fidelityOptions = new FidelityOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FreezingAspectOptions freezingAspectOptions = new FreezingAspectOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final InaneAspectOptions inaneAspectOptions = new InaneAspectOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final LeechingAspectOptions leechingAspectOptions = new LeechingAspectOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final MoltenWalkerOptions moltenWalkerOptions = new MoltenWalkerOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final NightbloodOptions nightbloodOptions = new NightbloodOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final OmenOfImmunityOptions omenOfImmunityOptions = new OmenOfImmunityOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ReflectionOptions reflectionOptions = new ReflectionOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final RegenerationBlessingOptions regenerationBlessingOptions = new RegenerationBlessingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SkywalkerOptions skywalkerOptions = new SkywalkerOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SpeedBlessingOptions speedBlessingOptions = new SpeedBlessingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final StripMinerOptions stripMinerOptions = new StripMinerOptions();

    public static class RuneOptions {
        public boolean runesEnabled = true;
        public boolean breakOnNoDurability = false;
    }

    public static class AccuracyOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
    }

    public static class AggressionBlessingOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int attackSpeed = 8; // REQUIRES RESTART
    }
    
    public static class AgitationCurseOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 8, max = 64)
        public int radius = 16;
    }

    public static class ArrowsFlightOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int arrowSpeed = 10;
    }

    public static class BashingOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int knockbackStrength = 6;

    }

    public static class EssenceOfEnderOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 2, max = 8)
        public int mobTeleportDistance = 5;
    }

    public static class FidelityOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
    }

    public static class FreezingAspectOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(max = 255)
        public int duration = 75;
    }

    public static class InaneAspectOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(max = 255)
        public int duration = 40;
    }

    public static class LeechingAspectOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        public float healing = 0.25f;
    }

    public static class MoltenWalkerOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int radius = 2;
    }

    public static class NightbloodOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        public boolean disablesExperience = true;
        @ConfigEntry.BoundedDiscrete(max = 10000)
        public int witherDuration = 200;
        @ConfigEntry.BoundedDiscrete(max = 255)
        public int witherAmplifier = 1;
        @ConfigEntry.BoundedDiscrete(max = 100)
        public int drainRate = 10;
    }

    public static class OmenOfImmunityOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
    }

    public static class ReflectionOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(max = 20)
        public int divergence = 10;
    }

    public static class RegenerationBlessingOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
    }

    public static class SkywalkerOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(max = 16)
        public int radius = 0;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int cloudDuration = 25;
    }

    public static class SpeedBlessingOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = false;
        public boolean randomSelection = true;
        public boolean bookOffer = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
        public int speedBoost = 2;
    }

    public static class StripMinerOptions {
        public boolean isEnabled = true;
        public boolean isTreasure = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int radius = 1;
    }
}
