package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class EntityEffectGoal extends Goal {
    protected final PokemonEntity pokemonEntity;
    protected final Holder<MobEffect> effect;
    protected final int duration;
    protected final int amplifier;
    protected final boolean includeSelf;
    protected int tick;

    public EntityEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int duration, int amplifier, boolean includeSelf) {
        this.pokemonEntity = pokemonEntity;
        this.effect = mobEffect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.includeSelf = includeSelf;
        this.tick = 0;
    }

    public EntityEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, int duration, int amplifier) {
        this(pokemonEntity, mobEffect, duration, amplifier, true);
    }

    public EntityEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect, boolean includeSelf) {
        this(pokemonEntity, mobEffect, 240, 0, includeSelf);
    }

    public EntityEffectGoal(PokemonEntity pokemonEntity, Holder<MobEffect> mobEffect) {
        this(pokemonEntity, mobEffect, 240, 0, true);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        if (--this.tick > 0) return;
        this.tick = this.adjustedTickDelay(120);

        for (LivingEntity entity : this.getEntities()) {
            this.applyEffect(entity);
        }
    }

    public List<LivingEntity> getEntities() {
        return TargetHelper.getNearbyEntities(this.pokemonEntity, 16, this.includeSelf);
    }

    public List<PokemonEntity> getPokemon() {
        return TargetHelper.getNearbyPokemon(this.pokemonEntity, 16, this.includeSelf);
    }

    public MobEffectInstance getEffect(Holder<MobEffect> mobEffect, int duration, int amplifier) {
        return new MobEffectInstance(mobEffect, duration, amplifier, false, true, true);
    }

    public void applyEffect(LivingEntity entity) {
        MobEffectInstance mobEffectInstance = this.getEffect(this.effect, this.duration, this.amplifier);
        if (entity.canBeAffected(mobEffectInstance)) entity.addEffect(mobEffectInstance, this.pokemonEntity);
    }
}
