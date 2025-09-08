package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.PathType;

public class BallFetchGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    public EmptyPokeBallEntity pokeBallEntity;
    private final int delay;
    private int tick;
    private int recalcPath;
    private float oldWaterCost;

    public BallFetchGoal(PokemonEntity pokemonEntity, EmptyPokeBallEntity pokeBallEntity, int shakes) {
        this.pokemonEntity = pokemonEntity;
        this.pokeBallEntity = pokeBallEntity;
        this.delay = 20 + shakes * 10;
        this.tick = 0;
        this.recalcPath = 0;
    }

    @Override
    public boolean canUse() {
        if (++this.tick < this.delay) return false;
        else if (this.pokemonEntity.isBusy()) return false;
        else if (!this.pokemonEntity.getPokemon().heldItem().isEmpty()) this.pokeBallEntity = null;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.pokemonEntity.getNavigation().isDone()) return true;
        else if (this.pokeBallEntity == null) return false;
        else if (this.pokemonEntity.isBusy()) return false;

        ItemStack item = this.pokeBallEntity.getPokeBall().stack(1);
        pokemonEntity.getPokemon().swapHeldItem(item, false);
        this.pokemonEntity.goalSelector.addGoal(3, new BallDropGoal(this.pokemonEntity));
        this.stop();
        return false;
    }

    @Override
    public void start() {
        this.oldWaterCost = this.pokemonEntity.getPathfindingMalus(PathType.WATER);
        this.pokemonEntity.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.pokeBallEntity = null;
        this.pokemonEntity.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        if (this.pokeBallEntity == null) return;
        this.pokemonEntity.getLookControl().setLookAt(this.pokeBallEntity, 10.0F, (float)this.pokemonEntity.getMaxHeadXRot());
        if (--this.recalcPath <= 0) {
            this.recalcPath = this.adjustedTickDelay(10);
            this.pokemonEntity.getNavigation().moveTo(this.pokeBallEntity, 1.3);
        }
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
