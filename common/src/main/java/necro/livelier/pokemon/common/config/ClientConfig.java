package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="livelierpokemon-client")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Category("weather")
    public boolean SHOW_RAIN = true;

    @ConfigEntry.Category("weather")
    public boolean SHOW_SANDSTORM = true;

    @ConfigEntry.Category("weather")
    public boolean SHOW_SNOW = true;

    @ConfigEntry.Category("weather")
    public boolean SHOW_SUN = true;
}
