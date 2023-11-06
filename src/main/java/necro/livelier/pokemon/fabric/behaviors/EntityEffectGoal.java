package necro.livelier.pokemon.fabric.behaviors;

import java.util.List;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemonManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.AABB;

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
        MobEffectInstance effect = null;
        effect = LivelierPokemonManager.getStatusEffect(parameter, 320, 0);
        if (effect != null)
        {
            if (entity.canBeAffected(effect))
                entity.addEffect(effect, pokemonEntity); 
        }
    }

    @Override
    public boolean canUse()
    {
        if (parameter.equals("illuminate") && pokemonEntity.getOwner() == null)
            return false;
        entityList = this.getLivingEntities();
        return !entityList.isEmpty();
    }

    @Override
    public void start()
    {
        if(this.canUse())
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
        AABB box = new AABB(pokemonEntity.getX()-16,pokemonEntity.getY()-16,pokemonEntity.getZ()-16,pokemonEntity.getX()+16,pokemonEntity.getY()+16,pokemonEntity.getZ()+16);
        return this.pokemonEntity.level().getEntitiesOfClass(LivingEntity.class, box);
    }
    
}
