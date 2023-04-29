package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.entity.ai.goal.Goal;

public class NosepassGoal extends Goal {
    PokemonEntity pokemonEntity;
    public NosepassGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canStart()
    {
        return true;
    }

    @Override
    public void start()
    {
        if (this.canStart())
        {
            pokemonEntity.getLookControl().lookAt(0, pokemonEntity.getEyeY(), 0);
        }
    }
    
    @Override
    public void tick()
    {
        this.start();
    }
}
