package necro.livelier.pokemon.common.helpers;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.List;
import java.util.function.Predicate;

public class TargetHelper {
    public static List<PokemonEntity> getNearbyPokemon(LivingEntity source, double radius, Predicate<PokemonEntity> filter) {
        return TargetHelper.getNearbyPokemon(source, radius, true, filter);
    }

    public static List<PokemonEntity> getNearbyPokemon(LivingEntity source, double radius, boolean includeSelf, Predicate<PokemonEntity> filter) {
        return TargetHelper.getNearbyPokemon(source, radius, includeSelf).stream().filter(filter).toList();
    }

    public static List<PokemonEntity> getNearbyPokemon(LivingEntity source, double radius) {
        return TargetHelper.getNearbyPokemon(source, radius, true);
    }

    public static List<PokemonEntity> getNearbyPokemon(LivingEntity source, double radius, boolean includeSelf) {
        List<PokemonEntity> entities = source.level().getNearbyEntities(
            PokemonEntity.class, TargetingConditions.forNonCombat().ignoreLineOfSight(), source, source.getBoundingBox().inflate(radius, radius, radius)
        );
        if (includeSelf && source instanceof PokemonEntity pokemonEntity) entities.add(pokemonEntity);
        return entities;
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, double height, Predicate<LivingEntity> filter) {
        return TargetHelper.getNearbyEntities(source, radius, height, true, filter);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, double height, boolean includeSelf, Predicate<LivingEntity> filter) {
        return TargetHelper.getNearbyEntities(source, radius, height, includeSelf).stream().filter(filter).toList();
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, Predicate<LivingEntity> filter) {
        return TargetHelper.getNearbyEntities(source, radius, radius, true, filter);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, boolean includeSelf, Predicate<LivingEntity> filter) {
        return TargetHelper.getNearbyEntities(source, radius, radius, includeSelf, filter);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius) {
        return TargetHelper.getNearbyEntities(source, radius, radius);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, boolean includeSelf) {
        return TargetHelper.getNearbyEntities(source, radius, radius, includeSelf);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, double height) {
        return TargetHelper.getNearbyEntities(source, radius, height, true);
    }

    public static List<LivingEntity> getNearbyEntities(LivingEntity source, double radius, double height, boolean includeSelf) {
        List<LivingEntity> entities = source.level().getNearbyEntities(
            LivingEntity.class, TargetingConditions.forNonCombat().ignoreLineOfSight(), source, source.getBoundingBox().inflate(radius, height, radius)
        );
        if (includeSelf) entities.add(source);
        return entities;
    }

    public static PokemonEntity getNearestPokemon(LivingEntity source, double radius, Predicate<PokemonEntity> filter) {
        return source.level().getNearestEntity(source.level().getEntitiesOfClass(
            PokemonEntity.class, source.getBoundingBox().inflate(radius, 3.0, radius), filter
        ), TargetingConditions.forNonCombat().ignoreLineOfSight(), source, source.getX(), source.getY(), source.getZ());
    }

    public static PokemonEntity getNearestPokemon(LivingEntity source, double radius) {
        return source.level().getNearestEntity(source.level().getEntitiesOfClass(
            PokemonEntity.class, source.getBoundingBox().inflate(radius, 3.0, radius)),
            TargetingConditions.forNonCombat().ignoreLineOfSight(), source, source.getX(), source.getY(), source.getZ()
        );
    }
}
