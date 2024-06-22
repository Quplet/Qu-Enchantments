package qu.quEnchantments.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;
import qu.quEnchantments.mixin.LootTablesInvoker;

public class ModLootTableModifier {
    private static final Identifier WITCH_ID = Identifier.ofVanilla("entities/witch");

    public static final RegistryKey<LootTable> LUCKY_MINER_OVERWORLD = LootTablesInvoker.invokeRegister(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(QuEnchantments.MOD_ID, "gameplay/mining/lucky_miner_overworld")));
    public static final RegistryKey<LootTable> LUCKY_MINER_NETHER = LootTablesInvoker.invokeRegister(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(QuEnchantments.MOD_ID, "gameplay/mining/lucky_miner_nether")));

    public static void ModifyLootTables() {
        LootTableEvents.MODIFY.register((key, builder, source) -> {

            if (!source.isBuiltin()) return;

//            if (key == LootTables.END_CITY_TREASURE_CHEST || key == LootTables.BASTION_BRIDGE_CHEST ||
//                    key == LootTables.BASTION_HOGLIN_STABLE_CHEST || key == LootTables.BASTION_OTHER_CHEST || key == LootTables.BASTION_TREASURE_CHEST) {
//                float chance = 0.2f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(Registries., ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
//                        .build();
//                tableBuilder.pool(pool);
//
//                chance = 0.08f;
//                pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(ModItems.RUNE_6)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_7)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_8)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if (key == LootTables.ANCIENT_CITY_CHEST || key == LootTables.ANCIENT_CITY_ICE_BOX_CHEST) {
//                float chance = 0.01f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
//                        .build();
//                tableBuilder.pool(pool);
//
//                chance = 0.05f;
//                pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(ModItems.RUNE_6)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_7)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_8)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if (key == LootTables.RUINED_PORTAL_CHEST || key == LootTables.NETHER_BRIDGE_CHEST) {
//                float chance = 0.003f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
//                        .build();
//                tableBuilder.pool(pool);
//
//                chance = 0.05f;
//                pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(ModItems.RUNE_3)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_4)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_5)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if (key.getValue().equals(WITCH_ID)) {
//                float chance = 0.005f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(1))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SHAPED_GLASS, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.NIGHTBLOOD, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.SKYWALKER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.ESSENCE_OF_ENDER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.OMEN_OF_IMMUNITY, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(Items.BOOK)
//                                .conditionally(RandomChanceLootCondition.builder(chance))
//                                .apply(new SetEnchantmentsLootFunction.Builder(false)
//                                        .enchantment(ModEnchantments.STRIP_MINER, ConstantLootNumberProvider.create(1))))
//                        .with(ItemEntry.builder(ModItems.RUNE_0)
//                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
//                        .with(ItemEntry.builder(ModItems.RUNE_1)
//                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
//                        .with(ItemEntry.builder(ModItems.RUNE_2)
//                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if(key == LootTables.BURIED_TREASURE_CHEST) {
//                float chance = 0.1f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(1))
//                        .with(ItemEntry.builder(ModItems.RUNE_0)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_1)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_2)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if (key == LootTables.PILLAGER_OUTPOST_CHEST || key == LootTables.WOODLAND_MANSION_CHEST) {
//                float chance = 0.1f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(ModItems.RUNE_3)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_4)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_5)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
//
//            if (key == LootTables.STRONGHOLD_LIBRARY_CHEST) {
//                float chance = 0.15f;
//                LootPool pool = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(2))
//                        .with(ItemEntry.builder(ModItems.RUNE_6)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_7)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .with(ItemEntry.builder(ModItems.RUNE_8)
//                                .conditionally(RandomChanceLootCondition.builder(chance)))
//                        .build();
//                tableBuilder.pool(pool);
//            }
        });
    }
}
