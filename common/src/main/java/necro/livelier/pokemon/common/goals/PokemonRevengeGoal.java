package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public abstract class PokemonRevengeGoal extends HurtByTargetGoal {
    protected final PokemonEntity pokemonEntity;
    private int lastHurt;

    public PokemonRevengeGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canUse() {
        return this.pokemonEntity.getLastHurtByMobTimestamp() != this.lastHurt && this.pokemonEntity.getLastHurtByMob() != null;
    }

    @Override
    public void start() {
        this.lastHurt = this.pokemonEntity.getLastHurtByMobTimestamp();
        super.start();
        this.doRevenge();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    public abstract void doRevenge();
}
