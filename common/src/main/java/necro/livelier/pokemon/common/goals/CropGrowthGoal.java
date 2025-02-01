package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;

import java.util.Random;

public class CropGrowthGoal extends Goal {
    private final PokemonEntity pokemonEntity;
    private final int radius;
    private final Level level;
    private final RandomSource random;

    public CropGrowthGoal(PokemonEntity pokemonEntity, int radius) {
        this.pokemonEntity = pokemonEntity;
        this.radius = radius;
        this.level = pokemonEntity.level();
        this.random = new SingleThreadedRandomSource(new Random().nextInt());
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        BlockPos.betweenClosedStream(this.pokemonEntity.getBoundingBox().inflate(this.radius, Math.min(this.radius, 8), this.radius))
            .filter(blockPos -> this.level.getBlockState(blockPos).getBlock() instanceof BonemealableBlock)
            .findAny()
            .ifPresent(blockPos -> this.level.getBlockState(blockPos).randomTick((ServerLevel) this.level, blockPos, this.random));
    }
}
