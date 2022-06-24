package qu.quEnchantments.enchantments;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModEnchantments {

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private static final EnchantmentTarget SHIELD = ClassTinkerers.getEnum(EnchantmentTarget.class, "SHIELD");
    private static final EnchantmentTarget RUNE = ClassTinkerers.getEnum(EnchantmentTarget.class, "RUNE");
    private static final EnchantmentTarget HORSE_ARMOR = ClassTinkerers.getEnum(EnchantmentTarget.class, "HORSE_ARMOR");

    public static final Enchantment FREEZING_ASPECT = register("freezing_aspect", new FreezingAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment LEECHING_ASPECT = register("leeching_aspect", new LeechingAspectEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment MOLTEN_WALKER = register("molten_walker", new MoltenWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
    public static final Enchantment BASHING = register("bashing", new BashingEnchantment(Enchantment.Rarity.COMMON, SHIELD, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment REFLECTION = register("reflection", new ReflectionEnchantment(Enchantment.Rarity.COMMON, SHIELD, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment ACCURACY = register("accuracy", new ArrowEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND));
    public static final Enchantment ARROWS_FLIGHT = register("arrows_flight", new ArrowEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BOW, EquipmentSlot.MAINHAND));
    public static final Enchantment SPEED_BLESSING = register("speed_blessing", new SpeedBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment REGENERATION_BLESSING = register("regeneration_blessing", new RegenerationBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment AGGRESSION_BLESSING = register("aggression_blessing", new AggressionBlessingEnchantment(Enchantment.Rarity.COMMON, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment FIDELITY = register("fidelity", new FidelityEnchantment(Enchantment.Rarity.RARE, HORSE_ARMOR, EquipmentSlot.CHEST));


    // Corrupted Enchantments
    public static final Enchantment SHAPED_GLASS = register("shaped_glass", new ShapedGlassEnchantment(CorruptedEnchantment.EnchantmentType.DAMAGE, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment NIGHTBLOOD = register("nightblood", new NightbloodEnchantment(CorruptedEnchantment.EnchantmentType.ASPECT, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment SKYWALKER = register("skywalker", new SkywalkerEnchantment(CorruptedEnchantment.EnchantmentType.WALKER, Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final Enchantment ESSENCE_OF_ENDER = register("essence_of_ender", new EssenceOfEnderEnchantment(CorruptedEnchantment.EnchantmentType.THORNS, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, ALL_ARMOR));
    public static final Enchantment OMEN_OF_IMMUNITY = register("omen_of_immunity", new OmenOfImmunityEnchantment(CorruptedEnchantment.EnchantmentType.RUNE, Enchantment.Rarity.VERY_RARE, RUNE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    public static final Enchantment STRIP_MINER_ENCHANTMENT = register("strip_miner", new StripMinerEnchantment(CorruptedEnchantment.EnchantmentType.PICKAXE_DROP, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(QuEnchantments.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        QuEnchantments.LOGGER.info("Registering ModEnchantments for " + QuEnchantments.MOD_ID);
        ItemGroup.COMBAT.setEnchantments(EnchantmentTarget.VANISHABLE, EnchantmentTarget.ARMOR, EnchantmentTarget.ARMOR_FEET, EnchantmentTarget.ARMOR_HEAD, EnchantmentTarget.ARMOR_LEGS, EnchantmentTarget.ARMOR_CHEST, EnchantmentTarget.BOW, EnchantmentTarget.WEAPON, EnchantmentTarget.WEARABLE, EnchantmentTarget.BREAKABLE, EnchantmentTarget.TRIDENT, EnchantmentTarget.CROSSBOW, SHIELD, RUNE, HORSE_ARMOR);
    }
}
