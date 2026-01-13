package necro.livelier.pokemon.common.mixins.client;

import necro.livelier.pokemon.common.util.ITransition;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Shadow
    private ClientLevel level;

    @Unique
    private static final Set<ClientboundGameEventPacket.Type> livelier_rainEvents = Set.of(
        ClientboundGameEventPacket.START_RAINING,
        ClientboundGameEventPacket.STOP_RAINING,
        ClientboundGameEventPacket.RAIN_LEVEL_CHANGE
    );

    @Inject(method = "handleGameEvent", at = @At("HEAD"), cancellable = true)
    private void handleGameEventInject(ClientboundGameEventPacket packet, CallbackInfo ci) {
        if (livelier_rainEvents.contains(packet.getEvent()) &&
            (((ITransition) level).livelier_isTransitioning() || ClientWeather.isActive())
        ) {
            if (packet.getEvent() == ClientboundGameEventPacket.START_RAINING) level.getLevelData().setRaining(true);
            if (packet.getEvent() == ClientboundGameEventPacket.STOP_RAINING) level.getLevelData().setRaining(false);
            ci.cancel();
        }
    }
}
