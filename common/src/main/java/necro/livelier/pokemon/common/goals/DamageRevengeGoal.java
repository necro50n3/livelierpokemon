package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import net.minecraft.world.damagesource.DamageSource;

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
        else if (!lastDamageSource.is(LivelierDamageType.MAKES_CONTACT)) return;

        this.targetMob.hurt(this.pokemonEntity.level().damageSources().thorns(this.pokemonEntity), this.damage);
    }
}
