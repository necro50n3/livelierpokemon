package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.config.CategoryCache;
import necro.livelier.pokemon.common.goals.AvoidPokemonGoal;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {
    @Shadow public abstract void setSwellDir(int i);

    protected CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals()V", at = @At("HEAD"))
    private void registerGoalsInject(CallbackInfo info)
    {
        if (!LivelierPokemon.getAbilityConfig().CREEPER_FLEE_CAT) return;
        this.goalSelector.addGoal(3, new AvoidPokemonGoal(this, 6.0f, 1.0, 1.2, (pokemonEntity) ->
            CategoryCache.getCats().contains(pokemonEntity.getPokemon().getSpecies().getName())));
    }

    @Inject(method = "setSwellDir(I)V", at = @At("HEAD"), cancellable = true)
    private void setSwellDirInject(int swell, CallbackInfo ci) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (!config.DAMP || swell == -1) return;
        else if (!TargetHelper.getNearbyPokemon(this, config.damp_radius, (entity) -> SpawnHelper.hasAbility(entity, "damp")).isEmpty()) {
            this.setSwellDir(-1);
            ci.cancel();
        }
    }

    @Inject(method = "ignite()V", at = @At("HEAD"), cancellable = true)
    private void igniteInject(CallbackInfo ci) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (!config.DAMP) return;
        else if (!TargetHelper.getNearbyPokemon(this, config.damp_radius, (entity) -> SpawnHelper.hasAbility(entity, "damp")).isEmpty()) {
            ci.cancel();
        }
    }
}
