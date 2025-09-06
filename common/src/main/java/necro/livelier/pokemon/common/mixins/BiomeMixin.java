package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    private void getPrecipitationAtInject(BlockPos blockPos, CallbackInfoReturnable<Biome.Precipitation> cir) {
        if (ClientWeather.wasRaining()) cir.setReturnValue(Biome.Precipitation.RAIN);
        else if (ClientWeather.wasSnowing()) cir.setReturnValue(Biome.Precipitation.SNOW);
        else if (ClientWeather.isRaining()) cir.setReturnValue(Biome.Precipitation.RAIN);
        else if (ClientWeather.isSnowing()) cir.setReturnValue(Biome.Precipitation.SNOW);
        else if (ClientWeather.isNotRainingOrSnowing()) cir.setReturnValue(Biome.Precipitation.NONE);
    }

    @Inject(method = "hasPrecipitation", at = @At("HEAD"), cancellable = true)
    private void hasPrecipitationInject(CallbackInfoReturnable<Boolean> cir) {
        if (ClientWeather.wasRaining()) cir.setReturnValue(true);
        else if (ClientWeather.wasSnowing()) cir.setReturnValue(true);
        else if (ClientWeather.isRaining()) cir.setReturnValue(true);
        else if (ClientWeather.isSnowing()) cir.setReturnValue(true);
        else if (ClientWeather.isNotRainingOrSnowing()) cir.setReturnValue(false);
    }
}
