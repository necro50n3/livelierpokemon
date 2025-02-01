package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.common.particles.TerrainParticle;
import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class LivelierPokemonFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ELECTRIC_TERRAIN, TerrainParticle.ElectricTerrainProvider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.GRASSY_TERRAIN, TerrainParticle.ElectricTerrainProvider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MISTY_TERRAIN, TerrainParticle.ElectricTerrainProvider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.PSYCHIC_TERRAIN, TerrainParticle.ElectricTerrainProvider::new);
    }
}
