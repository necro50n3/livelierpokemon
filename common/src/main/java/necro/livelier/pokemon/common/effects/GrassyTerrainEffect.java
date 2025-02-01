package necro.livelier.pokemon.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GrassyTerrainEffect extends MobEffect {
    public GrassyTerrainEffect() {
        super(MobEffectCategory.BENEFICIAL, 10092441);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.heal(1.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        return tick % 40 == 0;
    }
}
