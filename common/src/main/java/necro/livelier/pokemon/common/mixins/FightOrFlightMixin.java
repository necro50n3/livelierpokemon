package necro.livelier.pokemon.common.mixins;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.compat.ModCompat;
import necro.livelier.pokemon.common.helpers.ImmunityHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PokemonEntity.class)
public abstract class FightOrFlightMixin extends ShoulderRidingEntity {
    protected FightOrFlightMixin(EntityType<? extends ShoulderRidingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
    private void hurtInject(DamageSource damageSource, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (!ModCompat.FIGHTORFLIGHT.isLoaded()) return;
        else if (ImmunityHelper.isImmuneToFOF(damageSource, (PokemonEntity) (Object) this) || super.isInvulnerableTo(damageSource)) cir.setReturnValue(false);
    }
}
