package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.CategoryCache;
import necro.livelier.pokemon.common.goals.AvoidPokemonGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeleton.class)
public abstract class SkeletonMixin extends Monster {
    protected SkeletonMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("HEAD"))
    protected void registerGoalsInject(CallbackInfo info)
    {
        if (!LivelierPokemon.getAbilityConfig().SKELETON_FLEE_DOG) return;
        this.goalSelector.addGoal(3, new AvoidPokemonGoal(this, 6.0f, 1.0, 1.2, (pokemonEntity) ->
            CategoryCache.getDogs().contains(pokemonEntity.getPokemon().getSpecies().getName())));
    }
}
