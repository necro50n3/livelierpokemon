package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import java.util.Iterator;

public class CreeperFleeCatPokemonGoal<T extends LivingEntity> extends FleeEntityGoal<T>
{
    private List<T> entityList;
    //private String[] catPokemon;
    public CreeperFleeCatPokemonGoal(PathAwareEntity mob, Class<T> fleeFromType, float distance, double slowSpeed,
            double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        //this.catPokemon = getCatPokemon();
    }
    
    @Override
    public boolean canStart()
    {
        entityList = this.mob.world.getEntitiesByClass(this.classToFleeFrom, this.mob.getBoundingBox().expand(this.fleeDistance, 3.0, this.fleeDistance), livingEntity -> true);
        if (entityList.isEmpty())
            return false;
        if (this.classToFleeFrom != PokemonEntity.class)
            return false;
        Iterator<T> iterator = entityList.iterator();
        
        while (iterator.hasNext())
        {
            PokemonEntity pokemonEntity = (PokemonEntity) iterator.next();
            String name = pokemonEntity.getPokemon().getDisplayName().getString();
            if (!LivelierPokemon.catSet.contains(name))
            {
                iterator.remove();
            }
        }
        
        if (entityList.isEmpty())
            return false;
        this.targetEntity = this.mob.world.getClosestEntity(entityList, TargetPredicate.createAttackable().setBaseMaxDistance(this.fleeDistance).setPredicate(inclusionSelector.and(extraInclusionSelector)), this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.targetEntity == null) {
            return false;
        }
        Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, ((PokemonEntity)this.targetEntity).getPos());
        if (vec3d == null) {
            return false;
        }
        if (((PokemonEntity)this.targetEntity).squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < ((PokemonEntity)this.targetEntity).squaredDistanceTo(this.mob)) {
            return false;
        }
        this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
        return this.fleePath != null;
    }
}
