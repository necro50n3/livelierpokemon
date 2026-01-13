package necro.livelier.pokemon.common.mixins.client;

import necro.livelier.pokemon.common.util.ITransition;
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
public abstract class ClientLevelMixin extends Level implements ITransition {
    protected ClientLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
    }

    @Shadow
    public abstract ClientLevel.@NotNull ClientLevelData getLevelData();

    @Unique
    private boolean livelier_isTransitioning = false;

    @Unique
    private double livelier_targetRainLevel = -1;

    @Unique
    private static final float TRANSITION_SPEED = 0.02f;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        if (!this.livelier_isTransitioning) return;
        if (this.livelier_targetRainLevel == -1) this.livelier_targetRainLevel = (this.isRaining() || this.isThundering()) ? 1 : 0;

        if (this.livelier_isRaining() && this.livelier_targetRainLevel == 0f) this.livelier_targetRainLevel = 1f;
        else if (this.livelier_isNotRaining() && this.livelier_targetRainLevel == 1f) this.livelier_targetRainLevel = 0f;

        double rainLevel = this.livelier_step(this.rainLevel, this.livelier_targetRainLevel);
        this.setRainLevel((float) rainLevel);
        if (rainLevel == this.livelier_targetRainLevel) this.livelier_isTransitioning = false;
    }

    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void getSkyColorInject(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseColor = cir.getReturnValue();
        cir.setReturnValue(this.livelier_getTargetSkyColor(baseColor));
    }

    @Inject(method = "getCloudColor", at = @At("RETURN"), cancellable = true)
    private void getCloudColorInject(float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        Vec3 baseColor = cir.getReturnValue();
        cir.setReturnValue(this.livelier_getTargetCloudColor(baseColor));
    }

    @Override
    public void livelier_setTransitioning() {
        this.livelier_isTransitioning = true;
    }

    @Override
    public boolean livelier_isTransitioning() {
        return this.livelier_isTransitioning;
    }

    @Unique
    private boolean livelier_isRaining() {
        if (ClientWeather.isRainingOrSnowing()) return true;
        else if (ClientWeather.isNotRainingOrSnowing()) return false;
        else return this.getLevelData().isRaining() || this.getLevelData().isThundering();
    }

    @Unique
    private boolean livelier_isNotRaining() {
        if (ClientWeather.isRainingOrSnowing()) return false;
        else if (ClientWeather.isNotRainingOrSnowing()) return true;
        else return !this.getLevelData().isRaining() && !this.getLevelData().isThundering();
    }

    @Unique
    private Vec3 livelier_getTargetSkyColor(Vec3 baseColor) {
        if (ClientWeather.isSandstorm()) return this.livelier_getInterpolatedColor(baseColor, new Vec3(1.0, 0.8, 0.6));
        else if (ClientWeather.isSunny()) return this.livelier_getInterpolatedColor(baseColor, new Vec3(0.5, 0.7, 1.0));
        else return baseColor;
    }

    @Unique
    private Vec3 livelier_getTargetCloudColor(Vec3 baseColor) {
        if (ClientWeather.isSandstorm()) return this.livelier_getInterpolatedColor(baseColor, new Vec3(0.85, 0.75, 0.55));
        else if (ClientWeather.isSunny()) return this.livelier_getInterpolatedColor(baseColor, new Vec3(1.0, 1.0, 0.9));
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
        if (Math.abs(delta) <= TRANSITION_SPEED) return target;
        return base + Math.copySign(TRANSITION_SPEED, delta);
    }
}
