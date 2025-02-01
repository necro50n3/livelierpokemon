package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TamableAnimal.class)
public abstract class TamableAnimalMixin extends Animal {
    protected TamableAnimalMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"))
    public void dieInject(DamageSource damageSource, CallbackInfo ci) {
        if (!((TamableAnimal) (Object) this instanceof PokemonEntity pokemonEntity)) return;
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.AFTERMATH && SpawnHelper.hasAbility(pokemonEntity.getPokemon(), "aftermath") &&
            this.level().getDifficulty() != Difficulty.PEACEFUL && damageSource.is(LivelierDamageType.MAKES_CONTACT)
        ) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float) config.aftermath_explosion_radius,
                config.aftermath_affects_environment ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE
            );
        }
    }
}
