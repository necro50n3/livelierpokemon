package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class KecleonGoal extends PokemonRevengeGoal {
    private final int duration;

    public KecleonGoal(PokemonEntity pokemonEntity, int duration) {
        super(pokemonEntity);
        this.duration = duration;
    }

    @Override
    public void doRevenge() {
        if (this.targetMob == null) return;
        MobEffectInstance effect = new MobEffectInstance(MobEffects.INVISIBILITY, this.duration, 1);
        if (this.pokemonEntity.canBeAffected(effect)) this.pokemonEntity.addEffect(effect, this.pokemonEntity);
    }
}
