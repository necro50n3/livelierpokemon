package necro.livelier.pokemon.common.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class VoltorbAttackGoal extends MeleeAttackGoal {
    public VoltorbAttackGoal(PathfinderMob pathfinderMob, double d, boolean bl) {
        super(pathfinderMob, d, bl);
    }

    @Override
    protected boolean canPerformAttack(LivingEntity livingEntity) {
        return false;
    }
}
