package qu.quEnchantments.util.config;

import com.google.common.collect.ImmutableList;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import net.minecraft.text.Text;
import qu.quEnchantments.QuEnchantments;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigGenerator {

    private static final Option.Builder<Boolean> baseEnabled = Option.<Boolean>createBuilder()
            .name(Text.translatable(QuEnchantments.MOD_ID + ".config.general.enabled"))
            .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.general.enabled.desc")))
            .controller(TickBoxControllerBuilder::create);

    private static final Option.Builder<Boolean> baseRandomSelection = Option.<Boolean>createBuilder()
            .name(Text.translatable(QuEnchantments.MOD_ID + ".config.general.randomSelection"))
            .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.general.randomSelection.desc")))
            .controller(TickBoxControllerBuilder::create);

    private static final Option.Builder<Boolean> baseEnchantingTable = Option.<Boolean>createBuilder()
            .name(Text.translatable(QuEnchantments.MOD_ID + ".config.general.enchantingTable"))
            .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.general.enchantingTable.desc")))
            .controller(TickBoxControllerBuilder::create);

    private static final Option.Builder<Boolean> baseBookOffer = Option.<Boolean>createBuilder()
            .name(Text.translatable(QuEnchantments.MOD_ID + ".config.general.bookOffer"))
            .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.general.bookOffer.desc")))
            .controller(TickBoxControllerBuilder::create);

    private static final Collection<Option<?>> accuracyOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().accuracyEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().accuracyEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().accuracyRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().accuracyRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().accuracyEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().accuracyEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().accuracyBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().accuracyBookOffer = newVal
                    )
                    .build()
    );

    private static final Collection<Option<?>> aggressionBlessingOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "aggressionBlessing.attackSpeed",
                    0.8f,
                    () -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingAttackSpeed,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().aggressionBlessingAttackSpeed = newVal,
                    0.0f,
                    1.0f,
                    0.05f
            )
    );

    private static final Collection<Option<?>> agitationCurseOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().agitationCurseEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().agitationCurseEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().agitationCurseRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().agitationCurseRandomSelection = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().agitationCurseBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().agitationCurseBookOffer = newVal
                    )
                    .build(),

            createDoubleSliderOption(
                    "agitationCurse.radius",
                    16.0,
                    () -> ModConfig.CONFIG_HANDLER.instance().agitationCurseRadius,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().agitationCurseRadius = newVal,
                    0.0,
                    64.0,
                    0.25
            )
    );

    private static final Collection<Option<?>> arrowsFlightOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "arrowsFlight.arrowSpeed",
                    1.0f,
                    () -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightArrowSpeed,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().arrowsFlightArrowSpeed = newVal,
                    0.0f,
                    10.0f,
                    0.1f
            )
    );

    private static final Collection<Option<?>> bashingOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().bashingEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().bashingEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().bashingRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().bashingRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().bashingEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().bashingEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().bashingBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().bashingBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "bashing.knockbackStrength",
                    0.6f,
                    () -> ModConfig.CONFIG_HANDLER.instance().bashingKnockbackStrength,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().bashingKnockbackStrength = newVal,
                    0.0f,
                    10.0f,
                    0.1f
            )
    );

    private static final Collection<Option<?>> essenceOfEnderOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderBookOffer = newVal
                    )
                    .build(),

            createDoubleSliderOption(
                    "essenceOfEnder.teleportDistance",
                    5.0,
                    () -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderTeleportDistance,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().essenceOfEnderTeleportDistance = newVal,
                    2.0,
                    8.0,
                    0.25
            )
    );

    private static final Collection<Option<?>> fidelityOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().fidelityEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().fidelityEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().fidelityRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().fidelityRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().fidelityEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().fidelityEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().fidelityBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().fidelityBookOffer = newVal
                    )
                    .build()
    );

    private static final Collection<Option<?>> freezingAspectOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().freezingAspectEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().freezingAspectEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().freezingAspectRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().freezingAspectRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().freezingAspectEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().freezingAspectEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().freezingAspectBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().freezingAspectBookOffer = newVal
                    )
                    .build(),

            createIntegerSliderOption(
                    "freezingAspect.duration",
                    75,
                    () -> ModConfig.CONFIG_HANDLER.instance().freezingAspectDuration,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().freezingAspectDuration = newVal,
                    0,
                    500,
                    5
            )
    );

    private static final Collection<Option<?>> inaneAspectOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().inaneAspectEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().inaneAspectEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().inaneAspectRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().inaneAspectRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().inaneAspectEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().inaneAspectEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().inaneAspectBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().inaneAspectBookOffer = newVal
                    )
                    .build(),

            createIntegerSliderOption(
                    "inaneAspect.duration",
                    40,
                    () -> ModConfig.CONFIG_HANDLER.instance().inaneAspectDuration,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().inaneAspectDuration = newVal,
                    0,
                    500,
                    5
            )
    );

    private static final Collection<Option<?>> leechingAspectOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().leechingAspectEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().leechingAspectEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().leechingAspectRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().leechingAspectRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().leechingAspectEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().leechingAspectEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().leechingAspectBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().leechingAspectBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "leechingAspect.healing",
                    0.25f,
                    () -> ModConfig.CONFIG_HANDLER.instance().leechingAspectHealing,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().leechingAspectHealing = newVal,
                    0.0f,
                    20.0f,
                    0.25f
            )
    );

    private static final Collection<Option<?>> lightningBoundOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().lightningBoundEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().lightningBoundEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().lightningBoundRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().lightningBoundRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().lightningBoundEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().lightningBoundEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().lightningBoundBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().lightningBoundBookOffer = newVal
                    )
                    .build()
    );

    private static final Collection<Option<?>> luckyMinerOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().luckyMinerEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().luckyMinerEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().luckyMinerRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().luckyMinerRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().luckyMinerEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().luckyMinerEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().luckyMinerBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().luckyMinerBookOffer = newVal
                    )
                    .build()
    );

    private static final Collection<Option<?>> moltenWalkerOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerBookOffer = newVal
                    )
                    .build(),

            createIntegerSliderOption(
                    "moltenWalker.radius",
                    2,
                    () -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerRadius,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().moltenWalkerRadius = newVal,
                    0,
                    16,
                    1
            )
    );

    private static final Collection<Option<?>> nightbloodOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().nightbloodEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().nightbloodRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().nightbloodEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().nightbloodBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodBookOffer = newVal
                    )
                    .build(),

            createBooleanOption(
                    "nightblood.disablesExperience",
                    true,
                    () -> ModConfig.CONFIG_HANDLER.instance().nightbloodDisablesExperience,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodDisablesExperience = newVal
            ),

            createIntegerSliderOption(
                    "nightblood.witherDuration",
                    200,
                    () -> ModConfig.CONFIG_HANDLER.instance().nightbloodWitherDuration,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodWitherDuration = newVal,
                    0,
                    1000,
                    5
            ),

            createIntegerSliderOption(
                    "nightblood.witherAmplifier",
                    1,
                    () -> ModConfig.CONFIG_HANDLER.instance().nightbloodWitherAmplifier,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodWitherAmplifier = newVal,
                    1,
                    127,
                    1
            ),

            createFloatSliderOption(
                    "nightblood.drainRate",
                    1.0f,
                    () -> ModConfig.CONFIG_HANDLER.instance().nightbloodDrainRate,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().nightbloodDrainRate = newVal,
                    0.0f,
                    10.0f,
                    0.25f
            )
    );

    private static final Collection<Option<?>> omenOfImmunityOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityBookOffer = newVal
                    )
                    .build(),

            createBooleanOption(
                    "omenOfImmunity.breakOnNoDurability",
                    true,
                    () -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityBreakOnNoDurability,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().omenOfImmunityBreakOnNoDurability = newVal
            )
    );

    private static final Collection<Option<?>> reflectionOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().reflectionEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().reflectionEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().reflectionRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().reflectionRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().reflectionEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().reflectionEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().reflectionBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().reflectionBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "reflection.divergence",
                    1.0f,
                    () -> ModConfig.CONFIG_HANDLER.instance().reflectionDivergence,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().reflectionDivergence = newVal,
                    0.0f,
                    5.0f,
                    0.1f
            )
    );

    private static final Collection<Option<?>> regenerationBlessingOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().regenerationBlessingBookOffer = newVal
                    )
                    .build()
    );

    private static final Collection<Option<?>> shapedGlassOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "shapedGlass.damageMultiplier",
                    2.0f,
                    () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassDamageMultiplier,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassDamageMultiplier = newVal,
                    0.0f,
                    20.0f,
                    0.25f
            ),

            createIntegerSliderOption(
                    "shapedGlass.itemDamage",
                    20,
                    () -> ModConfig.CONFIG_HANDLER.instance().shapedGlassItemDamage,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().shapedGlassItemDamage = newVal,
                    0,
                    1000,
                    1
            )
    );

    private static final Collection<Option<?>> skywalkerOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().skywalkerEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().skywalkerRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().skywalkerEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().skywalkerBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerBookOffer = newVal
                    )
                    .build(),

            createIntegerSliderOption(
                    "skywalker.radius",
                    0,
                    () -> ModConfig.CONFIG_HANDLER.instance().skywalkerRadius,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerRadius = newVal,
                    0,
                    16,
                    1
            ),

            createIntegerSliderOption(
                    "skywalker.cloudDuration",
                    25,
                    () -> ModConfig.CONFIG_HANDLER.instance().skywalkerCloudDuration,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerCloudDuration = newVal,
                    0,
                    1000,
                    5
            ),

            createBooleanOption(
                    "skywalker.halfUltrawarmDuration",
                    true,
                    () -> ModConfig.CONFIG_HANDLER.instance().skywalkerHalfUltrawarmDuration,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().skywalkerHalfUltrawarmDuration = newVal
            )
    );

    private static final Collection<Option<?>> speedBlessingOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().speedBlessingEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().speedBlessingEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().speedBlessingRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().speedBlessingRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().speedBlessingEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().speedBlessingEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().speedBlessingBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().speedBlessingBookOffer = newVal
                    )
                    .build(),

            createFloatSliderOption(
                    "speedBlessing.speedBoost",
                    0.2f,
                    () -> ModConfig.CONFIG_HANDLER.instance().speedBlessingSpeedBoost,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().speedBlessingSpeedBoost = newVal,
                    0.0f,
                    10.0f,
                    0.1f
            )
    );

    private static final Collection<Option<?>> stripMinerOptions = ImmutableList.of(
            baseEnabled.binding(
                            true,
                            () -> ModConfig.CONFIG_HANDLER.instance().stripMinerEnabled,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().stripMinerEnabled = newVal
                    )
                    .build(),

            baseRandomSelection.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().stripMinerRandomSelection,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().stripMinerRandomSelection = newVal
                    )
                    .build(),

            baseEnchantingTable.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().stripMinerEnchantingTable,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().stripMinerEnchantingTable = newVal
                    )
                    .build(),

            baseBookOffer.binding(
                            false,
                            () -> ModConfig.CONFIG_HANDLER.instance().stripMinerBookOffer,
                            newVal -> ModConfig.CONFIG_HANDLER.instance().stripMinerBookOffer = newVal
                    )
                    .build(),

            createIntegerSliderOption(
                    "stripMiner.radius",
                    1,
                    () -> ModConfig.CONFIG_HANDLER.instance().stripMinerRadius,
                    newVal -> ModConfig.CONFIG_HANDLER.instance().stripMinerRadius = newVal,
                    0,
                    16,
                    1
            )
    );

    private static final Collection<OptionGroup> optionGroups = ImmutableList.of(
            createOptionsGroup("accuracy", accuracyOptions),
            createOptionsGroup("aggressionBlessing", aggressionBlessingOptions),
            createOptionsGroup("agitationCurse", agitationCurseOptions),
            createOptionsGroup("arrowsFlight", arrowsFlightOptions),
            createOptionsGroup("bashing", bashingOptions),
            createOptionsGroup("essenceOfEnder", essenceOfEnderOptions),
            createOptionsGroup("fidelity", fidelityOptions),
            createOptionsGroup("freezingAspect", freezingAspectOptions),
            createOptionsGroup("inaneAspect", inaneAspectOptions),
            createOptionsGroup("leechingAspect", leechingAspectOptions),
            createOptionsGroup("lightningBound", lightningBoundOptions),
            createOptionsGroup("luckyMiner", lightningBoundOptions),
            createOptionsGroup("moltenWalker", moltenWalkerOptions),
            createOptionsGroup("nightblood", nightbloodOptions),
            createOptionsGroup("omenOfImmunity", omenOfImmunityOptions),
            createOptionsGroup("reflection", reflectionOptions),
            createOptionsGroup("regenerationBlessing", regenerationBlessingOptions),
            createOptionsGroup("shapedGlass", shapedGlassOptions),
            createOptionsGroup("skywalker", skywalkerOptions),
            createOptionsGroup("speedBlessing", speedBlessingOptions),
            createOptionsGroup("stripMiner", stripMinerOptions)
    );

    public static YetAnotherConfigLib generateConfig() {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable(QuEnchantments.MOD_ID + ".config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(QuEnchantments.MOD_ID + ".config.category.enchantments"))
                        .groups(optionGroups)
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(QuEnchantments.MOD_ID + ".config.category.items"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable(QuEnchantments.MOD_ID + ".config.rune.group.title"))
                                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.rune.group.desc")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(QuEnchantments.MOD_ID + ".config.rune.breakOnNoDurability"))
                                        .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config.rune.breakOnNoDurability.desc")))
                                        .binding(
                                                false,
                                                () -> ModConfig.CONFIG_HANDLER.instance().runeBreakOnNoDurability,
                                                newVal -> ModConfig.CONFIG_HANDLER.instance().runeBreakOnNoDurability = newVal
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .save(() -> ModConfig.CONFIG_HANDLER.save())
                .build();
    }

    private static Option<Boolean> createBooleanOption(String name, boolean def, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Text.translatable(QuEnchantments.MOD_ID + ".config." + name))
                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".desc")))
                .controller(TickBoxControllerBuilder::create)
                .binding(def, getter, setter)
                .build();
    }

    private static Option<Float> createFloatSliderOption(
            String name,
            float def,
            Supplier<Float> getter,
            Consumer<Float> setter,
            float min,
            float max,
            float step
    ) {
        return Option.<Float>createBuilder()
                .name(Text.translatable(QuEnchantments.MOD_ID + ".config." + name))
                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".desc")))
                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                        .range(min, max)
                        .step(step)
                        .formatValue(value -> Text.literal("" + value)))
                .binding(
                        def,
                        getter,
                        setter
                )
                .build();
    }

    private static Option<Integer> createIntegerSliderOption(
            String name,
            int def,
            Supplier<Integer> getter,
            Consumer<Integer> setter,
            int min,
            int max,
            int step
    ) {
        return Option.<Integer>createBuilder()
                .name(Text.translatable(QuEnchantments.MOD_ID + ".config." + name))
                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".desc")))
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(min, max)
                        .step(step)
                        .formatValue(value -> Text.literal("" + value)))
                .binding(
                        def,
                        getter,
                        setter
                )
                .build();
    }

    private static Option<Double> createDoubleSliderOption(
            String name,
            double def,
            Supplier<Double> getter,
            Consumer<Double> setter,
            double min,
            double max,
            double step
    ) {
        return Option.<Double>createBuilder()
                .name(Text.translatable(QuEnchantments.MOD_ID + ".config." + name))
                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".desc")))
                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                        .range(min, max)
                        .step(step)
                        .formatValue(value -> Text.literal("" + value)))
                .binding(
                        def,
                        getter,
                        setter
                )
                .build();
    }

    private static OptionGroup createOptionsGroup(String name, Collection<Option<?>> options) {
        return OptionGroup.createBuilder()
                .name(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".group.title"))
                .description(OptionDescription.of(Text.translatable(QuEnchantments.MOD_ID + ".config." + name + ".group.desc")))
                .options(options)
                .build();
    }
}
