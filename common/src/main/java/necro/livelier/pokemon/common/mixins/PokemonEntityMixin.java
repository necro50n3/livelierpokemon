package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
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
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.WONDER_GUARD && SpawnHelper.hasAbility(this.getPokemon(), "wonderguard") && !damageSource.is(LivelierDamageType.BYPASSES_WONDER_GUARD)
        ) cir.setReturnValue(true);
        else if (config.LIGHTNING_ROD && SpawnHelper.hasAbility(this.getPokemon(), "lightningrod") && damageSource.is(DamageTypes.LIGHTNING_BOLT)
        ) cir.setReturnValue(true);
        else if (config.BULLETPROOF && SpawnHelper.hasAbility(this.getPokemon(), "bulletproof") &&
            (damageSource.is(DamageTypeTags.IS_PROJECTILE) || damageSource.is(DamageTypeTags.IS_EXPLOSION))
        ) cir.setReturnValue(true);
        else if (config.ARMOR_TAIL && SpawnHelper.hasAbility(this.getPokemon(), "armortail") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) cir.setReturnValue(true);
        else if (config.DAZZLING && SpawnHelper.hasAbility(this.getPokemon(), "dazzling") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) cir.setReturnValue(true);
        else if (config.QUEENLY_MAJESTY && SpawnHelper.hasAbility(this.getPokemon(), "queenlymajesty") && damageSource.is(DamageTypeTags.IS_PROJECTILE)
        ) cir.setReturnValue(true);
    }
}
