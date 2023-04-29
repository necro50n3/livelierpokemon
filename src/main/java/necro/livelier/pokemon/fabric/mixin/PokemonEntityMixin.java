package necro.livelier.pokemon.fabric.mixin;

import necro.livelier.pokemon.fabric.LivelierPokemon;
import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

@Mixin(PokemonEntity.class)
public abstract class PokemonEntityMixin extends TameableShoulderEntity
{
    protected PokemonEntityMixin(EntityType<PokemonEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Shadow(remap = false)
    public abstract Pokemon getPokemon();

    private void addNewGoals(PokemonEntity pokemonEntity)
    {
        ArrayList<String> keyList = LivelierPokemon.isNameInKey(LivelierPokemon.getAttributes(pokemonEntity.getPokemon()));
        if (keyList.size() > 0)
        {
            for (String validKey : keyList)
            {
                if (LivelierPokemon.map.get(validKey).get("trigger").equals("onSend"))
                    new LivelierPokemonManager(pokemonEntity, LivelierPokemon.map.get(validKey)).behaviourManager();
                else if (LivelierPokemon.map.get(validKey).get("trigger").equals("onSpawn") && pokemonEntity.getOwner() == null)
                    new LivelierPokemonManager(pokemonEntity, LivelierPokemon.map.get(validKey)).behaviourManager();
            }
        }    
    }

    @Override
    public void applyDamage(DamageSource source, float amount)
    {
        if (this.checkAbility().equals("unaware"))
        {
            this.applyDamageIfUnaware(source, amount);
            return;
        }
        super.applyDamage(source, amount);
    }

    private void applyDamageIfUnaware(DamageSource source, float amount)
    {
        if (this.isInvulnerableTo(source))
            return;
        float health = this.getHealth();
        float fixedDamage = 4;
        if (source.getSource() == null)
            fixedDamage = Math.min(fixedDamage, amount);
        this.setHealth(health - fixedDamage);
        this.getDamageTracker().onDamage(source, health, fixedDamage);
        this.emitGameEvent(GameEvent.ENTITY_DAMAGE);
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect)
    {
        if (this.checkAbility().equals("magicguard") || this.checkAbility().equals("goodasgold") || this.checkAbility().equals("fullmetalbody"))
            return false;
        else if (this.checkAbility().equals("immunity") && effect.getEffectType().equals(StatusEffects.POISON))
            return false;
        for (String type : this.checkType())
        {
            if (type.equals("poison") || type.equals("steel") && effect.getEffectType().equals(StatusEffects.POISON))
                return false;
            if (type.equals("electric") && effect.getEffectType().equals(StatusEffects.SLOWNESS))
                return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    private String checkAbility()
    {
        return this.getPokemon().getAbility().getName();
    }

    private ArrayList<String> checkType()
    {
        ArrayList<String> types = new ArrayList<String>();
        types.add(this.getPokemon().getPrimaryType().getName());
        if (this.getPokemon().getSecondaryType() != null)
            types.add(this.getPokemon().getSecondaryType().getName());
        return types;
    }

    private void createAftermath(DamageSource source)
    {
        float x = (float) this.getX();
        float y = (float) this.getY();
        float z = (float) this.getZ();
        this.world.createExplosion(this, new DamageSource("explosion"), null, x, y, z, 6, false, Explosion.DestructionType.NONE);
    }

    @Inject(method = "isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    public boolean isInvulnerableToInject(DamageSource source, CallbackInfoReturnable<Boolean> cir)
    {
        if (this.checkAbility().equals("wonderguard") && !(source.isFire() || source.equals(DamageSource.LAVA)))
            cir.setReturnValue(true);
        else if(this.checkAbility().equals("levitate") && source.equals(DamageSource.FALL))
            cir.setReturnValue(true);
        return false;
    }

    @Override
    public PassiveEntity createChild(ServerWorld var1, PassiveEntity var2)
    {
        return null;
    }

    /*
     * Temporary Inject Code for Cobblemon Version 1.3.
     * Remove code and replace with event listeners on update to 1.4.
     * See @LivelierPokemon.java
     */
    @Inject(method = "initGoals()V", at = @At("TAIL"))
    public void initGoalsInject(CallbackInfo info)
    {
        if (this.getPokemon() != null)
            this.addNewGoals((PokemonEntity) (TameableShoulderEntity) this);
    }

    @Inject(method = "isFireImmune()Z", at = @At("HEAD"), cancellable = true)
    public boolean isFireImmuneInject(CallbackInfoReturnable<Boolean> cir)
    {
        if (this.checkAbility().equals("flashfire"))
        {
            StatusEffectInstance effect = LivelierPokemonManager.getStatusEffect("strength", 200, 0);
            if (this.canHaveStatusEffect(effect))
                this.addStatusEffect(effect, this);
            cir.setReturnValue(true);
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if (this.checkAbility().equals("aftermath") && !source.isProjectile())
            this.createAftermath(source);
        super.onDeath(source);
    }

    @Override
    protected void onStatusEffectApplied(StatusEffectInstance effect, @Nullable Entity source)
    {
        if (source instanceof LivingEntity)
        {
            LivingEntity target = (LivingEntity) source;
            if (this.checkAbility().equals("magicbounce") || this.checkAbility().equals("synchronize") || this.checkAbility().equals("mirrorarmor"))
            {
                this.removeStatusEffect(effect.getEffectType());
                target.addStatusEffect(effect, this);
            }
        }
        super.onStatusEffectApplied(effect, source);
    }
}
