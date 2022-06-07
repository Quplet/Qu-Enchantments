package qu.quEnchantments.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModItems {

    public static Item RUNE_0 = register("rune_0", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(300)));
    public static Item RUNE_1 = register("rune_1", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(350)));
    public static Item RUNE_2 = register("rune_2", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(400)));
    public static Item RUNE_3 = register("rune_3", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(450)));
    public static Item RUNE_4 = register("rune_4", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(500)));
    public static Item RUNE_5 = register("rune_5", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(550)));
    public static Item RUNE_6 = register("rune_6", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(600)));
    public static Item RUNE_7 = register("rune_7", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(650)));
    public static Item RUNE_8 = register("rune_8", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(700)));

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(QuEnchantments.MOD_ID, name), item);
    }

    public static void initializeModItems() {
        QuEnchantments.LOGGER.info("Registering ModItems for " + QuEnchantments.MOD_ID);
    }
}
