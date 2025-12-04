package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.CategoryCache;
import necro.livelier.pokemon.common.goals.AvoidPokemonGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Spider.class)
public abstract class SpiderMixin extends Monster {
    protected SpiderMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("HEAD"))
    protected void registerGoalsInject(CallbackInfo info)
    {
        if (!LivelierPokemon.getAbilityConfig().SPIDER_FLEE_ARMADILLO) return;
        this.goalSelector.addGoal(3, new AvoidPokemonGoal(this, 6.0f, 1.0, 1.2, (pokemonEntity) ->
            CategoryCache.getArmadillos().contains(pokemonEntity.getPokemon().getSpecies().getName())));
    }
}
