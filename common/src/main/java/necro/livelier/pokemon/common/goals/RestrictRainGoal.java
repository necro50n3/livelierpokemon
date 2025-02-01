package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.util.GoalUtils;

public class RestrictRainGoal extends RestrictSunGoal {
    private final PokemonEntity pokemonEntity;

    public RestrictRainGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canUse() {
        return (pokemonEntity.level().isRaining() || pokemonEntity.level().isThundering()) &&
            pokemonEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
            GoalUtils.hasGroundPathNavigation(pokemonEntity);
    }
}
