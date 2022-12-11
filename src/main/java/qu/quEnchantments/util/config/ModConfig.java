package qu.quEnchantments.util.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.math.MathHelper;
import qu.quEnchantments.QuEnchantments;

@Config(name = QuEnchantments.MOD_ID)
//@Config.Gui.Background("qu-enchantments:textures/block/hot_obsidian_2.png")
public class ModConfig implements ConfigData {

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
    public final LightningBoundOptions lightningBoundOptions = new LightningBoundOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final LuckyMinerOptions luckyMinerOptions = new LuckyMinerOptions();
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
    public final ShapedGlassOptions shapedGlassOptions = new ShapedGlassOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SkywalkerOptions skywalkerOptions = new SkywalkerOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SpeedBlessingOptions speedBlessingOptions = new SpeedBlessingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final StripMinerOptions stripMinerOptions = new StripMinerOptions();

    public static class RuneOptions {
        @ConfigEntry.Gui.Tooltip
        public boolean breakOnNoDurability = false;
    }

    public static class AccuracyOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
    }

    public static class AggressionBlessingOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        @ConfigEntry.Gui.Tooltip
        public float attackSpeed = 0.8f; // REQUIRES RESTART
    }
    
    public static class AgitationCurseOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 8, max = 64)
        public double radius = 16.0;
    }

    public static class ArrowsFlightOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public float arrowSpeed = 1.0f;
    }

    public static class BashingOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public float knockbackStrength = 0.6f;

    }

    public static class EssenceOfEnderOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(min = 2, max = 8)
        public int entityTeleportDistance = 5;
    }

    public static class FidelityOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
    }

    public static class FreezingAspectOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public int duration = 75;
    }

    public static class InaneAspectOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public int duration = 40;
    }

    public static class LeechingAspectOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public float healing = 0.25f;
    }

    public static class LightningBoundOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
    }

    public static class LuckyMinerOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = false;
        public boolean bookOffer = true;
    }

    public static class MoltenWalkerOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        @ConfigEntry.BoundedDiscrete(max = 16)
        public int radius = 2;
    }

    public static class NightbloodOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        public boolean disablesExperience = true;
        @ConfigEntry.Gui.Tooltip
        public int witherDuration = 200;
        @ConfigEntry.BoundedDiscrete(max = 255)
        public int witherAmplifier = 1;
        @ConfigEntry.Gui.Tooltip
        public float drainRate = 1.0f;
    }

    public static class OmenOfImmunityOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        public boolean breakOnNoDurability = true;
    }

    public static class ReflectionOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        public float divergence = 1.0f;
    }

    public static class RegenerationBlessingOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
    }

    public static class ShapedGlassOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        public float damageMultiplier = 2.0f;
        public float itemDamage = 1.0f;
    }

    public static class SkywalkerOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(max = 16)
        public int radius = 0;
        @ConfigEntry.Gui.Tooltip
        public int cloudDuration = 25;
    }

    public static class SpeedBlessingOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = true;
        public boolean enchantingTable = true;
        public boolean bookOffer = true;
        @ConfigEntry.Gui.Tooltip
        public float speedBoost = 0.2f;
    }

    public static class StripMinerOptions {
        public boolean isEnabled = true;
        public boolean randomSelection = false;
        public boolean enchantingTable = false;
        public boolean bookOffer = false;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 16)
        public int radius = 1;
    }

    @Override
    public void validatePostLoad() {
        aggressionBlessingOptions.attackSpeed = MathHelper.clamp(aggressionBlessingOptions.attackSpeed, 0.0f, 255.0f);

        agitationCurseOptions.radius = MathHelper.clamp(agitationCurseOptions.radius, 0.0, 64.0);

        arrowsFlightOptions.arrowSpeed = MathHelper.clamp(arrowsFlightOptions.arrowSpeed, 0.0f, 20.0f);

        bashingOptions.knockbackStrength = MathHelper.clamp(bashingOptions.knockbackStrength, 0.01f, 100.0f);

        essenceOfEnderOptions.entityTeleportDistance = MathHelper.clamp(essenceOfEnderOptions.entityTeleportDistance, 2, 8);

        freezingAspectOptions.duration = MathHelper.clamp(freezingAspectOptions.duration, 0, 99999);

        inaneAspectOptions.duration = MathHelper.clamp(inaneAspectOptions.duration, 0, 99999);

        leechingAspectOptions.healing = MathHelper.clamp(leechingAspectOptions.healing, 0.0f, 99999.0f);

        moltenWalkerOptions.radius = MathHelper.clamp(moltenWalkerOptions.radius, 0, 16);

        nightbloodOptions.witherDuration = MathHelper.clamp(nightbloodOptions.witherDuration, 0, 99999);
        nightbloodOptions.witherAmplifier = MathHelper.clamp(nightbloodOptions.witherAmplifier, 0, 255);
        nightbloodOptions.drainRate = MathHelper.clamp(nightbloodOptions.drainRate, 0.0f, 99999.0f);

        reflectionOptions.divergence = MathHelper.clamp(reflectionOptions.divergence, 0.0f, 20.0f);

        shapedGlassOptions.damageMultiplier = MathHelper.clamp(shapedGlassOptions.damageMultiplier, 0.0f, 99999.9f);
        shapedGlassOptions.itemDamage = MathHelper.clamp(shapedGlassOptions.itemDamage, 0, 99999);

        skywalkerOptions.radius = MathHelper.clamp(skywalkerOptions.radius, 0, 16);
        skywalkerOptions.cloudDuration = MathHelper.clamp(skywalkerOptions.cloudDuration, 1, 99999);

        speedBlessingOptions.speedBoost = MathHelper.clamp(speedBlessingOptions.speedBoost, 0.0f, 255.0f);

        stripMinerOptions.radius = MathHelper.clamp(stripMinerOptions.radius, 1, 16);

        QuEnchantments.LOGGER.info("Finished validating config for " + QuEnchantments.MOD_ID);
    }
}
