package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;

public class AvoidRainGoal extends AvoidSunlightGoal {
    private PokemonEntity pokemonEntity;

    public AvoidRainGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canStart()
    {
        return (this.pokemonEntity.world.isRaining() || this.pokemonEntity.world.isThundering()) && NavigationConditions.hasMobNavigation(this.pokemonEntity);
    }

    @Override
    public void start() {
        super.start();
    }
}
