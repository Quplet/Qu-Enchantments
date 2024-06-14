package qu.quEnchantments.enchantments.shield;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ReflectionEnchantment extends QuEnchantment {

    private static final ModConfig CONFIG = ModConfig.CONFIG_HANDLER.instance();

    public ReflectionEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.reflectionRandomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.reflectionBookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.reflectionEnchantingTable;
    }

    public static boolean reflect(PersistentProjectileEntity projectile, EntityHitResult result) {
        World world;
        if ((world = projectile.getWorld()).isClient || !(result.getEntity() instanceof PlayerEntity player)) return false;

        DamageSource damageSource = projectile instanceof TridentEntity ?
                world.getDamageSources().trident(projectile, projectile.getOwner() == null ? projectile : projectile.getOwner()) :
                world.getDamageSources().arrow(projectile, projectile.getOwner() == null ? projectile : projectile.getOwner());

        int reflectionLevel;
        if (!player.blockedByShield(damageSource) ||
                (reflectionLevel = EnchantmentHelper.getEquipmentLevel(ModEnchantments.REFLECTION, player)) == 0) return false;

        projectile.setVelocity(
                player,
                player.getPitch() - 1.0f,
                player.getYaw(), 0.0f,
                (float)projectile.getVelocity().length(),
                25.0f * (CONFIG.reflectionDivergence * 0.1f) / reflectionLevel
        );

        return true;
    }
}
