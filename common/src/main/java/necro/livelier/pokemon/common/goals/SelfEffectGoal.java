package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class SelfEffectGoal extends EntityEffectGoal {
    public SelfEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int duration, int amplifier) {
        super(pokemonEntity, mobEffect, duration, amplifier);
    }

    public SelfEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect) {
        super(pokemonEntity, mobEffect);
    }

    @Override
    public List<LivingEntity> getEntities() {
        return Collections.singletonList(this.pokemonEntity);
    }

    public MobEffectInstance getEffect(Holder<MobEffect> mobEffect, int duration, int amplifier) {
        return new MobEffectInstance(mobEffect, duration, amplifier, false, false, false);
    }
}
