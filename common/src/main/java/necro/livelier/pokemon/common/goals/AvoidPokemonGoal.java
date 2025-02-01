package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class AvoidPokemonGoal extends AvoidEntityGoal<PokemonEntity>
{
    private final Predicate<PokemonEntity> avoidPredicate;

    public AvoidPokemonGoal(PathfinderMob mob, float distance, double slowSpeed,
                            double fastSpeed, Predicate<PokemonEntity> avoidPredicate) {
        super(mob, PokemonEntity.class, distance, slowSpeed, fastSpeed);
        this.avoidPredicate = avoidPredicate;
    }
    
    @Override
    public boolean canUse()
    {
        this.toAvoid = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(
            PokemonEntity.class, this.mob.getBoundingBox().inflate(this.maxDist, 3.0, this.maxDist), this.avoidPredicate
        ), TargetingConditions.forCombat().range(this.maxDist), this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());

        if (this.toAvoid == null) return false;
        else {
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