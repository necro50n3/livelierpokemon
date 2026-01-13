package necro.livelier.pokemon.common.mixins;

import necro.livelier.pokemon.common.damage.LivelierDamageType;
import necro.livelier.pokemon.common.weather.WeatherManager;
import necro.livelier.pokemon.common.weather.WeatherType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Unique
    private int livelier_ticksInSnow;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(CallbackInfo ci) {
        if (this.tickCount % 20 != 0 || this.level().isClientSide) return;
        else if (!((LivingEntity) this instanceof Monster)) return;
        else if (!this.level().canSeeSky(this.blockPosition())) return;

        Monster monster = (Monster) (Object) this;
        if (WeatherManager.isWeather(monster.blockPosition(), monster.level(), WeatherType.SANDSTORM)) {
            if (!(monster instanceof Husk))
                monster.hurt(LivelierDamageType.getDamageSource(LivelierDamageType.SANDSTORM, monster.level(), monster, null), 1);
        }
        else if (WeatherManager.isWeather(monster.blockPosition(), monster.level(), WeatherType.SNOW)) {
            if (monster instanceof Stray) return;
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0, false, false, false));

            this.livelier_ticksInSnow = Math.min(this.getTicksRequiredToFreeze(), this.livelier_ticksInSnow + 1);
            if (this.tickCount % 40 == 0 && this.livelier_ticksInSnow > 4) {
                this.hurt(this.damageSources().freeze(), 1.0f);
            }
            return;
        }
        this.livelier_ticksInSnow = 0;
    }
}
