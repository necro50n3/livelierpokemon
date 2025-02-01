package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.util.IFreeze;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;

public class BlockProjectileGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final int radius;

    public BlockProjectileGoal(PokemonEntity pokemonEntity, int radius) {
        this.pokemonEntity = pokemonEntity;
        this.radius = radius;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        this.pokemonEntity.level().getEntitiesOfClass(Projectile.class, this.pokemonEntity.getBoundingBox().inflate(this.radius)).forEach(
            projectile -> ((IFreeze) projectile).livelierpokemon$SetTicksFrozen(10)
        );
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
