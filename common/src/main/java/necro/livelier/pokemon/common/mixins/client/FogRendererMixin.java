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
    private static float currentFogEnd = 0f;

    @Inject(method = "setupFog", at = @At("RETURN"))
    private static void injectSetupFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci) {
        if (ClientWeather.isSandstorm()) currentFogEnd = Math.min(1f, currentFogEnd + 0.05f);
        else currentFogEnd = Math.max(0f, currentFogEnd - 0.05f);

        if (ClientWeather.isSandstorm() || ClientWeather.wasSandstorm()) {
            RenderSystem.setShaderFogStart(0.0f);
            RenderSystem.setShaderFogEnd(15f * currentFogEnd);
            RenderSystem.setShaderFogColor(0.85f, 0.65f, 0.4f, 0.7f * currentFogEnd);
            RenderSystem.setShaderFogShape(FogShape.CYLINDER);
        }
    }
}
