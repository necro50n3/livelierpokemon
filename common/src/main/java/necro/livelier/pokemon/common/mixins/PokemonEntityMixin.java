package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.helpers.ImmunityHelper;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PokemonEntity.class)
public abstract class PokemonEntityMixin extends ShoulderRidingEntity {
    @Shadow(remap = false)
    public abstract Pokemon getPokemon();

    protected PokemonEntityMixin(EntityType<? extends ShoulderRidingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "fireImmune()Z", at = @At("HEAD"), cancellable = true)
    public void fireImmuneInject(CallbackInfoReturnable<Boolean> cir) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if ((config.FLASH_FIRE && SpawnHelper.hasAbility(this.getPokemon(), "flashfire") ||
            config.FLARE_BOOST && SpawnHelper.hasAbility(this.getPokemon(), "flareboost")) &&
            this.getRemainingFireTicks() > 0
        ) {
            MobEffectInstance effect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 240, 0);
            if (this.canBeAffected(effect)) this.addEffect(effect, this);
            cir.setReturnValue(true);
        } else if (config.WELL_BAKED_BODY && SpawnHelper.hasAbility(this.getPokemon(), "wellbakedbody") &&
            this.getRemainingFireTicks() > 0
        ) {
            MobEffectInstance effect = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 0);
            if (this.canBeAffected(effect)) this.addEffect(effect, this);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    public void isInvulnerableToInject(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (ImmunityHelper.isImmuneTo(damageSource, this.getPokemon())) cir.setReturnValue(true);
    }

    @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("RETURN"))
    private void hurtInject(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (LivelierPokemon.getAbilityConfig().INNARDS_OUT)
            if (SpawnHelper.hasAbility((PokemonEntity) (Object) this, "innardsout") && this.isDeadOrDying() &&
                damageSource.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.hurt(this.damageSources().mobAttack(this), damage);
            }
    }
}
