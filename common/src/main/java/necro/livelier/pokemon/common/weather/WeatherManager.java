package necro.livelier.pokemon.common.weather;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.function.BiConsumer;

public class WeatherManager {
    private static final Map<ChunkPos, List<Weather>> WEATHER_MAP = new HashMap<>();
    private static final Set<UUID> ACTIVE_SOURCES = new HashSet<>();
    private static int TICK = 0;

    public static BiConsumer<ServerPlayer, CustomPacketPayload> WEATHER_PACKET;

    public static void addWeather(Weather weather) {
        if (ACTIVE_SOURCES.contains(weather.source.getPokemon().getUuid())) return;

        Set<ChunkPos> chunks = WeatherManager.getCoveredChunks(weather.region);
        List<Weather> toRemove = new ArrayList<>();

        List<Weather> candidates = new ArrayList<>();
        for (ChunkPos pos : chunks) {
            List<Weather> list = WEATHER_MAP.get(pos);
            if (list != null) {
                candidates.addAll(list);
            }
        }

        for (Weather existingWeather : candidates) {
            if (!existingWeather.intersects(weather)) continue;
            else if (existingWeather.weatherType == weather.weatherType) return;
            else if (existingWeather.weatherType.getPriority() > weather.weatherType.getPriority()) return;
            else toRemove.add(existingWeather);
        }
        toRemove.forEach(WeatherManager::removeWeather);

        for (ChunkPos pos : chunks) {
            WEATHER_MAP.computeIfAbsent(pos, k -> new ArrayList<>()).add(weather);
        }
        ACTIVE_SOURCES.add(weather.source.getPokemon().getUuid());
        weather.onSummon();
    }

    public static void removeWeather(Weather weather) {
        weather.onExit();
        Set<ChunkPos> coveredChunks = WeatherManager.getCoveredChunks(weather.region);
        for (ChunkPos pos : coveredChunks) {
            List<Weather> list = WEATHER_MAP.get(pos);
            if (list == null) continue;

            list.remove(weather);
            ACTIVE_SOURCES.remove(weather.source.getPokemon().getUuid());
            if (list.isEmpty()) WEATHER_MAP.remove(pos);
        }
    }

    public static void removeFromPos(BlockPos blockPos, Level level) {
        List<Weather> weathers = WEATHER_MAP.get(new ChunkPos(blockPos));
        if (weathers == null || weathers.isEmpty()) return;

        Weather toRemove = null;
        for (Weather weather : weathers) {
            if (weather.level == level && weather.contains(blockPos)) {
                toRemove = weather;
                break;
            }
        }
        if (toRemove != null) removeWeather(toRemove);
    }

    public static boolean isWeather(BlockPos blockPos, Level level, WeatherType... weatherTypes) {
        List<Weather> weathers = WEATHER_MAP.get(new ChunkPos(blockPos));
        if (weathers == null || weathers.isEmpty()) return false;

        Set<WeatherType> typeSet = Set.of(weatherTypes);

        for (Weather weather : weathers) {
            if (weather.level == level &&
                typeSet.contains(weather.weatherType) &&
                weather.contains(blockPos)) {
                return true;
            }
        }
        return false;
    }

    public static void tick() {
        if (++TICK < 20) return;
        TICK = 0;

        Set<Weather> uniqueWeathers = new HashSet<>();
        for (List<Weather> list : WEATHER_MAP.values()) {
            uniqueWeathers.addAll(list);
        }

        for (Weather weather : uniqueWeathers) {
            if (!weather.tick()) WeatherManager.removeWeather(weather);
        }
    }

    private static Set<ChunkPos> getCoveredChunks(AABB region) {
        int minChunkX = Mth.floor(region.minX) >> 4;
        int minChunkZ = Mth.floor(region.minZ) >> 4;
        int maxChunkX = Mth.floor(region.maxX) >> 4;
        int maxChunkZ = Mth.floor(region.maxZ) >> 4;

        Set<ChunkPos> chunks = new HashSet<>();
        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                chunks.add(new ChunkPos(cx, cz));
            }
        }
        return chunks;
    }
}
