package necro.livelier.pokemon.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ElectricTerrainEffect extends MobEffect {
    public ElectricTerrainEffect() {
        super(MobEffectCategory.BENEFICIAL, 16767232);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
