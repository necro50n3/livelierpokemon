package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;

public class EntityEffectGoal extends Goal
{
    private PokemonEntity pokemonEntity;
    private String parameter;
    private int checkTick;
    private List<LivingEntity> entityList;

    public EntityEffectGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
        this.parameter = parameter;
    }

    public void applyEffect(LivingEntity entity)
    {
        StatusEffectInstance effect = null;
        effect = LivelierPokemonManager.getStatusEffect(parameter, 320, 0);
        if (effect != null)
        {
            if (entity.canHaveStatusEffect(effect))
                entity.addStatusEffect(effect, pokemonEntity); 
        }
    }

    @Override
    public boolean canStart()
    {
        if (parameter.equals("illuminate") && pokemonEntity.getOwner() == null)
            return false;
        entityList = this.getLivingEntities();
        return !entityList.isEmpty();
    }

    @Override
    public void start()
    {
        if(this.canStart())
        {
            for (LivingEntity entity : entityList)
            {
                if (entity != pokemonEntity)
                    this.applyEffect(entity);
            }
        } 
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

    public List<LivingEntity> getLivingEntities()
    {
        Box box = new Box(pokemonEntity.getX()-16,pokemonEntity.getY()-16,pokemonEntity.getZ()-16,pokemonEntity.getX()+16,pokemonEntity.getY()+16,pokemonEntity.getZ()+16);
        return pokemonEntity.world.getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), box, EntityPredicates.VALID_ENTITY);
    }
    
}
