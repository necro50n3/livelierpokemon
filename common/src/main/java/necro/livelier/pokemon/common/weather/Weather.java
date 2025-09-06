package necro.livelier.pokemon.common.weather;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.network.SetWeatherPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

public abstract class Weather {
    protected final PokemonEntity source;
    protected final AABB region;
    protected final BlockPos origin;
    protected final int radius;
    protected final Level level;
    protected int tick;
    private final boolean doTick;
    protected final WeatherType weatherType;
    protected Set<Player> players;

    public Weather(PokemonEntity source, BlockPos blockPos, Level level, int radius, int duration, WeatherType weatherType) {
        this.source = source;
        this.region = new AABB(blockPos).inflate(radius, level.getMaxBuildHeight(), radius);
        this.origin = blockPos;
        this.radius = radius;
        this.level = level;
        this.tick = duration;
        this.doTick = duration != -1;
        this.weatherType = weatherType;
        this.players = new HashSet<>();
    }

    public Weather(PokemonEntity source, Vec3 vec3, Level level, int radius, int duration, WeatherType weatherType) {
        this(source, new BlockPos((int) vec3.x(), (int) vec3.y(), (int) vec3.z()), level, radius, duration, weatherType);
    }

    public Weather(PokemonEntity source, int x, int y, int z, Level level, int radius, int duration, WeatherType weatherType) {
        this(source, new BlockPos(x, y, z), level, radius, duration, weatherType);
    }

    public boolean intersects(Weather weather) {
        return this.level == weather.level && this.region.intersects(weather.region);
    }

    public boolean contains(BlockPos blockPos) {
        return this.region.contains(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public void onSummon() {
        this.checkPlayers();
    }

    public void onExit() {
        this.players.forEach(this::onPlayerExit);
    }

    protected void doWeather() {}

    protected void onPlayerEnter(Player player) {
        if (player instanceof ServerPlayer serverPlayer)
            WeatherManager.WEATHER_PACKET.accept(serverPlayer, new SetWeatherPacket(this.weatherType));
    }

    protected void onPlayerTick(Player player) {}

    protected void onPlayerExit(Player player) {
        if (player instanceof ServerPlayer serverPlayer)
            WeatherManager.WEATHER_PACKET.accept(serverPlayer, new SetWeatherPacket(WeatherType.NONE));
    }

    private void checkPlayers() {
        Set<Player> current = new HashSet<>();
        for (Player player : this.level.getNearbyPlayers(TargetingConditions.forNonCombat().ignoreLineOfSight(), null, this.region)) {
            if (this.level.canSeeSky(player.blockPosition())) current.add(player);
        }

        for (Player player : current) {
            if (!players.contains(player)) onPlayerEnter(player);
        }
        for (Player player : players) {
            if (!current.contains(player)) onPlayerExit(player);
        }

        this.players.clear();
        this.players.addAll(current);
    }

    public boolean tick() {
        if (!this.doTick && this.source.isRemoved()) return false;
        else if (this.doTick && --this.tick <= 0) return false;

        this.doWeather();
        this.checkPlayers();
        this.players.forEach(this::onPlayerTick);
        return true;
    }

    public int getTick() {
        return this.tick;
    }
}
