package qu.quEnchantments.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;

public interface LivingEntityEvents {
    Event<OnLivingEntityTick> ON_TICK_EVENT = EventFactory.createArrayBacked(OnLivingEntityTick.class,
            (listeners) -> livingEntity -> {
                for (OnLivingEntityTick listener : listeners) {
                    listener.onTick(livingEntity);
                }
            });

    Event<OnLivingEntityBlock> ON_BLOCK_EVENT = EventFactory.createArrayBacked(OnLivingEntityBlock.class,
            (listeners) -> (source, entity) -> {
                for (OnLivingEntityBlock listener : listeners) {
                    listener.onBlock(source, entity);
                }
            });

    Event<OnApplyMovementEffects> ON_MOVEMENT_EFFECTS_EVENT = EventFactory.createArrayBacked(OnApplyMovementEffects.class,
            (listeners) -> (entity, blockPos) -> {
                for (OnApplyMovementEffects listener : listeners) {
                    listener.onAffect(entity, blockPos);
                }
            });

    Event<OnAttack> ON_ATTACK_EVENT = EventFactory.createArrayBacked(OnAttack.class,
            listeners -> (entity, attacker) -> {
                for (OnAttack listener : listeners) {
                    listener.onAttack(entity, attacker);
                }
            });


    interface OnLivingEntityTick {
        void onTick(LivingEntity livingEntity);
    }

    interface OnLivingEntityBlock {
        void onBlock(DamageSource source, LivingEntity entity);
    }

    interface OnApplyMovementEffects {
        void onAffect(LivingEntity entity, BlockPos blockPos);
    }

    interface OnAttack {
        void onAttack(Entity target, LivingEntity attacker);
    }
}
