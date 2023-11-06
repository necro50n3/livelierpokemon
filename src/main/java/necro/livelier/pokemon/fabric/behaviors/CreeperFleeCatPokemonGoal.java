package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import necro.livelier.pokemon.fabric.LivelierPokemon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Iterator;

public class CreeperFleeCatPokemonGoal<T extends LivingEntity> extends AvoidEntityGoal<T>
{
    private List<T> entityList;
    public CreeperFleeCatPokemonGoal(PathfinderMob mob, Class<T> fleeFromType, float distance, double slowSpeed,
            double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
    }
    
    @Override
    public boolean canUse()
    {
        entityList = this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().expandTowards(this.maxDist, 3.0, this.maxDist), livingEntity -> true);
        if (entityList.isEmpty())
            return false;
        if (this.avoidClass != PokemonEntity.class)
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
        this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate((double)this.maxDist, 3.0, (double)this.maxDist), (livingEntity) -> {
            return true;
        }), this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vec3 == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }
}