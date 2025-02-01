package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoisonMobEffect.class)
public abstract class PoisonEffectMixin extends MobEffect {
    protected PoisonEffectMixin(MobEffectCategory mobEffectCategory, int i, ParticleOptions particleOptions) {
        super(mobEffectCategory, i, particleOptions);
    }

    protected PoisonEffectMixin(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    @Inject(method = "applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z", at = @At("HEAD"), cancellable = true)
    private void applyEffectTickInject(LivingEntity livingEntity, int i, CallbackInfoReturnable<Boolean> cir) {
        if (!(livingEntity instanceof PokemonEntity pokemonEntity)) return;
        else if (LivelierPokemon.getAbilityConfig().POISON_HEAL && SpawnHelper.hasAbility(pokemonEntity, "poisonheal")) {
            pokemonEntity.heal(1.0f);
            cir.setReturnValue(true);
        }
    }
}
