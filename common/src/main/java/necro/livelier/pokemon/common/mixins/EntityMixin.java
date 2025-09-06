package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.weather.WeatherManager;
import necro.livelier.pokemon.common.weather.WeatherType;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Level level();

    @Shadow public abstract BlockPos blockPosition();

    @Inject(method = "isInWaterOrRain", at = @At("HEAD"), cancellable = true)
    private void isInWaterOrRainInject(CallbackInfoReturnable<Boolean> cir) {
        if (this.level().isClientSide()) {
            if (ClientWeather.isRaining()) cir.setReturnValue(true);
        }
        else {
            if (WeatherManager.isWeather(this.blockPosition(), this.level(), WeatherType.HEAVY_RAIN, WeatherType.RAIN)) cir.setReturnValue(true);
        }
    }
}
