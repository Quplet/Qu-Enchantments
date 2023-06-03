package qu.quEnchantments.enchantments.weapon;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.CompoundEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class LightningBoundEnchantment extends CompoundEnchantment {

    private static final ModConfig.LightningBoundOptions CONFIG = QuEnchantments.getConfig().lightningBoundOptions;

    public LightningBoundEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot ... slotTypes) {
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
