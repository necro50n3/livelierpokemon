package necro.livelier.pokemon.common.events;

import com.cobblemon.mod.common.pokemon.Pokemon;
import org.jetbrains.annotations.NotNull;

public class PastureSentEvent {
    public @NotNull Pokemon pokemon;

    public PastureSentEvent(@NotNull Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
