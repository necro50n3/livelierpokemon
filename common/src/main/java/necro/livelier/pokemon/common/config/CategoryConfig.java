package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Config(name="livelierpokemon-categories")
public class CategoryConfig implements ConfigData {
    @ConfigEntry.Category("Cat Pokemon")
    public List<String> CAT_POKEMON = Arrays.asList(
        "Meowth",
        "Persian",
        "Espeon",
        "Raikou",
        "Skitty",
        "Delcatty",
        "Shinx",
        "Luxio",
        "Luxray",
        "Glameow",
        "Purugly",
        "Purrloin",
        "Liepard",
        "Litleo",
        "Pyroar",
        "Espurr",
        "Meowstic",
        "Litten",
        "Torracat",
        "Incineroar",
        "Solgaleo",
        "Zeraora",
        "Perrserker",
        "Sprigatito",
        "Floragato",
        "Meowscarada",
        "Chien-Pao"
    );
    @ConfigEntry.Category("Dog Pokemon")
    public List<String> DOG_POKEMON = Arrays.asList(
        "Growlithe",
        "Arcanine",
        "Snubbull",
        "Granbull",
        "Houndour",
        "Houndoom",
        "Smeargle",
        "Entei",
        "Poochyena",
        "Mightyena",
        "Electrike",
        "Manectric",
        "Riolu",
        "Lucario",
        "Lillipup",
        "Herdier",
        "Stoutland",
        "Furfrou",
        "Rockruff",
        "Lycanrock",
        "Yamper",
        "Boltund",
        "Zacian",
        "Zamazenta",
        "Fidough",
        "Dachsbun",
        "Maschiff",
        "Mabosstiff",
        "Greavard",
        "Houndstone",
        "Okidogi"
    );

    private Set<String> CAT_SET;
    private Set<String> DOG_SET;

    public void init() {
        CAT_SET = new HashSet<>(this.CAT_POKEMON);
        DOG_SET = new HashSet<>(this.DOG_POKEMON);
    }

    public Set<String> getCats() {
        return this.CAT_SET;
    }

    public Set<String> getDogs() {
        return this.DOG_SET;
    }
}
