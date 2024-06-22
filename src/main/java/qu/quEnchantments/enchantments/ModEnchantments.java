package qu.quEnchantments.enchantments;

import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;

public class ModEnchantments {

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final RegistryKey<Enchantment> FREEZING_ASPECT = ModEnchantments.of("freezing_aspect");
    public static final RegistryKey<Enchantment> LEECHING_ASPECT = ModEnchantments.of("leeching_aspect");
    public static final RegistryKey<Enchantment> INANE_ASPECT = ModEnchantments.of("inane_aspect");
    public static final RegistryKey<Enchantment> MOLTEN_WALKER = ModEnchantments.of("molten_walker");
    public static final RegistryKey<Enchantment> BASHING = ModEnchantments.of("bashing");
    public static final RegistryKey<Enchantment> REFLECTION = ModEnchantments.of("reflection");
    public static final RegistryKey<Enchantment> ACCURACY = ModEnchantments.of("accuracy");
    public static final RegistryKey<Enchantment> ARROWS_FLIGHT = ModEnchantments.of("arrows_flight");
    public static final RegistryKey<Enchantment> SPEED_BLESSING = ModEnchantments.of("speed_blessing");
    public static final RegistryKey<Enchantment> REGENERATION_BLESSING = ModEnchantments.of("regeneration_blessing");
    public static final RegistryKey<Enchantment> AGGRESSION_BLESSING = ModEnchantments.of("aggression_blessing");
    public static final RegistryKey<Enchantment> FIDELITY = ModEnchantments.of("fidelity");

    // Corrupted Enchantments
    public static final RegistryKey<Enchantment> SHAPED_GLASS = ModEnchantments.of("shaped_glass");
    public static final RegistryKey<Enchantment> NIGHTBLOOD = ModEnchantments.of("nightblood");
    public static final RegistryKey<Enchantment> SKYWALKER = ModEnchantments.of("skywalker");
    public static final RegistryKey<Enchantment> ESSENCE_OF_ENDER = ModEnchantments.of("essence_of_ender");
    public static final RegistryKey<Enchantment> OMEN_OF_IMMUNITY = ModEnchantments.of("omen_of_immunity");
    public static final RegistryKey<Enchantment> STRIP_MINER = ModEnchantments.of("strip_miner");

    // Curses
    public static final RegistryKey<Enchantment> AGITATION_CURSE = ModEnchantments.of("agitation_curse");

    // Compound Enchantments
    public static final RegistryKey<Enchantment> LUCKY_MINER = ModEnchantments.of("lucky_miner");
    public static final RegistryKey<Enchantment> LIGHTNING_BOUND = ModEnchantments.of("lightning_bound");

    public static final ImmutableList<RegistryKey<Enchantment>> QU_ENCHANTMENTS = ImmutableList.of(FREEZING_ASPECT, LEECHING_ASPECT,
            INANE_ASPECT, MOLTEN_WALKER, BASHING, REFLECTION, ACCURACY, ARROWS_FLIGHT, SPEED_BLESSING, REGENERATION_BLESSING,
            AGGRESSION_BLESSING, FIDELITY, SHAPED_GLASS, NIGHTBLOOD, SKYWALKER, ESSENCE_OF_ENDER, OMEN_OF_IMMUNITY, STRIP_MINER,
            AGITATION_CURSE, LUCKY_MINER, LIGHTNING_BOUND);

//    public static final QuEnchantment FREEZING_ASPECT = register("freezing_aspect", new FreezingAspectEnchantment(Enchantment.properties(ItemTags.FIRE_ASPECT_ENCHANTABLE, 2,  CONFIG.freezingAspectEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 4, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment LEECHING_ASPECT = register("leeching_aspect", new LeechingAspectEnchantment(Enchantment.properties(ItemTags.FIRE_ASPECT_ENCHANTABLE, 2, CONFIG.leechingAspectEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 4, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment INANE_ASPECT = register("inane_aspect", new InaneAspectEnchantment(Enchantment.properties(ItemTags.FIRE_ASPECT_ENCHANTABLE, 2, CONFIG.inaneAspectEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 4, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment MOLTEN_WALKER = register("molten_walker", new MoltenWalkerEnchantment(Enchantment.properties(ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, CONFIG.moltenWalkerEnabled ? 2 : 0, Enchantment.leveledCost(10, 10), Enchantment.leveledCost(25, 10), 4, EquipmentSlot.FEET)));
//    public static final QuEnchantment BASHING = register("bashing", new BashingEnchantment(Enchantment.properties(ModTags.SHIELD_ENCHANTABLE, 2, CONFIG.bashingEnabled ? 1 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 4, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment REFLECTION = register("reflection", new ReflectionEnchantment(Enchantment.properties(ModTags.SHIELD_ENCHANTABLE, 2, CONFIG.reflectionEnabled ? 3 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 4, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment ACCURACY = register("accuracy", new AccuracyEnchantment(Enchantment.properties(ItemTags.CROSSBOW_ENCHANTABLE, 2, CONFIG.accuracyEnabled ? 2 : 0, Enchantment.leveledCost(12, 20), Enchantment.constantCost(50), 2, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment ARROWS_FLIGHT = register("arrows_flight", new ArrowsFlightEnchantment(Enchantment.properties(ItemTags.BOW_ENCHANTABLE, 2, CONFIG.arrowsFlightEnabled ? 2 : 0, Enchantment.leveledCost(12, 20), Enchantment.constantCost(50), 2, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment SPEED_BLESSING = register("speed_blessing", new SpeedBlessingEnchantment(Enchantment.properties(ModTags.RUNE_ENCHANTABLE, 5, CONFIG.speedBlessingEnabled ? 1 : 0, Enchantment.constantCost(5), Enchantment.constantCost(50), 8, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment REGENERATION_BLESSING = register("regeneration_blessing", new RegenerationBlessingEnchantment(Enchantment.properties(ModTags.RUNE_ENCHANTABLE, 5, CONFIG.regenerationBlessingEnabled ? 1 : 0, Enchantment.constantCost(5), Enchantment.constantCost(50), 8, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment AGGRESSION_BLESSING = register("aggression_blessing", new AggressionBlessingEnchantment(Enchantment.properties(ModTags.RUNE_ENCHANTABLE, 5, CONFIG.aggressionBlessingEnabled ? 1 : 0, Enchantment.constantCost(15), Enchantment.constantCost(50), 8, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment FIDELITY = register("fidelity", new FidelityEnchantment(Enchantment.properties(ModTags.HORSE_ARMOR_ENCHANTABLE, 10, CONFIG.fidelityEnabled ? 1 : 0, Enchantment.constantCost(20), Enchantment.constantCost(50), 8, EquipmentSlot.CHEST)));
//
//    // Corrupted Enchantments
//    public static final QuEnchantment SHAPED_GLASS = register("shaped_glass", new ShapedGlassEnchantment(Enchantment.properties(ItemTags.SHARP_WEAPON_ENCHANTABLE, 1, CONFIG.shapedGlassEnabled ? 5 : 0, Enchantment.leveledCost(10, 10), Enchantment.leveledCost(30, 10), 2, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment NIGHTBLOOD = register("nightblood", new NightbloodEnchantment(Enchantment.properties(ItemTags.SWORD_ENCHANTABLE, 1, CONFIG.nightbloodEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 5, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment SKYWALKER = register("skywalker", new SkywalkerEnchantment(Enchantment.properties(ItemTags.FOOT_ARMOR_ENCHANTABLE, 1, CONFIG.skywalkerEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 5, EquipmentSlot.FEET)));
//    public static final QuEnchantment ESSENCE_OF_ENDER = register("essence_of_ender", new EssenceOfEnderEnchantment(Enchantment.properties(ItemTags.ARMOR_ENCHANTABLE, 1, CONFIG.essenceOfEnderEnabled ? 3 : 0, Enchantment.leveledCost(10, 15), Enchantment.leveledCost(40, 15), 2, ALL_ARMOR)));
//    public static final QuEnchantment OMEN_OF_IMMUNITY = register("omen_of_immunity", new OmenOfImmunityEnchantment(Enchantment.properties(ModTags.RUNE_ENCHANTABLE, 1, CONFIG.omenOfImmunityEnabled ? 5 : 0, Enchantment.leveledCost(10, 10), Enchantment.leveledCost(30, 10), 2, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)));
//    public static final QuEnchantment STRIP_MINER = register("strip_miner", new StripMinerEnchantment(Enchantment.properties(ItemTags.MINING_ENCHANTABLE, 1 ,CONFIG.stripMinerEnabled ? 2 : 0, Enchantment.leveledCost(10, 20), Enchantment.leveledCost(60, 20), 5, EquipmentSlot.MAINHAND)));
//
//    // Curses
//    public static final QuEnchantment AGITATION_CURSE = register("agitation_curse", new AgitationCurseEnchantment(Enchantment.properties(ItemTags.ARMOR_ENCHANTABLE, 1, CONFIG.agitationCurseEnabled ? 1 : 0, Enchantment.constantCost(25), Enchantment.constantCost(50), 8, ALL_ARMOR)));
//
//    // Compound Enchantments
//    public static final QuEnchantment LUCKY_MINER = register("lucky_miner", new LuckyMinerEnchantment(Enchantment.properties(ItemTags.MINING_ENCHANTABLE, 2, CONFIG.luckyMinerEnabled ? 100 : 0, Enchantment.leveledCost(1, 1), Enchantment.leveledCost(5, 1), 1, EquipmentSlot.MAINHAND)));
//    public static final QuEnchantment LIGHTNING_BOUND = register("lightning_bound", new LightningBoundEnchantment(Enchantment.properties(ItemTags.SHARP_WEAPON_ENCHANTABLE, 2, CONFIG.lightningBoundEnabled ? 100 : 0, Enchantment.leveledCost(1, 1), Enchantment.leveledCost(5, 1), 1, EquipmentSlot.MAINHAND)));
//
//    public static final ImmutableList<QuEnchantment> QU_ENCHANTMENTS = ImmutableList.of(FREEZING_ASPECT, LEECHING_ASPECT,
//            INANE_ASPECT, MOLTEN_WALKER, BASHING, REFLECTION, ACCURACY, ARROWS_FLIGHT, SPEED_BLESSING, REGENERATION_BLESSING,
//            AGGRESSION_BLESSING, FIDELITY, SHAPED_GLASS, NIGHTBLOOD, SKYWALKER, ESSENCE_OF_ENDER, OMEN_OF_IMMUNITY, STRIP_MINER,
//            AGITATION_CURSE, LUCKY_MINER, LIGHTNING_BOUND);

//    private static QuEnchantment register(String name, QuEnchantment enchantment) {
//        return Registry.register(Registries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE, new Identifier(QuEnchantments.MOD_ID, name), enchantment);
//    }

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(QuEnchantments.MOD_ID, id));
    }

    public static void registerModEnchantments() {
        QuEnchantments.LOGGER.info("Registering ModEnchantments for " + QuEnchantments.MOD_ID);
    }
}
