package necro.livelier.pokemon.neoforge.events;

import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.events.BadDreamsEvent;
import necro.livelier.pokemon.common.events.GeodudeSpawnEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = LivelierPokemon.MODID)
public class GameEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        BadDreamsEvent.onPlayerSleep(event.getEntity());
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        GeodudeSpawnEvent.onBlockBreak(event.getPlayer().level(), event.getPos(), event.getState());
    }
}
