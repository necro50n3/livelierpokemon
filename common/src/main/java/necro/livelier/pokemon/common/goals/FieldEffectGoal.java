package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.helpers.ParticleHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class FieldEffectGoal extends EntityEffectGoal {
    protected final int radius;
    protected final ParticleOptions particle;
    protected final Predicate<PokemonEntity> filter;

    public FieldEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int duration, int amplifier,
                           int radius, @Nullable ParticleOptions particle, Predicate<PokemonEntity> filter) {
        super(pokemonEntity, mobEffect, duration, amplifier);
        this.radius = radius;
        this.particle = particle;
        this.filter = filter;
    }

    public FieldEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int radius,
                           @Nullable ParticleOptions particle, Predicate<PokemonEntity> filter) {
        this(pokemonEntity, mobEffect, 240, 0, radius, particle, filter);
    }

    @Override
    public void tick() {
        if (this.tick % 10 == 0 && this.particle != null) ParticleHelper.doFieldEffect(this.pokemonEntity, this.radius, this.particle);
        if (--this.tick > 0) return;
        this.tick = this.adjustedTickDelay(120);

        for (PokemonEntity entity : this.getPokemon()) {
            this.applyEffect(entity);
        }
    }

    public List<PokemonEntity> getPokemon() {
        return TargetHelper.getNearbyPokemon(this.pokemonEntity, this.radius, this.filter);
    }
}
