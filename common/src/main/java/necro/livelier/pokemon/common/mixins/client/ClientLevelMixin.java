package necro.livelier.pokemon.common.mixins.client;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {
    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Shadow
    public abstract ClientLevel.@NotNull ClientLevelData getLevelData();

    @Unique
    private float weatherProgress = 0f;
    @Unique
    private float rainProgress = 0f;
    @Unique
    private float sunProgress = 0f;
    @Unique
    private static final float TRANSITION_SPEED = 0.05f;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        if (ClientWeather.isActive())
            this.weatherProgress = Math.min(1f, this.weatherProgress + TRANSITION_SPEED);
        else this.weatherProgress = Math.max(0f, this.weatherProgress - TRANSITION_SPEED);
        if (this.weatherProgress == 0f && ClientWeather.hasPrevious()) ClientWeather.clearPrevious();

        if (ClientWeather.isRainingOrSnowing() || this.getLevelData().isRaining() || this.getLevelData().isThundering())
            this.rainProgress = Math.min(1f, this.rainProgress + TRANSITION_SPEED);
        else this.rainProgress = Math.max(0f, this.rainProgress - TRANSITION_SPEED);
        if (!this.getLevelData().isRaining()) this.setRainLevel(this.rainProgress);

        if (ClientWeather.isSunny() || ClientWeather.isSandstorm()) this.sunProgress = Math.min(1f, this.sunProgress + TRANSITION_SPEED);
        else this.sunProgress = Math.max(0f, this.sunProgress - TRANSITION_SPEED);
    }

    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void getSkyColorInject(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseColor = cir.getReturnValue();
        Vec3 targetColor = ClientWeather.isSandstorm() ? new Vec3(1.0, 0.8, 0.6) : new Vec3(0.5, 0.7, 1.0);
        cir.setReturnValue(this.getInterpolatedSkyColor(baseColor, targetColor));
    }

    @Inject(method = "getCloudColor", at = @At("RETURN"), cancellable = true)
    private void getCloudColorInject(float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseCloud = cir.getReturnValue();
        Vec3 targetColor = ClientWeather.isSandstorm() ? new Vec3(0.85, 0.75, 0.55) : new Vec3(1.0, 1.0, 0.9);
        cir.setReturnValue(this.getInterpolatedSkyColor(baseCloud, targetColor));
    }

    @Unique
    private Vec3 getInterpolatedSkyColor(Vec3 baseColor, Vec3 sunnyColor) {
        return new Vec3(
            Mth.lerp(sunProgress, baseColor.x, sunnyColor.x),
            Mth.lerp(sunProgress, baseColor.y, sunnyColor.y),
            Mth.lerp(sunProgress, baseColor.z, sunnyColor.z)
        );
    }
}
