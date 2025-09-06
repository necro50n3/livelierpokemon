package necro.livelier.pokemon.common.weather.client;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.weather.WeatherType;

public class ClientWeather {
    public static WeatherType PREVIOUS_WEATHER = null;
    public static WeatherType ACTIVE_WEATHER = null;

    public static void setWeather(WeatherType weatherType) {
        PREVIOUS_WEATHER = ACTIVE_WEATHER;
        ACTIVE_WEATHER = weatherType;
    }

    public static void clearPrevious() {
        PREVIOUS_WEATHER = WeatherType.NONE;
    }

    public static boolean isActive() {
        return ACTIVE_WEATHER != null && ACTIVE_WEATHER != WeatherType.NONE;
    }

    public static boolean hasPrevious() {
        return PREVIOUS_WEATHER != WeatherType.NONE && PREVIOUS_WEATHER != null;
    }

    public static boolean isSnowing() {
        return isActive()
            && ACTIVE_WEATHER == WeatherType.SNOW
            && LivelierPokemon.getClientConfig().SHOW_SNOW;
    }

    public static boolean isRaining() {
        return isActive()
            && (ACTIVE_WEATHER == WeatherType.RAIN || ACTIVE_WEATHER == WeatherType.HEAVY_RAIN)
            && LivelierPokemon.getClientConfig().SHOW_RAIN;
    }

    public static boolean isRainingOrSnowing() {
        return isActive()
            && (((ACTIVE_WEATHER == WeatherType.RAIN || ACTIVE_WEATHER == WeatherType.HEAVY_RAIN) && LivelierPokemon.getClientConfig().SHOW_RAIN)
            || (ACTIVE_WEATHER == WeatherType.SNOW && LivelierPokemon.getClientConfig().SHOW_SNOW));
    }

    public static boolean isNotRainingOrSnowing() {
        return isActive()
            && (((ACTIVE_WEATHER == WeatherType.SUN || ACTIVE_WEATHER == WeatherType.HARSH_SUN) && LivelierPokemon.getClientConfig().SHOW_SUN)
                || (ACTIVE_WEATHER == WeatherType.SANDSTORM  && LivelierPokemon.getClientConfig().SHOW_SANDSTORM)
                || ACTIVE_WEATHER == WeatherType.STRONG_WIND);
    }

    public static boolean isSunny() {
        return isActive()
            && (ACTIVE_WEATHER == WeatherType.SUN || ACTIVE_WEATHER == WeatherType.HARSH_SUN)
            && LivelierPokemon.getClientConfig().SHOW_SUN;
    }

    public static boolean isSandstorm() {
        return isActive()
            && ACTIVE_WEATHER == WeatherType.SANDSTORM
            && LivelierPokemon.getClientConfig().SHOW_SANDSTORM;
    }

    public static boolean wasSnowing() {
        return PREVIOUS_WEATHER == WeatherType.SNOW && LivelierPokemon.getClientConfig().SHOW_SNOW;
    }

    public static boolean wasRaining() {
        return (PREVIOUS_WEATHER == WeatherType.RAIN || PREVIOUS_WEATHER == WeatherType.HEAVY_RAIN) && LivelierPokemon.getClientConfig().SHOW_RAIN;
    }

    public static boolean wasSandstorm() {
        return PREVIOUS_WEATHER == WeatherType.SANDSTORM && LivelierPokemon.getClientConfig().SHOW_SANDSTORM;
    }
}
