package necro.livelier.pokemon.fabric.mixins;

import necro.livelier.pokemon.common.events.BadDreamsEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void LivelierPokemon$tick(CallbackInfo ci) {
        BadDreamsEvent.onPlayerSleep((Player) (Object) this);
    }
}
