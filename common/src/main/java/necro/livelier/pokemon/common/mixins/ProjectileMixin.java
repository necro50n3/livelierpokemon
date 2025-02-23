package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import necro.livelier.pokemon.common.util.IFreeze;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity implements IFreeze {
    @Shadow public abstract void lerpMotion(double d, double e, double f);

    @Unique
    public int livelierpokemon$ticksFrozen;

    public ProjectileMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void tickInject(CallbackInfo ci) {
        if (this.livelierpokemon$ticksFrozen == 1) {
            this.noPhysics = false;
            this.setNoGravity(false);
        }
        else if (this.livelierpokemon$ticksFrozen > 0) {
            this.livelierpokemon$ticksFrozen--;
            this.setDeltaMovement(0, 0, 0);
            this.noPhysics = true;
            this.setNoGravity(true);
            ci.cancel();
        }
    }

    @Override
    public void livelierpokemon$SetTicksFrozen(int ticks) {
        if ((Entity) this instanceof EmptyPokeBallEntity) return;
        this.livelierpokemon$ticksFrozen = ticks;
    }
}
