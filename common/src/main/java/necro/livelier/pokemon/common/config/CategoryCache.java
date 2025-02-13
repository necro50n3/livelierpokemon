package necro.livelier.pokemon.common.config;

import necro.livelier.pokemon.common.LivelierPokemon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryCache {
    private static Set<String> CAT_SET;
    private static Set<String> DOG_SET;

    public static void init() {
        CAT_SET = new HashSet<>(List.of(LivelierPokemon.getCategoryConfig().CAT_POKEMON));
        DOG_SET = new HashSet<>(List.of(LivelierPokemon.getCategoryConfig().DOG_POKEMON));
    }

    public static Set<String> getCats() {
        return CAT_SET;
    }

    public static Set<String> getDogs() {
        return DOG_SET;
    }
}
