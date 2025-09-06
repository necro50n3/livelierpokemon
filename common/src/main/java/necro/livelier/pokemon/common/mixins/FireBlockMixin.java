package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.weather.WeatherManager;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireBlockMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tickInject(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if (WeatherManager.isWeather(blockPos, serverLevel, WeatherType.RAIN, WeatherType.HEAVY_RAIN)) {
            serverLevel.removeBlock(blockPos, false);
            ci.cancel();
        }
    }
}
