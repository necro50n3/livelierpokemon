package necro.livelier.pokemon.common.compat;

import necro.livelier.pokemon.common.LivelierPokemon;

import java.util.Locale;

public enum ModCompat {
    FIGHTORFLIGHT;

    private final String id;
    private boolean loaded;

    ModCompat() {
        this.id = name().toLowerCase(Locale.ROOT);
        this.loaded = false;
    }

    public String getId() {
        return this.id;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
        if (loaded) LivelierPokemon.LOGGER.info("Loaded {}", this.id);
    }

    public boolean isLoaded() {
        return this.loaded;
    }
}
