package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;

public class FlameBodyGoal extends PokemonRevengeGoal {
    private final float triggerChance;
    private final int duration;

    public FlameBodyGoal(PokemonEntity pokemonEntity, float triggerChance, int duration) {
        super(pokemonEntity);
        this.triggerChance = triggerChance;
        this.duration = duration;
    }

    @Override
    public void doRevenge() {
        DamageSource lastDamageSource = this.pokemonEntity.getLastDamageSource();
        if (lastDamageSource == null) return;
        else if (this.targetMob == null) return;
        else if (lastDamageSource.is(DamageTypes.MOB_PROJECTILE)) return;
        else if (lastDamageSource.is(DamageTypes.ARROW)) return;
        else if (lastDamageSource.is(DamageTypes.THROWN)) return;

        if (Math.random() < this.triggerChance && !this.targetMob.fireImmune()) {
            this.targetMob.igniteForTicks(this.duration);
        }
    }
}
