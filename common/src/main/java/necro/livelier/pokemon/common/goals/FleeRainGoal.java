package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class FleeRainGoal extends FleeSunGoal {
    private final PokemonEntity pokemonEntity;

    public FleeRainGoal(PokemonEntity pokemonEntity, double speed)
    {
        super(pokemonEntity, speed);
        this.pokemonEntity = pokemonEntity;
    }
    
    @Override
    public boolean canUse() {
        if (pokemonEntity.getTarget() != null) {
            return false;
        }
        if (!pokemonEntity.level().isRaining() && !pokemonEntity.level().isThundering()) {
            return false;
        }
        if (!pokemonEntity.level().canSeeSky(pokemonEntity.blockPosition())) {
            return false;
        }
        return super.setWantedPos();
    }
}
