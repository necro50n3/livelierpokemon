package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="livelierpokemon-categories")
public class CategoryConfig implements ConfigData {
    @ConfigEntry.Category("Armadillo Pokemon")
    public String[] ARMADILLO_POKEMON = {
        "Meowth"
    };
}
