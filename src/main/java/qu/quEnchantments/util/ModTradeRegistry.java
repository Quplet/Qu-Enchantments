package qu.quEnchantments.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.items.ModItems;

public class ModTradeRegistry {

    public static void initializeModTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 3, factories -> {
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_0), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_1), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 7), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_2), 3, 10, 0.05f)));
        });

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_3), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 7), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_4), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 8), new ItemStack(Items.AMETHYST_SHARD), new ItemStack(ModItems.RUNE_5), 3, 10, 0.05f)));
            factories.add(((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 10), new ItemStack(Items.BOOK), EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.OMEN_OF_IMMUNITY, 1)), 3, 10, 0.05f)));
        });
    }
}
