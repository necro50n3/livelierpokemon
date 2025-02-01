package necro.livelier.pokemon.common.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ParalysisEffect extends MobEffect {
    public ParalysisEffect() {
        super(MobEffectCategory.HARMFUL, 16767232);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.withDefaultNamespace("effect.slowness"), -0.15000000596046448, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (Math.random() < 0.1 * (amplifier + 1)) entity.setDeltaMovement(0, 0, 0);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }
}
