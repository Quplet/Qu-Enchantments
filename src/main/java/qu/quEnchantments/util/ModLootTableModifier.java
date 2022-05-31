package qu.quEnchantments.util;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
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

    private static final Identifier WITCH_ID = new Identifier("minecraft", "entities/witch");

    private static final int NUM_CORRUPTED = 4;

    public static void ModifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, builder, setter) -> {

            if (END_CITY_TREASURE_ID.equals(id) || BASTION_BRIDGE_ID.equals(id) || BASTION_HOGLIN_STABLE_ID.equals(id) ||
                    BASTION_OTHER_ID.equals(id) || BASTION_TREASURE_ID.equals(id)) {
                float chance = 0.05f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(NUM_CORRUPTED))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
                        .build();

                builder.pool(pool);
            }

            if (RUINED_PORTAL_ID.equals(id) || NETHER_BRIDGE_ID.equals(id)) {
                float chance = 0.005f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(NUM_CORRUPTED))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
                        .build();

                builder.pool(pool);
            }

            if (WITCH_ID.equals(id)) {
                float chance = 0.005f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
                        .build();

                builder.pool(pool);
            }
        }));
    }
}
