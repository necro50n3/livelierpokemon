package necro.livelier.pokemon.common.weather.weathers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.Weather;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class Sandstorm extends Weather {
    public Sandstorm(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        super(source, blockPos, level, radius, duration, WeatherType.SANDSTORM);
    }
}
