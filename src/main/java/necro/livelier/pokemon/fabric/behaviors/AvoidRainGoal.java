package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.util.GoalUtils;

public class AvoidRainGoal extends RestrictSunGoal {
    private PokemonEntity pokemonEntity;

    public AvoidRainGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity);
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canUse() {
        return (pokemonEntity.level.isRaining() || pokemonEntity.level.isThundering()) && pokemonEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && GoalUtils.hasGroundPathNavigation(pokemonEntity);
    }
}
