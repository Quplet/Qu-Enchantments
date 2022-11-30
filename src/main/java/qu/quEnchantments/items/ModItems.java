package qu.quEnchantments.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import qu.quEnchantments.QuEnchantments;

public class ModItems {

    public static final Item RUNE_0 = register("rune_0", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(300)));
    public static final Item RUNE_1 = register("rune_1", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(350)));
    public static final Item RUNE_2 = register("rune_2", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(400)));
    public static final Item RUNE_3 = register("rune_3", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(450)));
    public static final Item RUNE_4 = register("rune_4", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(500)));
    public static final Item RUNE_5 = register("rune_5", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(550)));
    public static final Item RUNE_6 = register("rune_6", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(600)));
    public static final Item RUNE_7 = register("rune_7", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(650)));
    public static final Item RUNE_8 = register("rune_8", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).maxDamage(700)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(QuEnchantments.MOD_ID, name), item);
    }

    public static void initializeModItems() {
        QuEnchantments.LOGGER.info("Registering ModItems for " + QuEnchantments.MOD_ID);
    }
}
