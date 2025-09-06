package necro.livelier.pokemon.common.weather.weathers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.weather.Weather;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Sun extends Weather {
    private final Set<UUID> affectedPlayers;

    public Sun(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration, WeatherType weatherType) {
        super(source, blockPos, level, radius, duration, weatherType);
        this.affectedPlayers = new HashSet<>();
    }

    public Sun(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        this(source, blockPos, level, radius, duration, WeatherType.SUN);
    }

    @Override
    protected void onPlayerEnter(Player player) {
        super.onPlayerEnter(player);
        if (player.hasEffect(MobEffects.NIGHT_VISION)) return;
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false, false));
        this.affectedPlayers.add(player.getUUID());
    }

    @Override
    protected void onPlayerTick(Player player) {
        if (!this.affectedPlayers.contains(player.getUUID())) return;
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false, false));
    }

    @Override
    protected void onPlayerExit(Player player) {
        super.onPlayerExit(player);
        if (this.affectedPlayers.remove(player.getUUID())) player.removeEffect(MobEffects.NIGHT_VISION);
    }
}
