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
    private static final float TRANSITION_SPEED = 0.01f;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(BooleanSupplier booleanSupplier, CallbackInfo ci) {
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
