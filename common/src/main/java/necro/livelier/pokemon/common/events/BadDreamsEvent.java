package necro.livelier.pokemon.common.events;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.damage.LivelierDamageType;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BadDreamsEvent {
    public static void onPlayerSleep(Player player) {
        if (player.getSleepTimer() != 80) return;
        else if (!LivelierPokemon.getAbilityConfig().BAD_DREAMS) return;

        PokemonEntity pokemonEntity = TargetHelper.getNearestPokemon(
            player, 16, e -> SpawnHelper.hasAbility(e, "baddreams")
        );
        if (pokemonEntity == null) return;
        
        player.hurt(LivelierDamageType.getDamageSource(LivelierDamageType.BAD_DREAMS, pokemonEntity.level(), player, pokemonEntity),
            LivelierPokemon.getAbilityConfig().bad_dreams_damage
        );
        player.stopSleeping();
        player.displayClientMessage(Component.translatable("ability.livelierpokemon.baddreams"), false);
    }
}
