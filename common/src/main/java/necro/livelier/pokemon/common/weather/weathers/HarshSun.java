package necro.livelier.pokemon.common.weather.weathers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class HarshSun extends Sun {
    public HarshSun(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        super(source, blockPos, level, radius, duration, WeatherType.HARSH_SUN);
    }
}
