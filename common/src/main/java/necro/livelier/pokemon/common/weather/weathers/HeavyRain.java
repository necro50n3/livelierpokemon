package necro.livelier.pokemon.common.weather.weathers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class HeavyRain extends Rain {
    public HeavyRain(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        super(source, blockPos, level, radius, duration, WeatherType.HEAVY_RAIN);
    }
}
