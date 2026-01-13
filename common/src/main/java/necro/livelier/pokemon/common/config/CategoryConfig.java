package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="livelierpokemon-categories")
public class CategoryConfig implements ConfigData {
    @ConfigEntry.Category("Spider Repellent Pokemon")
    public String[] ARMADILLO_LIKE_POKEMON = {
        "Sandshrew",
        "Sandslash",
        "Cyndaquil",
        "Quilava",
        "Typhlosion",
        "Anorith",
        "Armaldo",
        "Heatmor"
    };
}
