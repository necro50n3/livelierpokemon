package necro.livelier.pokemon.fabric.network;

import necro.livelier.pokemon.common.network.SetWeatherPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class NetworkMessages {
    public static void registerPayload() {
        PayloadTypeRegistry.playS2C().register(SetWeatherPacket.PACKET_TYPE, SetWeatherPacket.CODEC);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SetWeatherPacket.PACKET_TYPE, NetworkMessages::handle);
    }

    public static void sendPacketToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        ServerPlayNetworking.send(player, packet);
    }

    public static void handle(SetWeatherPacket packet, ClientPlayNetworking.Context context) {
        packet.handle();
    }
}
