package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.WeatherManager;
import net.minecraft.world.entity.ai.goal.Goal;

public class RemoveWeatherGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private int tick;

    public RemoveWeatherGoal(PokemonEntity pokemonEntity) {
        this.pokemonEntity = pokemonEntity;
        this.tick = 0;
    }

    @Override
    public boolean canUse() { return true; }

    @Override
    public void tick() {
        if (--this.tick > 0) return;
        this.tick = this.adjustedTickDelay(20);

        WeatherManager.removeFromPos(this.pokemonEntity.blockPosition(), this.pokemonEntity.level());
    }
}
