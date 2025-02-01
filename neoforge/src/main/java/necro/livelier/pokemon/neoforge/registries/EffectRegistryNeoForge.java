package necro.livelier.pokemon.neoforge.registries;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.effects.*;
import necro.livelier.pokemon.common.registries.EffectRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectRegistryNeoForge extends EffectRegistry {
    public static DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, LivelierPokemon.MODID);

    public static void register() {
        EffectRegistry.PARALYSIS = MOB_EFFECTS.register("paralysis", ParalysisEffect::new);

        EffectRegistry.ELECTRIC_TERRAIN = MOB_EFFECTS.register("electric_terrain_effect", ElectricTerrainEffect::new);
        EffectRegistry.GRASSY_TERRAIN = MOB_EFFECTS.register("grassy_terrain_effect", GrassyTerrainEffect::new);
        EffectRegistry.MISTY_TERRAIN = MOB_EFFECTS.register("misty_terrain_effect", MistyTerrainEffect::new);
        EffectRegistry.PSYCHIC_TERRAIN = MOB_EFFECTS.register("psychic_terrain_effect", PsychicTerrainEffect::new);
    }
}
