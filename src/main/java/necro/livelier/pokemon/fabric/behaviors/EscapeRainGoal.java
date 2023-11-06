package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class EscapeRainGoal extends FleeSunGoal {
    private PokemonEntity pokemonEntity;

    public EscapeRainGoal(PokemonEntity pokemonEntity, String speed)
    {
        super(pokemonEntity, Double.parseDouble(speed));
        this.pokemonEntity = pokemonEntity;
    }
    
    @Override
    public boolean canUse() {
        if (pokemonEntity.getTarget() != null) {
            return false;
        }
        if (!this.level.isRaining() && !this.level.isThundering()) {
            return false;
        }
        if (!this.level.canSeeSky(pokemonEntity.blockPosition())) {
            return false;
        }
        return super.setWantedPos();
    }
}
