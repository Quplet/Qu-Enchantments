package qu.quEnchantments.enchantments;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.armor.*;
import qu.quEnchantments.enchantments.rune.AggressionBlessingEnchantment;
import qu.quEnchantments.enchantments.rune.OmenOfImmunityEnchantment;
import qu.quEnchantments.enchantments.rune.RegenerationBlessingEnchantment;
import qu.quEnchantments.enchantments.rune.SpeedBlessingEnchantment;
import qu.quEnchantments.enchantments.shield.BashingEnchantment;
import qu.quEnchantments.enchantments.shield.ReflectionEnchantment;
import qu.quEnchantments.enchantments.tool.LuckyMinerEnchantment;
import qu.quEnchantments.enchantments.tool.StripMinerEnchantment;
import qu.quEnchantments.enchantments.weapon.*;

public class ModEnchantments {

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private static final EnchantmentTarget SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "SHIELD");
    private static final EnchantmentTarget RUNE = ClassTinkerers.getEnum(EnchantmentTarget.class, "RUNE");
    private static final EnchantmentTarget HORSE_ARMOR = ClassTinkerers.getEnum(EnchantmentTarget.class, "HORSE_ARMOR");

    public static final QuEnchantment FREEZING_ASPECT = register("freezing_aspect", new FreezingAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final QuEnchantment LEECHING_ASPECT = register("leeching_aspect", new LeechingAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final QuEnchantment INANE_ASPECT = register("inane_aspect", new InaneAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final QuEnchantment MOLTEN_WALKER = register("molten_walker", new MoltenWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
    public static final QuEnchantment BASHING = register("bashing", new BashingEnchantment(Enchantment.Rarity.COMMON, SHIELD, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment REFLECTION = register("reflection", new ReflectionEnchantment(Enchantment.Rarity.COMMON, SHIELD, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment ACCURACY = register("accuracy", new AccuracyEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND));
    public static final QuEnchantment ARROWS_FLIGHT = register("arrows_flight", new ArrowsFlightEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND));
    public static final QuEnchantment SPEED_BLESSING = register("speed_blessing", new SpeedBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment REGENERATION_BLESSING = register("regeneration_blessing", new RegenerationBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment AGGRESSION_BLESSING = register("aggression_blessing", new AggressionBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment FIDELITY = register("fidelity", new FidelityEnchantment(Enchantment.Rarity.RARE, HORSE_ARMOR, EquipmentSlot.CHEST));


    // Corrupted Enchantments
    public static final QuEnchantment SHAPED_GLASS = register("shaped_glass", new ShapedGlassEnchantment(CorruptedEnchantment.EnchantmentType.DAMAGE, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final QuEnchantment NIGHTBLOOD = register("nightblood", new NightbloodEnchantment(CorruptedEnchantment.EnchantmentType.ASPECT, Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment SKYWALKER = register("skywalker", new SkywalkerEnchantment(CorruptedEnchantment.EnchantmentType.WALKER, Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final QuEnchantment ESSENCE_OF_ENDER = register("essence_of_ender", new EssenceOfEnderEnchantment(CorruptedEnchantment.EnchantmentType.THORNS, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, ALL_ARMOR));
    public static final QuEnchantment OMEN_OF_IMMUNITY = register("omen_of_immunity", new OmenOfImmunityEnchantment(CorruptedEnchantment.EnchantmentType.RUNE, Enchantment.Rarity.VERY_RARE, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final QuEnchantment STRIP_MINER = register("strip_miner", new StripMinerEnchantment(CorruptedEnchantment.EnchantmentType.PICKAXE_DROP, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    public static final QuEnchantment AGITATION_CURSE = register("agitation_curse", new AgitationCurseEnchantment(Enchantment.Rarity.VERY_RARE, ALL_ARMOR));

    // Compound Enchantments
    public static final QuEnchantment LUCKY_MINER = register("lucky_miner", new LuckyMinerEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));
    public static final QuEnchantment LIGHTNING_BOUND = register("lightning_bound", new LightningBoundEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final ImmutableList<QuEnchantment> QU_ENCHANTMENTS = ImmutableList.of(FREEZING_ASPECT, LEECHING_ASPECT,
            INANE_ASPECT, MOLTEN_WALKER, BASHING, REFLECTION, ACCURACY, ARROWS_FLIGHT, SPEED_BLESSING, REGENERATION_BLESSING,
            AGGRESSION_BLESSING, FIDELITY, SHAPED_GLASS, NIGHTBLOOD, SKYWALKER, ESSENCE_OF_ENDER, OMEN_OF_IMMUNITY, STRIP_MINER,
            AGITATION_CURSE);

    private static QuEnchantment register(String name, QuEnchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(QuEnchantments.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        QuEnchantments.LOGGER.info("Registering ModEnchantments for " + QuEnchantments.MOD_ID);
        //ItemGroups.COMBAT.setEnchantments(EnchantmentTarget.VANISHABLE, EnchantmentTarget.ARMOR, EnchantmentTarget.ARMOR_FEET, EnchantmentTarget.ARMOR_HEAD, EnchantmentTarget.ARMOR_LEGS, EnchantmentTarget.ARMOR_CHEST, EnchantmentTarget.BOW, EnchantmentTarget.WEAPON, EnchantmentTarget.WEARABLE, EnchantmentTarget.BREAKABLE, EnchantmentTarget.TRIDENT, EnchantmentTarget.CROSSBOW, SHIELD, RUNE, HORSE_ARMOR);
    }
}
