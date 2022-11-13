package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CompoundEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class LighteningBoundEnchantment extends CompoundEnchantment {

    private static final ModConfig.LighteningBoundOptions CONFIG = QuEnchantments.getConfig().lighteningBoundOptions;

    public LighteningBoundEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? super.getMaxLevel() : 0;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, ItemStack stack, Entity target, int level) {
        if (user.world.isClient || !target.world.isSkyVisible(target.getBlockPos()) ||
                !passed(getLuck(user), user.world.random, aDouble -> aDouble * 100 >= 100.0 - level)) return;

        ServerWorld world = (ServerWorld) user.world;
        if (!world.isRaining()) {
            world.setWeather(0, 100, true, true);
        }

        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
        lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(target.getBlockPos()));
        lightning.setChanneler(user instanceof ServerPlayerEntity ? (ServerPlayerEntity)user : null);
        world.spawnEntity(lightning);
    }
}
