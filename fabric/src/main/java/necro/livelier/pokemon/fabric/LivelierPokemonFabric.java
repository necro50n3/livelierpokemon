package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.events.GeodudeSpawnEvent;
import necro.livelier.pokemon.fabric.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class LivelierPokemonFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        LivelierPokemon.init();
        EffectRegistryFabric.register();
        ParticleRegistryFabric.register();

        PlayerBlockBreakEvents.AFTER.register((level, player, blockPos, blockState, entity) -> GeodudeSpawnEvent.onBlockBreak(level, blockPos, blockState));
    }

}
