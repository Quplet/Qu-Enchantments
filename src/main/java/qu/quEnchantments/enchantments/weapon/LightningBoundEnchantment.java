package qu.quEnchantments.enchantments.weapon;

import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.CompoundEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class LightningBoundEnchantment extends CompoundEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public LightningBoundEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.lightningBoundEnchantingTable;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.lightningBoundBookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.lightningBoundRandomSelection;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {
        World world;
        if ((world = user.getWorld()).isClient || !world.isSkyVisible(target.getBlockPos()) ||
                !passed(getLuck(user), world.random, aDouble -> aDouble * 100 >= 100.0 - level)) return;

        if (!world.isRaining()) {
            ((ServerWorld) world).setWeather(0, 100, true, true);
        }

        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
        if (lightning == null) return;

        lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(target.getBlockPos()));
        lightning.setChanneler(user instanceof ServerPlayerEntity ? (ServerPlayerEntity)user : null);
        world.spawnEntity(lightning);
    }
}
