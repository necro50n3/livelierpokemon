package necro.livelier.pokemon.fabric.mixin;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.gameevent.GameEvent;

@Mixin(LivingEntity.class)
public abstract class PokemonEntityMixin extends Entity
{
    protected PokemonEntityMixin(EntityType<? extends Entity> entityType, Level world)
    {
        super(entityType, world);
    }

    @Inject(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At("HEAD"))
    public void actuallyHurtInject(DamageSource source, float amount, CallbackInfo info)
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (this.checkAbility().equals("unaware"))
            {
                this.applyDamageIfUnaware(source, amount);
                info.cancel();
            }
        }
    }

    private void applyDamageIfUnaware(DamageSource source, float amount)
    {
        LivingEntity entity = (LivingEntity)(Entity)this;
        if (this.isInvulnerableTo(source))
            return;
        float health = entity.getHealth();
        float fixedDamage = 4;
        if (source.getEntity() == null)
            fixedDamage = Math.min(fixedDamage, amount);
        entity.setHealth(health - fixedDamage);
        entity.getCombatTracker().recordDamage(source, fixedDamage);
        this.gameEvent(GameEvent.ENTITY_DAMAGE);
    }

    @Inject(method = "canBeAffected(Lnet/minecraft/world/effect/MobEffectInstance;)Z", at = @At("HEAD"), cancellable = true)
    public boolean canBeAffectedInject(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir)
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (this.checkAbility().equals("magicguard") || this.checkAbility().equals("goodasgold") || this.checkAbility().equals("fullmetalbody"))
                cir.setReturnValue(false);
            else if (this.checkAbility().equals("immunity") && effect.getEffect() == MobEffects.POISON)
                cir.setReturnValue(false);
            for (String type : this.checkType())
            {
                if (type.equals("poison") || type.equals("steel") && effect.getEffect() == MobEffects.POISON)
                    cir.setReturnValue(false);
                if (type.equals("electric") && effect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN)
                    cir.setReturnValue(false);
            }
        }
        return true;
    }

    private String checkAbility()
    {
        PokemonEntity pokemonEntity = (PokemonEntity)(Entity)this;
        return pokemonEntity.getPokemon().getAbility().getName();
    }

    private ArrayList<String> checkType()
    {
        PokemonEntity pokemonEntity = (PokemonEntity)(Entity)this;
        ArrayList<String> types = new ArrayList<String>();
        types.add(pokemonEntity.getPokemon().getPrimaryType().getName());
        if (pokemonEntity.getPokemon().getSecondaryType() != null)
            types.add(pokemonEntity.getPokemon().getSecondaryType().getName());
        return types;
    }

    private void createAftermath(DamageSource source)
    {
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3, ExplosionInteraction.NONE);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source)
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (this.checkAbility().equals("wonderguard") && !(source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA)))
                return true;
            else if(this.checkAbility().equals("levitate") && source.is(DamageTypes.FALL))
                return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean fireImmune()
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (this.checkAbility().equals("flashfire"))
            {
                LivingEntity entity = (LivingEntity)(Entity)this;
                MobEffectInstance effect = LivelierPokemonManager.getStatusEffect("strength", 200, 0);
                if (entity.canBeAffected(effect))
                    entity.addEffect(effect, this);
                return true;
            }
        }
        return super.fireImmune();
    }

    @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"))
    public void dieInject(DamageSource source, CallbackInfo info)
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (this.checkAbility().equals("aftermath") && !(source.is(DamageTypes.ARROW) || source.is(DamageTypes.MOB_PROJECTILE) || source.is(DamageTypes.THROWN)))
                this.createAftermath(source);
        }
    }

    @Inject(method = "onEffectAdded(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    public void onEffectAddedInject(MobEffectInstance effect, @Nullable Entity source, CallbackInfo info)
    {
        if ((Entity)this instanceof PokemonEntity)
        {
            if (source instanceof LivingEntity)
            {
                LivingEntity target = (LivingEntity) source;
                if (this.checkAbility().equals("magicbounce") || this.checkAbility().equals("synchronize") || this.checkAbility().equals("mirrorarmor"))
                {
                    LivingEntity entity = (LivingEntity)(Entity)this;
                    entity.removeEffect(effect.getEffect());
                    target.addEffect(effect, this);
                }
            }
        }
    }
}
