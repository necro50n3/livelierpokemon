package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.config.AbilityConfig;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.registries.EffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow protected abstract float getDamageAfterMagicAbsorb(DamageSource arg, float g);

    @Shadow public abstract void remove(RemovalReason arg);

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> arg);

    @Shadow public abstract boolean isDeadOrDying();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float actuallyHurtInject(LivingEntity livingEntity, DamageSource damageSource, float damage) {
        if (((LivingEntity) (Object) this) instanceof PokemonEntity pokemonEntity) {
            AbilityConfig config = LivelierPokemon.getAbilityConfig();
            if (config.UNAWARE && SpawnHelper.hasAbility(pokemonEntity, "unaware")) {
                return (float) config.unaware_damage;
            }
        }
        return this.getDamageAfterMagicAbsorb(damageSource, damage);
    }

    @Inject(method = "canBeAffected(Lnet/minecraft/world/effect/MobEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
    private void canBeAffectedInject(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (this.hasEffect(EffectRegistry.MISTY_TERRAIN) && !effect.getEffect().value().isBeneficial()) cir.setReturnValue(false);
        if (!((LivingEntity) (Object) this instanceof PokemonEntity pokemonEntity)) return;
        if (config.MAGIC_GUARD && SpawnHelper.hasAbility(pokemonEntity, "magicguard")) cir.setReturnValue(false);
        else if (config.GOOD_AS_GOLD && SpawnHelper.hasAbility(pokemonEntity, "goodasgold")) cir.setReturnValue(false);
        else if (config.FULL_METAL_BODY && SpawnHelper.hasAbility(pokemonEntity, "fullmetalbody")) cir.setReturnValue(false);
        else if (config.IMMUNITY && SpawnHelper.hasAbility(pokemonEntity, "immunity") && effect.getEffect() == MobEffects.POISON) cir.setReturnValue(false);
        else if (config.POISON && SpawnHelper.isType(pokemonEntity, "poison") && effect.getEffect() == MobEffects.POISON) cir.setReturnValue(false);
        else if (config.STEEL && SpawnHelper.isType(pokemonEntity, "steel") && effect.getEffect() == MobEffects.POISON) cir.setReturnValue(false);
        else if (config.ELECTRIC && SpawnHelper.isType(pokemonEntity, "electric") &&
            (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN || effect.getEffect() == EffectRegistry.PARALYSIS)) cir.setReturnValue(false);
        else if (config.LIMBER && SpawnHelper.hasAbility(pokemonEntity, "limber") &&
            (effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN || effect.getEffect() == EffectRegistry.PARALYSIS)) cir.setReturnValue(false);
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void addEffectInject(MobEffectInstance effect, @Nullable Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!((LivingEntity) (Object) this instanceof PokemonEntity pokemonEntity)) return;
        AbilityConfig config = LivelierPokemon.getAbilityConfig();
        if (config.MAGIC_BOUNCE && SpawnHelper.hasAbility(pokemonEntity.getPokemon(), "magicbounce") ||
            config.SYNCHRONIZE && SpawnHelper.hasAbility(pokemonEntity.getPokemon(), "synchronize") ||
            config.MIRROR_ARMOR && SpawnHelper.hasAbility(pokemonEntity.getPokemon(), "mirrorarmor")) {
            if (entity instanceof LivingEntity livingEntity && livingEntity.canBeAffected(effect))
                livingEntity.addEffect(effect, this);
            cir.setReturnValue(false);
        }
    }
}
