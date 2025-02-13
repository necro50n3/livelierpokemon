package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.compat.ModCompat;
import necro.livelier.pokemon.common.events.GeodudeSpawnEvent;
import necro.livelier.pokemon.fabric.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.loader.api.FabricLoader;

public class LivelierPokemonFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        LivelierPokemon.init();
        if (LivelierPokemon.getAbilityConfig().SERVER_MODE) {
            EffectRegistryFabric.registerServerMode();
        }
        else {
            EffectRegistryFabric.register();
            ParticleRegistryFabric.register();
        }

        for (ModCompat mod : ModCompat.values()) {
            mod.setLoaded(FabricLoader.getInstance().isModLoaded(mod.getId()));
        }

        PlayerBlockBreakEvents.AFTER.register((level, player, blockPos, blockState, entity) -> GeodudeSpawnEvent.onBlockBreak(level, blockPos, blockState));
    }

}
