package necro.livelier.pokemon.common.mixins.client;

import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
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
    private double livelier_rainProgress = 0f;
    @Unique
    private Vec3 livelier_skyColor = null;
    @Unique
    private Vec3 livelier_cloudColor = null;
    @Unique
    private static final double TRANSITION_SPEED = 0.05f;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        if (this.livelier_isRaining()) this.livelier_rainProgress = this.livelier_step(this.livelier_rainProgress, 1f);
        else this.livelier_rainProgress = this.livelier_step(this.livelier_rainProgress, 0f);
        this.setRainLevel((float) this.livelier_rainProgress);
    }

    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void getSkyColorInject(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseColor = cir.getReturnValue();
        if (this.livelier_skyColor == null) this.livelier_skyColor = baseColor;
        Vec3 targetColor = this.livelier_getTargetSkyColor(baseColor);
        Vec3 interpolated = this.livelier_getInterpolatedColor(this.livelier_skyColor, targetColor);
        this.livelier_skyColor = interpolated;
        cir.setReturnValue(interpolated);
    }

    @Inject(method = "getCloudColor", at = @At("RETURN"), cancellable = true)
    private void getCloudColorInject(float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseColor = cir.getReturnValue();
        if (this.livelier_cloudColor == null) this.livelier_cloudColor = baseColor;
        Vec3 targetColor = this.livelier_getTargetCloudColor(baseColor);
        Vec3 interpolated = this.livelier_getInterpolatedColor(this.livelier_cloudColor, targetColor);
        this.livelier_cloudColor = interpolated;
        cir.setReturnValue(interpolated);
    }

    @Unique
    private boolean livelier_isRaining() {
        if (ClientWeather.isRainingOrSnowing()) return true;
        else if (ClientWeather.isNotRainingOrSnowing()) return false;
        else return this.getLevelData().isRaining() || this.getLevelData().isThundering();
    }

    @Unique
    private Vec3 livelier_getTargetSkyColor(Vec3 baseColor) {
        if (ClientWeather.isSandstorm()) return new Vec3(1.0, 0.8, 0.6);
        else if (ClientWeather.isSunny()) return new Vec3(0.5, 0.7, 1.0);
        else return baseColor;
    }

    @Unique
    private Vec3 livelier_getTargetCloudColor(Vec3 baseColor) {
        if (ClientWeather.isSandstorm()) return new Vec3(0.85, 0.75, 0.55);
        else if (ClientWeather.isSunny()) return new Vec3(1.0, 1.0, 0.9);
        else return baseColor;
    }

    @Unique
    private Vec3 livelier_getInterpolatedColor(Vec3 baseColor, Vec3 targetColor) {
        return new Vec3(
            this.livelier_step(baseColor.x, targetColor.x),
            this.livelier_step(baseColor.y, targetColor.y),
            this.livelier_step(baseColor.z, targetColor.z)
        );
    }

    @Unique
    private double livelier_step(double base, double target) {
        double delta = target - base;
        if (Math.abs(delta) <= ClientLevelMixin.TRANSITION_SPEED) return target;
        return base + Math.copySign(ClientLevelMixin.TRANSITION_SPEED, delta);
    }
}
