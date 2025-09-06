package necro.livelier.pokemon.common.mixins.client;

import necro.livelier.pokemon.common.weather.WeatherType;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "setLevel", at = @At("HEAD"))
    private void setLevelInject(ClientLevel clientLevel, ReceivingLevelScreen.Reason reason, CallbackInfo ci) {
        ClientWeather.setWeather(WeatherType.NONE);
        ClientWeather.clearPrevious();
    }
}
