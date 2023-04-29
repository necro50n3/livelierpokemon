package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;

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
        StatusEffectInstance effect = null;
        if (!parameter.equals("clear"))
        {
            int amplifier = (pokemonEntity.getPokemon().getFriendship() >= 220 ? 1 : 0);
            effect = LivelierPokemonManager.getStatusEffect(parameter, 320, amplifier);
            if (effect != null)
            {
                if (owner.canHaveStatusEffect(effect))
                    owner.addStatusEffect(effect, pokemonEntity); 
            }
        }
        else
        {
            if(Math.random() < 0.30)
                owner.clearStatusEffects();
        }
    }

    @Override
    public boolean canStart()
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
        if(this.canStart() && pokemonEntity.distanceTo(pokemonEntity.getOwner()) < 8)
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
