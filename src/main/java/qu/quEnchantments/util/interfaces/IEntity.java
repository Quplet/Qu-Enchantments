package qu.quEnchantments.util.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface IEntity {

    int quEnchantments$getInaneTicks();

    void quEnchantments$setInaneTicks(int value);

    static boolean isCreativePlayer(Entity entity) {
        return entity instanceof PlayerEntity player && player.getAbilities().creativeMode;
    }

}
