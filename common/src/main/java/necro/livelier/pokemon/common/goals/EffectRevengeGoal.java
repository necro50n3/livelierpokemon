package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectRevengeGoal extends PokemonRevengeGoal {
    protected final MobEffectInstance effect;
    protected final double triggerChance;

    public EffectRevengeGoal(PokemonEntity pokemonEntity, MobEffectInstance effect, double triggerChance) {
        super(pokemonEntity);
        this.effect = effect;
        this.triggerChance = triggerChance;
    }

    public EffectRevengeGoal(PokemonEntity pokemonEntity, Holder<MobEffect> effect, int duration, int amplifier, double triggerChance) {
        super(pokemonEntity);
        this.effect = new MobEffectInstance(effect, duration, amplifier);
        this.triggerChance = triggerChance;
    }

    public EffectRevengeGoal(PokemonEntity pokemonEntity, Holder<MobEffect> effect, int duration, double triggerChance) {
        this(pokemonEntity, effect, duration, 1, triggerChance);
    }

    public EffectRevengeGoal(PokemonEntity pokemonEntity, Holder<MobEffect> effect, double triggerChance) {
        this(pokemonEntity, effect, 100, triggerChance);
    }

    @Override
    public void doRevenge() {
        DamageSource lastDamageSource = this.pokemonEntity.getLastDamageSource();
        if (lastDamageSource == null) return;
        else if (this.targetMob == null) return;
        else if (!lastDamageSource.is(LivelierDamageType.MAKES_CONTACT)) return;

        if (Math.random() < this.triggerChance && this.targetMob.canBeAffected(this.effect)) {
            this.targetMob.addEffect(this.effect, this.pokemonEntity);
        }
    }
}
