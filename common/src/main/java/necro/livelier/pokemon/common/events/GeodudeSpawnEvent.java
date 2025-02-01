package necro.livelier.pokemon.common.events;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GeodudeSpawnEvent {
    public static void onBlockBreak(Level level, BlockPos blockPos, BlockState blockState) {
        if (!blockState.is(BlockTags.BASE_STONE_OVERWORLD)) return;
        else if (!LivelierPokemon.getAbilityConfig().GEODUDE) return;
        else if (level.getRandom().nextFloat() >= LivelierPokemon.getAbilityConfig().geodude_spawn_rate) return;

        PokemonProperties properties = PokemonProperties.Companion.parse("species=geodude", " ", "=");
        properties.setLevel((int) (Math.random() * Math.min(30, Cobblemon.config.getMaxPokemonLevel()) + 1));
        if (properties.getSpecies() == null) return;
        properties.setShiny(false);
        PokemonEntity geodude = properties.createEntity(level);
        geodude.moveTo((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.05, (double) blockPos.getZ() + 0.5, 0.0f, 0.0f);
        level.addFreshEntity(geodude);
    }
}
