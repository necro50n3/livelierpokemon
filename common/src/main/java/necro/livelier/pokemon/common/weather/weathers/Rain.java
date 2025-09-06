package necro.livelier.pokemon.common.weather.weathers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.Weather;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class Rain extends Weather {
    public Rain(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration, WeatherType weatherType) {
        super(source, blockPos, level, radius, duration, weatherType);
    }

    public Rain(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        this(source, blockPos, level, radius, duration, WeatherType.RAIN);
    }
}
