package necro.livelier.pokemon.common.helpers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.registries.EffectRegistry;
import necro.livelier.pokemon.common.registries.ParticleRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;

import java.util.Map;
import java.util.function.BiConsumer;

public class ParticleHelper {
    private static final Map<ParticleOptions, BiConsumer<PokemonEntity, Integer>> TERRAIN_MAP = Map.ofEntries(
        Map.entry(ParticleRegistry.ELECTRIC_TERRAIN, ParticleHelper::summonElectricTerrain),
        Map.entry(ParticleRegistry.GRASSY_TERRAIN, ParticleHelper::summonGrassyTerrain),
        Map.entry(ParticleRegistry.MISTY_TERRAIN, ParticleHelper::summonMistyTerrain),
        Map.entry(ParticleRegistry.PSYCHIC_TERRAIN, ParticleHelper::summonPsychicTerrain)
    );

    public static void doFieldEffect(PokemonEntity source, int radius, ParticleOptions particle) {
        BiConsumer<PokemonEntity, Integer> func = TERRAIN_MAP.getOrDefault(particle, null);
        if (func != null) func.accept(source, radius);
    }

    private static AreaEffectCloud summonTerrain(PokemonEntity source, int radius, ParticleOptions particle) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(
            source.level(), source.getX(), source.getY() + 0.3, source.getZ()
        );
        areaEffectCloud.setOwner(source);
        areaEffectCloud.setParticle(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(20);
        return areaEffectCloud;
    }

    private static void summonElectricTerrain(PokemonEntity source, int radius) {
        AreaEffectCloud areaEffectCloud = summonTerrain(source, radius, ParticleRegistry.ELECTRIC_TERRAIN);
        if (EffectRegistry.ELECTRIC_TERRAIN != null)
            areaEffectCloud.addEffect(new MobEffectInstance(EffectRegistry.ELECTRIC_TERRAIN, 60, 0, false, false, false));
        source.level().addFreshEntity(areaEffectCloud);
    }

    private static void summonGrassyTerrain(PokemonEntity source, int radius) {
        AreaEffectCloud areaEffectCloud = summonTerrain(source, radius, ParticleRegistry.GRASSY_TERRAIN);
        if (EffectRegistry.GRASSY_TERRAIN != null)
            areaEffectCloud.addEffect(new MobEffectInstance(EffectRegistry.GRASSY_TERRAIN, 60, 0, false, false, false));
        source.level().addFreshEntity(areaEffectCloud);
    }

    private static void summonMistyTerrain(PokemonEntity source, int radius) {
        AreaEffectCloud areaEffectCloud = summonTerrain(source, radius, ParticleRegistry.MISTY_TERRAIN);
        if (EffectRegistry.MISTY_TERRAIN != null)
            areaEffectCloud.addEffect(new MobEffectInstance(EffectRegistry.MISTY_TERRAIN, 60, 0, false, false, false));
        source.level().addFreshEntity(areaEffectCloud);
    }

    private static void summonPsychicTerrain(PokemonEntity source, int radius) {
        AreaEffectCloud areaEffectCloud = summonTerrain(source, radius, ParticleRegistry.PSYCHIC_TERRAIN);
        if (EffectRegistry.PSYCHIC_TERRAIN != null)
            areaEffectCloud.addEffect(new MobEffectInstance(EffectRegistry.PSYCHIC_TERRAIN, 60, 0, false, false, false));
        source.level().addFreshEntity(areaEffectCloud);
    }
}
