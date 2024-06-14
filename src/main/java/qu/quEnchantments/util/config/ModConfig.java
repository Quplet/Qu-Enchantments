package qu.quEnchantments.util.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

public class ModConfig {

    public static ConfigClassHandler<ModConfig> CONFIG_HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(new Identifier(QuEnchantments.MOD_ID, "configuration"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(QuEnchantments.MOD_ID + ".json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    static {
        if (!CONFIG_HANDLER.load()) {
            QuEnchantments.LOGGER.warn("Unable to load configuration file. Perhaps it doesn't exist yet?");
        }
    }

    // Rune options
    @SerialEntry
    public boolean runeBreakOnNoDurability = false;

    // Accuracy options
    @SerialEntry
    public boolean accuracyEnabled = true;
    @SerialEntry
    public boolean accuracyRandomSelection = true;
    @SerialEntry
    public boolean accuracyEnchantingTable = true;
    @SerialEntry
    public boolean accuracyBookOffer = true;

    // Aggression Blessing options
    @SerialEntry
    public boolean aggressionBlessingEnabled = true;
    @SerialEntry
    public boolean aggressionBlessingRandomSelection = true;
    @SerialEntry
    public boolean aggressionBlessingEnchantingTable = true;
    @SerialEntry
    public boolean aggressionBlessingBookOffer = true;
    @SerialEntry
    public float aggressionBlessingAttackSpeed = 0.8f;

    // Agitation Curse options
    @SerialEntry
    public boolean agitationCurseEnabled = true;
    @SerialEntry
    public boolean agitationCurseRandomSelection = false;
    @SerialEntry
    public boolean agitationCurseBookOffer = false;
    @SerialEntry
    public double agitationCurseRadius = 16.0;

    // Arrows Flight options
    @SerialEntry
    public boolean arrowsFlightEnabled = true;
    @SerialEntry
    public boolean arrowsFlightRandomSelection = true;
    @SerialEntry
    public boolean arrowsFlightEnchantingTable = true;
    @SerialEntry
    public boolean arrowsFlightBookOffer = true;
    @SerialEntry
    public float arrowsFlightArrowSpeed = 1.0f;

    // Bashing options
    @SerialEntry
    public boolean bashingEnabled = true;
    @SerialEntry
    public boolean bashingRandomSelection = true;
    @SerialEntry
    public boolean bashingEnchantingTable = true;
    @SerialEntry
    public boolean bashingBookOffer = true;
    @SerialEntry
    public float bashingKnockbackStrength = 0.6f;

    // Essence Of Ender options
    @SerialEntry
    public boolean essenceOfEnderEnabled = true;
    @SerialEntry
    public boolean essenceOfEnderRandomSelection = false;
    @SerialEntry
    public boolean essenceOfEnderEnchantingTable = false;
    @SerialEntry
    public boolean essenceOfEnderBookOffer = false;
    @SerialEntry
    public double essenceOfEnderTeleportDistance = 5.0;

    // Fidelity options
    @SerialEntry
    public boolean fidelityEnabled = true;
    @SerialEntry
    public boolean fidelityRandomSelection = false;
    @SerialEntry
    public boolean fidelityEnchantingTable = false;
    @SerialEntry
    public boolean fidelityBookOffer = false;

    // Freezing Aspect options
    @SerialEntry
    public boolean freezingAspectEnabled = true;
    @SerialEntry
    public boolean freezingAspectRandomSelection = true;
    @SerialEntry
    public boolean freezingAspectEnchantingTable = true;
    @SerialEntry
    public boolean freezingAspectBookOffer = true;
    @SerialEntry
    public int freezingAspectDuration = 75;

    // Inane Aspect options
    @SerialEntry
    public boolean inaneAspectEnabled = true;
    @SerialEntry
    public boolean inaneAspectRandomSelection = true;
    @SerialEntry
    public boolean inaneAspectEnchantingTable = true;
    @SerialEntry
    public boolean inaneAspectBookOffer = true;
    @SerialEntry
    public int inaneAspectDuration = 40;

    // Leeching Aspect options
    @SerialEntry
    public boolean leechingAspectEnabled = true;
    @SerialEntry
    public boolean leechingAspectRandomSelection = true;
    @SerialEntry
    public boolean leechingAspectEnchantingTable = true;
    @SerialEntry
    public boolean leechingAspectBookOffer = true;
    @SerialEntry
    public float leechingAspectHealing = 0.25f;

    // Lightning Bound options
    @SerialEntry
    public boolean lightningBoundEnabled = true;
    @SerialEntry
    public boolean lightningBoundRandomSelection = true;
    @SerialEntry
    public boolean lightningBoundEnchantingTable = true;
    @SerialEntry
    public boolean lightningBoundBookOffer = true;

    // Lucky Miner options
    @SerialEntry
    public boolean luckyMinerEnabled = true;
    @SerialEntry
    public boolean luckyMinerRandomSelection = true;
    @SerialEntry
    public boolean luckyMinerEnchantingTable = false;
    @SerialEntry
    public boolean luckyMinerBookOffer = true;

    // Molten Walker options
    @SerialEntry
    public boolean moltenWalkerEnabled = true;
    @SerialEntry
    public boolean moltenWalkerRandomSelection = true;
    @SerialEntry
    public boolean moltenWalkerEnchantingTable = true;
    @SerialEntry
    public boolean moltenWalkerBookOffer = true;
    @SerialEntry
    public int moltenWalkerRadius = 2;

    // Nightblood options
    @SerialEntry
    public boolean nightbloodEnabled = true;
    @SerialEntry
    public boolean nightbloodRandomSelection = false;
    @SerialEntry
    public boolean nightbloodEnchantingTable = false;
    @SerialEntry
    public boolean nightbloodBookOffer = false;
    @SerialEntry
    public boolean nightbloodDisablesExperience = true;
    @SerialEntry
    public int nightbloodWitherDuration = 200;
    @SerialEntry
    public int nightbloodWitherAmplifier = 1;
    @SerialEntry
    public float nightbloodDrainRate = 1.0f;

    // Omen Of Immunity options
    @SerialEntry
    public boolean omenOfImmunityEnabled = true;
    @SerialEntry
    public boolean omenOfImmunityRandomSelection = false;
    @SerialEntry
    public boolean omenOfImmunityEnchantingTable = false;
    @SerialEntry
    public boolean omenOfImmunityBookOffer = false;
    @SerialEntry
    public boolean omenOfImmunityBreakOnNoDurability = true;

    // Reflection options
    @SerialEntry
    public boolean reflectionEnabled = true;
    @SerialEntry
    public boolean reflectionRandomSelection = true;
    @SerialEntry
    public boolean reflectionEnchantingTable = true;
    @SerialEntry
    public boolean reflectionBookOffer = true;
    @SerialEntry
    public float reflectionDivergence = 1.0f;

    // Regeneration Blessing options
    @SerialEntry
    public boolean regenerationBlessingEnabled = true;
    @SerialEntry
    public boolean regenerationBlessingRandomSelection = true;
    @SerialEntry
    public boolean regenerationBlessingEnchantingTable = true;
    @SerialEntry
    public boolean regenerationBlessingBookOffer = true;

    // Shaped Glass options
    @SerialEntry
    public boolean shapedGlassEnabled = true;
    @SerialEntry
    public boolean shapedGlassRandomSelection = false;
    @SerialEntry
    public boolean shapedGlassEnchantingTable = false;
    @SerialEntry
    public boolean shapedGlassBookOffer = false;
    @SerialEntry
    public float shapedGlassDamageMultiplier = 2.0f;
    @SerialEntry
    public int shapedGlassItemDamage = 20;

    // Skywalker options
    @SerialEntry
    public boolean skywalkerEnabled = true;
    @SerialEntry
    public boolean skywalkerRandomSelection = false;
    @SerialEntry
    public boolean skywalkerEnchantingTable = false;
    @SerialEntry
    public boolean skywalkerBookOffer = false;
    @SerialEntry
    public int skywalkerRadius = 0;
    @SerialEntry
    public int skywalkerCloudDuration = 25;
    @SerialEntry
    public boolean skywalkerHalfUltrawarmDuration = true;

    // Speed Blessing options
    @SerialEntry
    public boolean speedBlessingEnabled = true;
    @SerialEntry
    public boolean speedBlessingRandomSelection = true;
    @SerialEntry
    public boolean speedBlessingEnchantingTable = true;
    @SerialEntry
    public boolean speedBlessingBookOffer = true;
    @SerialEntry
    public float speedBlessingSpeedBoost = 0.2f;

    // Strip Miner options
    @SerialEntry
    public boolean stripMinerEnabled = true;
    @SerialEntry
    public boolean stripMinerRandomSelection = false;
    @SerialEntry
    public boolean stripMinerEnchantingTable = false;
    @SerialEntry
    public boolean stripMinerBookOffer = false;
    @SerialEntry
    public int stripMinerRadius = 1;
}
