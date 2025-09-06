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

public class StrongWind extends Weather {
    private final Set<UUID> slowFallingPlayers;
    private final Set<UUID> jumpBoostPlayers;

    public StrongWind(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration) {
        super(source, blockPos, level, radius, duration, WeatherType.STRONG_WIND);
        this.slowFallingPlayers = new HashSet<>();
        this.jumpBoostPlayers = new HashSet<>();
    }

    @Override
    protected void onPlayerEnter(Player player) {
        super.onPlayerEnter(player);
        if (!player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 0, false, false, false));
            this.slowFallingPlayers.add(player.getUUID());
        }
        if (!player.hasEffect(MobEffects.JUMP)) {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 0, false, false, false));
            this.jumpBoostPlayers.add(player.getUUID());
        }
    }

    @Override
    protected void onPlayerTick(Player player) {
        if (this.slowFallingPlayers.contains(player.getUUID())) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 0, false, false, false));
        }
        if (this.jumpBoostPlayers.contains(player.getUUID())) {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, 0, false, false, false));
        }
    }

    @Override
    protected void onPlayerExit(Player player) {
        super.onPlayerExit(player);
        if (this.slowFallingPlayers.remove(player.getUUID())) player.removeEffect(MobEffects.SLOW_FALLING);
        if (this.jumpBoostPlayers.remove(player.getUUID())) player.removeEffect(MobEffects.JUMP);
    }
}
