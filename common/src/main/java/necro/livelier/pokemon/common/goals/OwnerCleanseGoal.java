package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class OwnerCleanseGoal extends Goal {
    private final LivingEntity owner;
    private int tick;

    public OwnerCleanseGoal(PokemonEntity pokemonEntity) {
        this.owner = pokemonEntity.getOwner();
        this.tick = 0;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        if (--this.tick > 0) return;

        this.tick = this.adjustedTickDelay(120);
        this.owner.removeAllEffects();
    }
}
