package qu.quEnchantments.enchantments.shield;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.EntityHitResult;
import qu.quEnchantments.QuEnchantments;
import qu.quEnchantments.enchantments.ModEnchantments;
import qu.quEnchantments.enchantments.QuEnchantment;
import qu.quEnchantments.util.config.ModConfig;

public class ReflectionEnchantment extends QuEnchantment {

    private static final ModConfig.ReflectionOptions CONFIG = QuEnchantments.getConfig().reflectionOptions;

    public ReflectionEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 3 : 0;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }

    @Override
    public boolean isAvailableForEnchantingTable() {
        return CONFIG.enchantingTable;
    }

    public static boolean reflect(PersistentProjectileEntity projectile, EntityHitResult result) {
        if (!projectile.world.isClient) {
            Entity entity = result.getEntity();
            if (entity != null && !entity.getWorld().isClient() && entity instanceof PlayerEntity player) {
                DamageSource damageSource;
                if (projectile instanceof TridentEntity) {
                    damageSource = DamageSource.trident(projectile, projectile.getOwner() == null ? projectile : projectile.getOwner());
                } else {
                    damageSource = DamageSource.arrow(projectile, projectile.getOwner() == null ? projectile : projectile.getOwner());
                }
                int i;
                if (player.blockedByShield(damageSource) && (i = EnchantmentHelper.getEquipmentLevel(ModEnchantments.REFLECTION, player)) > 0) {
                    projectile.setVelocity(player, player.getPitch() - 1.0f, player.getYaw(), 0.0f, (float) projectile.getVelocity().length(), 25.0f * (CONFIG.divergence * 0.1f) / i);
                    return true;
                }
            }
        }
        return false;
    }
}
