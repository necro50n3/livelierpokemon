package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;

public class DampGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final int radius;

    public DampGoal(PokemonEntity pokemonEntity, int radius) {
        this.pokemonEntity = pokemonEntity;
        this.radius = radius;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        this.pokemonEntity.level()
            .getEntitiesOfClass(Creeper.class, this.pokemonEntity.getBoundingBox().inflate(radius, 2, radius))
            .forEach(creeper -> creeper.setSwellDir(-1));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
