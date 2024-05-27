package qu.quEnchantments.util;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemGroups;
import qu.quEnchantments.callbacks.LivingEntityEvents;
import qu.quEnchantments.enchantments.rune.AggressionBlessingEnchantment;
import qu.quEnchantments.enchantments.rune.SpeedBlessingEnchantment;
import qu.quEnchantments.items.ModItems;

public class ModEvents {

    public static void RegisterModEvents() {

        LivingEntityEvents.ON_TICK_EVENT.register(livingEntity -> {
            if (livingEntity.getWorld().isClient) return;

            EntityAttributeInstance instance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (instance != null && instance.getModifier(SpeedBlessingEnchantment.SPEED_BOOST.uuid()) != null) {
                instance.removeModifier(SpeedBlessingEnchantment.SPEED_BOOST);
            }
            instance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
            if (instance != null && instance.getModifier(AggressionBlessingEnchantment.ATTACK_BOOST.uuid()) != null) {
                instance.removeModifier(AggressionBlessingEnchantment.ATTACK_BOOST);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(ModItems.RUNE_0);
            entries.add(ModItems.RUNE_1);
            entries.add(ModItems.RUNE_2);
            entries.add(ModItems.RUNE_3);
            entries.add(ModItems.RUNE_4);
            entries.add(ModItems.RUNE_5);
            entries.add(ModItems.RUNE_6);
            entries.add(ModItems.RUNE_7);
            entries.add(ModItems.RUNE_8);
        });
    }
}
