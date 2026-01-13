package necro.livelier.pokemon.neoforge.events;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.particles.TerrainParticle;
import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = LivelierPokemon.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.ELECTRIC_TERRAIN, TerrainParticle.ElectricTerrainProvider::new);
        event.registerSpriteSet(ParticleRegistry.GRASSY_TERRAIN, TerrainParticle.GrassyTerrainProvider::new);
        event.registerSpriteSet(ParticleRegistry.MISTY_TERRAIN, TerrainParticle.MistyTerrainProvider::new);
        event.registerSpriteSet(ParticleRegistry.PSYCHIC_TERRAIN, TerrainParticle.PsychicTerrainProvider::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterEvent event) {
        event.register(Registries.PARTICLE_TYPE, helper -> {
            helper.register(ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "electric_terrain"), ParticleRegistry.ELECTRIC_TERRAIN);
            helper.register(ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "grassy_terrain"), ParticleRegistry.GRASSY_TERRAIN);
            helper.register(ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "misty_terrain"), ParticleRegistry.MISTY_TERRAIN);
            helper.register(ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "psychic_terrain"), ParticleRegistry.PSYCHIC_TERRAIN);
        });
    }
}
