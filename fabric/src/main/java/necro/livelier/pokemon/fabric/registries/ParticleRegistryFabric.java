package necro.livelier.pokemon.fabric.registries;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ParticleRegistryFabric extends ParticleRegistry {

    public static void register() {
        ParticleRegistry.ELECTRIC_TERRAIN = FabricParticleTypes.simple();
        Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "electric_terrain"), ELECTRIC_TERRAIN
        );

        ParticleRegistry.GRASSY_TERRAIN = FabricParticleTypes.simple();
        Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "grassy_terrain"), GRASSY_TERRAIN
        );

        ParticleRegistry.MISTY_TERRAIN = FabricParticleTypes.simple();
        Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "misty_terrain"), MISTY_TERRAIN
        );

        ParticleRegistry.PSYCHIC_TERRAIN = FabricParticleTypes.simple();
        Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "psychic_terrain"), PSYCHIC_TERRAIN
        );
    }
}
