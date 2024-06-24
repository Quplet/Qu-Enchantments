package qu.quEnchantments.util.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface IEntity {

    int qu_Enchantments$getInaneTicks();

    void qu_Enchantments$setInaneTicks(int value);

    static boolean isCreativePlayer(Entity entity) {
        return entity instanceof PlayerEntity player && player.getAbilities().creativeMode;
    }

}
