package qu.quEnchantments.util;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import qu.quEnchantments.enchantments.ModEnchantments;

public class ModLootTableModifier {
    private static final Identifier END_CITY_TREASURE_ID = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier BASTION_BRIDGE_ID = new Identifier("minecraft", "chests/bastion_bridge");
    private static final Identifier BASTION_HOGLIN_STABLE_ID = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_OTHER_ID = new Identifier("minecraft", "chests/bastion_other");
    private static final Identifier BASTION_TREASURE_ID = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier RUINED_PORTAL_ID = new Identifier("minecraft", "chests/ruined_portal");
    private static final Identifier NETHER_BRIDGE_ID = new Identifier("minecraft", "chests/nether_bridge");

    public static void ModifyLootTables() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {

            if (END_CITY_TREASURE_ID.equals(id) || BASTION_BRIDGE_ID.equals(id) || BASTION_HOGLIN_STABLE_ID.equals(id) ||
                    BASTION_OTHER_ID.equals(id) || BASTION_TREASURE_ID.equals(id)) {
                supplier.withPool(createLootPool(1, 0.05f, Items.BOOK, ModEnchantments.SHAPED_GLASS, 1));
                supplier.withPool(createLootPool(1, 0.05f, Items.BOOK, ModEnchantments.NIGHTBLOOD, 1));
            }

            if (RUINED_PORTAL_ID.equals(id) || NETHER_BRIDGE_ID.equals(id)) {
                supplier.withPool(createLootPool(1, 0.005f, Items.BOOK, ModEnchantments.SHAPED_GLASS, 1));
                supplier.withPool(createLootPool(1, 0.005f, Items.BOOK, ModEnchantments.NIGHTBLOOD, 1));
            }

        }));
    }

    /**
     * Will create a loot pool with the {@link Item} with a given number of rolls, chance, enchantment, and enchantment level.
     * @param rolls The number of rolls.
     * @param chance The chance of loot spawning per roll.
     * @param item The {@link Item} added to the pool.
     * @param enchantment The {@link Enchantment} added to the item.
     * @param level The level of the enchantment.
     * @return The created {@link LootPool}.
     */
    public static LootPool createLootPool(int rolls, float chance, Item item, Enchantment enchantment, int level) {
        return createLootPool(rolls, chance, item).withFunction(new SetEnchantmentsLootFunction.Builder(true)
                .enchantment(enchantment, ConstantLootNumberProvider.create(level)).build()).build();
    }

    /**
     * Will create a loot pool with the {@link Item} with a given number of rolls, chance, enchantment, and enchantment
     * level range.
     * @param rolls The number of rolls.
     * @param chance The chance of loot spawning per roll.
     * @param item The {@link Item} added to the pool.
     * @param enchantment The {@link Enchantment} added to the item.
     * @param minLevel The minimum level the enchantment can be added with.
     * @param maxLevel The maximum level the enchantment can be added with.
     * @return The created {@link LootPool}.
     */
    public static LootPool createLootPool(int rolls, float chance, Item item, Enchantment enchantment, int minLevel, int maxLevel) {
        return createLootPool(rolls, chance, item).withFunction(new SetEnchantmentsLootFunction.Builder(true)
                .enchantment(enchantment, UniformLootNumberProvider.create(minLevel, maxLevel)).build()).build();
    }

    /**
     * Will create a loot pool builder with the {@link Item} with a given number of rolls and chance.
     * @param rolls The number of rolls.
     * @param chance The chance of loot spawning per roll.
     * @param item The {@link Item} added to the pool.
     * @return The {@link FabricLootPoolBuilder}. Run {@link FabricLootPoolBuilder#build()} to generate the loot pool.
     */
    private static FabricLootPoolBuilder createLootPool(int rolls, float chance, Item item) {
        return FabricLootPoolBuilder.builder()
                .rolls(ConstantLootNumberProvider.create(rolls))
                .conditionally(RandomChanceLootCondition.builder(chance))
                .with(ItemEntry.builder(item));
    }
}
