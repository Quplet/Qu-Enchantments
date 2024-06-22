package qu.quEnchantments.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;

import java.util.Optional;

public class ModTradeRegistry {

    public static void initializeModTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 3, factories -> {
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 5), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_0), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 6), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_1), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 7), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_2), 3, 10, 0.05f)));
        });

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 6), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_3), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 7), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_4), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 8), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_5), 3, 10, 0.05f)));
            factories.add(((entity, random) -> {
                Optional<? extends RegistryEntry<Enchantment>> optional = entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(ModEnchantments.OMEN_OF_IMMUNITY);
                return optional.map(enchantmentRegistryEntry -> new TradeOffer(new TradedItem(Items.EMERALD, 10), Optional.of(new TradedItem(Items.BOOK)), EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantmentRegistryEntry, 1)), 3, 10, 0.05f))
                        .orElse(new TradeOffer(new TradedItem(Items.EMERALD, 5), Optional.of(new TradedItem(Items.AMETHYST_SHARD)), new ItemStack(ModItems.RUNE_0), 3, 10, 0.05f));
            }));
        });
    }
}
