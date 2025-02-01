package necro.livelier.pokemon.neoforge;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.neoforge.registries.EffectRegistryNeoForge;
import necro.livelier.pokemon.neoforge.registries.ParticleRegistryNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(LivelierPokemon.MODID)
public class LivelierPokemonNeoForge {
    public LivelierPokemonNeoForge(IEventBus modBus, ModContainer container) {
        LivelierPokemon.init();
        EffectRegistryNeoForge.register();
        ParticleRegistryNeoForge.register();

        EffectRegistryNeoForge.MOB_EFFECTS.register(modBus);
    }
}
