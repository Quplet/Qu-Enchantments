package qu.quEnchantments.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;

public class ModLootTableModifier {
    private static final Identifier END_CITY_TREASURE_ID = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier BASTION_BRIDGE_ID = new Identifier("minecraft", "chests/bastion_bridge");
    private static final Identifier BASTION_HOGLIN_STABLE_ID = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_OTHER_ID = new Identifier("minecraft", "chests/bastion_other");
    private static final Identifier BASTION_TREASURE_ID = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier RUINED_PORTAL_ID = new Identifier("minecraft", "chests/ruined_portal");
    private static final Identifier NETHER_BRIDGE_ID = new Identifier("minecraft", "chests/nether_bridge");
    private static final Identifier ANCIENT_CITY_ID = new Identifier("minecraft", "chests/ancient_city");
    private static final Identifier ANCIENT_CITY_ICE_BOX_ID = new Identifier("minecraft", "chests/ancient_city_ice_box");
    private static final Identifier BURIED_TREASURE = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier PILLAGER_OUTPOST = new Identifier("minecraft", "chests/pillager_outpost");
    private static final Identifier STRONGHOLD_LIBRARY = new Identifier("minecraft", "chests/stronghold_library");
    private static final Identifier WOODLAND_MANSION = new Identifier("minecraft", "chests/woodland_mansion");

    private static final Identifier WITCH_ID = new Identifier("minecraft", "entities/witch");

    public static void ModifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, builder, setter) -> {
            if (END_CITY_TREASURE_ID.equals(id) || BASTION_BRIDGE_ID.equals(id) || BASTION_HOGLIN_STABLE_ID.equals(id) ||
                    BASTION_OTHER_ID.equals(id) || BASTION_TREASURE_ID.equals(id)) {
                float chance = 0.02f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
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
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
                        .build();
                builder.pool(pool);

                chance = 0.08f;
                pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.RUNE_6)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_7)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_8)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .build();
                builder.pool(pool);
            }

            if (ANCIENT_CITY_ID.equals(id) || ANCIENT_CITY_ICE_BOX_ID.equals(id)) {
                float chance = 0.01f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
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
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
                        .build();
                builder.pool(pool);

                chance = 0.05f;
                pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.RUNE_6)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_7)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_8)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .build();
                builder.pool(pool);
            }

            if (RUINED_PORTAL_ID.equals(id) || NETHER_BRIDGE_ID.equals(id)) {
                float chance = 0.003f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
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
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
                        .build();
                builder.pool(pool);

                chance = 0.05f;
                pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.RUNE_3)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_4)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_5)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
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
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.BOOK)
                                .conditionally(RandomChanceLootCondition.builder(chance))
                                .apply(new SetEnchantmentsLootFunction.Builder(false)
                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(ModItems.RUNE_0)
                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
                        .with(ItemEntry.builder(ModItems.RUNE_1)
                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
                        .with(ItemEntry.builder(ModItems.RUNE_2)
                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
                        .build();
                builder.pool(pool);
            }

            if(BURIED_TREASURE.equals(id)) {
                float chance = 0.1f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.RUNE_0)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_1)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_2)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .build();
                builder.pool(pool);
            }

            if (PILLAGER_OUTPOST.equals(id) || WOODLAND_MANSION.equals(id)) {
                float chance = 0.1f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.RUNE_3)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_4)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_5)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .build();
                builder.pool(pool);
            }

            if (STRONGHOLD_LIBRARY.equals(id)) {
                float chance = 0.15f;
                LootPool pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.RUNE_6)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_7)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .with(ItemEntry.builder(ModItems.RUNE_8)
                                .conditionally(RandomChanceLootCondition.builder(chance)))
                        .build();
                builder.pool(pool);
            }
        }));
    }
}
