package necro.livelier.pokemon.neoforge;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.compat.ModCompat;
import necro.livelier.pokemon.neoforge.registries.EffectRegistryNeoForge;
import necro.livelier.pokemon.neoforge.registries.ParticleRegistryNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

@Mod(LivelierPokemon.MODID)
public class LivelierPokemonNeoForge {
    public LivelierPokemonNeoForge(IEventBus modBus, ModContainer container) {
        LivelierPokemon.init();
        if (LivelierPokemon.getAbilityConfig().SERVER_MODE) {
            EffectRegistryNeoForge.registerServerMode();
        }
        else {
            EffectRegistryNeoForge.register();
            ParticleRegistryNeoForge.register();

            EffectRegistryNeoForge.MOB_EFFECTS.register(modBus);
        }

        for (ModCompat mod : ModCompat.values()) {
            mod.setLoaded(ModList.get().isLoaded(mod.getId()));;
        }
    }
}
