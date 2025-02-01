package necro.livelier.pokemon.fabric.registries;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.effects.*;
import necro.livelier.pokemon.common.registries.EffectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class EffectRegistryFabric extends EffectRegistry {

    public static void register() {
        EffectRegistry.PARALYSIS = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "paralysis"), new ParalysisEffect()
        );

        EffectRegistry.ELECTRIC_TERRAIN = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "electric_terrain_effect"), new ElectricTerrainEffect()
        );
        EffectRegistry.GRASSY_TERRAIN = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "grassy_terrain_effect"), new GrassyTerrainEffect()
        );
        EffectRegistry.MISTY_TERRAIN = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "misty_terrain_effect"), new MistyTerrainEffect()
        );
        EffectRegistry.PSYCHIC_TERRAIN = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "psychic_terrain_effect"), new PsychicTerrainEffect()
        );
    }
}
