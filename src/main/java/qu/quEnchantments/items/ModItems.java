package qu.quEnchantments.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import qu.quEnchantments.QuEnchantments;

public class ModItems {

    public static Item RUNE = register("rune", new RuneItem(new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1).group(ItemGroup.COMBAT).maxDamage(600)));

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(QuEnchantments.MOD_ID, name), item);
    }

    public static void initializeModItems() {
        QuEnchantments.LOGGER.info("Registering ModItems for " + QuEnchantments.MOD_ID);
    }
}
