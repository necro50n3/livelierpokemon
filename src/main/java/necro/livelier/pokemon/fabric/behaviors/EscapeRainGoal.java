package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.world.World;

public class EscapeRainGoal extends EscapeSunlightGoal {
    private final World world;
    private PokemonEntity pokemonEntity;

    public EscapeRainGoal(PokemonEntity pokemonEntity, String speed)
    {
        super(pokemonEntity, Double.parseDouble(speed));
        this.pokemonEntity = pokemonEntity;
        this.world = pokemonEntity.world;
    }
    
    @Override
    public boolean canStart() {
        if (pokemonEntity.getTarget() != null) {
            return false;
        }
        if (!world.isRaining() && !world.isThundering()) {
            return false;
        }
        if (!world.isSkyVisible(pokemonEntity.getBlockPos())) {
            return false;
        }
        return super.targetShadedPos();
    }

    @Override
    public void start() {
        super.start();
    }
}
