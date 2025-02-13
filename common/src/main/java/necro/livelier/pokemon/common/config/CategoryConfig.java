package necro.livelier.pokemon.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="livelierpokemon-categories")
public class CategoryConfig implements ConfigData {
    @ConfigEntry.Category("Cat Pokemon")
    public String[] CAT_POKEMON = {
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
    };
    @ConfigEntry.Category("Dog Pokemon")
    public String[] DOG_POKEMON = {
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
    };
}
