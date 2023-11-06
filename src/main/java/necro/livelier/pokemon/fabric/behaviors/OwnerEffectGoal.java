package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.effect.MobEffectInstance;

public class OwnerEffectGoal extends Goal
{
    private PokemonEntity pokemonEntity;
    private LivingEntity owner;
    private String parameter;
    private int checkTick;

    public OwnerEffectGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
        this.parameter = parameter;
    }

    public void applyEffect()
    {
        MobEffectInstance effect = null;
        if (!parameter.equals("clear"))
        {
            int amplifier = (pokemonEntity.getPokemon().getFriendship() >= 220 ? 1 : 0);
            effect = LivelierPokemonManager.getStatusEffect(parameter, 320, amplifier);
            if (effect != null)
            {
                if (owner.canBeAffected(effect))
                    owner.addEffect(effect, pokemonEntity); 
            }
        }
        else
        {
            if(Math.random() < 0.30)
                owner.removeAllEffects();
        }
    }

    @Override
    public boolean canUse()
    {
        if (owner == null)
        {
            owner = (LivingEntity) this.pokemonEntity.getOwner();
            return false;
        }
        return true;
    }

    @Override
    public void start()
    {
        if(this.canUse() && pokemonEntity.distanceTo(pokemonEntity.getOwner()) < 8)
            this.applyEffect();
    }

    @Override
    public void tick()
    {
        ++checkTick;
        if (checkTick > 30)
        {
            this.start();
            checkTick = 0;
        }
    }
}
