package necro.livelier.pokemon.neoforge.network;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.network.SetWeatherPacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = LivelierPokemon.MODID)
public class NetworkMessages {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar payloadRegistrar = event.registrar(LivelierPokemon.MODID).versioned("1.0.0").optional();
        payloadRegistrar.playToClient(SetWeatherPacket.PACKET_TYPE, SetWeatherPacket.CODEC, NetworkMessages::handle);
    }

    public static void sendPacketToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void handle(SetWeatherPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> packet.handle((ClientLevel) context.player().level()));
    }
}
