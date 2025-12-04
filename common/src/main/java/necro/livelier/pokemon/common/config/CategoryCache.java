package necro.livelier.pokemon.common.config;

import necro.livelier.pokemon.common.LivelierPokemon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryCache {
    private static Set<String> ARMADILLO_SET;

    public static void init() {
        ARMADILLO_SET = new HashSet<>(List.of(LivelierPokemon.getCategoryConfig().ARMADILLO_POKEMON));
    }

    public static Set<String> getArmadillos() {
        return ARMADILLO_SET;
    }
}
