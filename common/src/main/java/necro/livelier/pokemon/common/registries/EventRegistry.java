package necro.livelier.pokemon.common.registries;

import com.cobblemon.mod.common.api.reactive.EventObservable;
import necro.livelier.pokemon.common.events.PastureSentEvent;

public class EventRegistry {
    public static EventObservable<PastureSentEvent> PASTURE_SENT = new EventObservable<>();
}
