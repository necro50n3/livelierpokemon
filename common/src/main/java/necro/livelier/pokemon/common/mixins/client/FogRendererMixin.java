package necro.livelier.pokemon.common.mixins.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    private static float livelier_currentFogEnd = 0f;

    @Inject(method = "setupFog", at = @At("RETURN"))
    private static void injectSetupFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci) {
        if (ClientWeather.isSandstorm()) livelier_currentFogEnd = Math.min(1f, livelier_currentFogEnd + 0.05f);
        else livelier_currentFogEnd = Math.max(0f, livelier_currentFogEnd - 0.05f);

        if (ClientWeather.isSandstorm() || ClientWeather.wasSandstorm()) {
            RenderSystem.setShaderFogStart(0.0f);
            RenderSystem.setShaderFogEnd(15f * livelier_currentFogEnd);
            RenderSystem.setShaderFogColor(0.85f, 0.65f, 0.4f, 0.7f * livelier_currentFogEnd);
            RenderSystem.setShaderFogShape(FogShape.CYLINDER);
        }
    }
}
