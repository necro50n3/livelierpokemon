package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;

public class DamageRevengeGoal extends PokemonRevengeGoal {
    private final float damage;

    public DamageRevengeGoal(PokemonEntity pokemonEntity, float damage) {
        super(pokemonEntity);
        this.damage = damage;
    }

    @Override
    public void doRevenge() {
        DamageSource lastDamageSource = this.pokemonEntity.getLastDamageSource();
        if (lastDamageSource == null) return;
        else if (this.targetMob == null) return;
        else if (lastDamageSource.is(DamageTypes.MOB_PROJECTILE)) return;
        else if (lastDamageSource.is(DamageTypes.ARROW)) return;
        else if (lastDamageSource.is(DamageTypes.THROWN)) return;

        this.targetMob.hurt(this.pokemonEntity.level().damageSources().thorns(this.pokemonEntity), this.damage);
    }
}
