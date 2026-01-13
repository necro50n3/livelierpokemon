package necro.livelier.pokemon.common.network;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.util.ITransition;
import necro.livelier.pokemon.common.weather.WeatherType;
import necro.livelier.pokemon.common.weather.client.ClientWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetWeatherPacket(WeatherType weatherType) implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(LivelierPokemon.MODID, "set_weather");
    public static final Type<SetWeatherPacket> PACKET_TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, SetWeatherPacket> CODEC = StreamCodec.ofMember(SetWeatherPacket::write, SetWeatherPacket::read);

    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(this.weatherType);
    }

    public static SetWeatherPacket read(FriendlyByteBuf buf) {
        return new SetWeatherPacket(buf.readEnum(WeatherType.class));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return PACKET_TYPE; }

    public void handle(ClientLevel level) {
        ClientWeather.setWeather(this.weatherType);
        ((ITransition) level).livelier_setTransitioning();
    }
}
