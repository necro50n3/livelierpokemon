package necro.livelier.pokemon.common.events;

import com.cobblemon.mod.common.api.events.pokeball.PokeBallCaptureCalculatedEvent;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import necro.livelier.pokemon.common.goals.BallFetchGoal;
import necro.livelier.pokemon.common.helpers.SpawnHelper;
import necro.livelier.pokemon.common.helpers.TargetHelper;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class BallFetchEvent {
    public static void onPokeballCaptureCalculated(PokeBallCaptureCalculatedEvent event) {
        if (event.getCaptureResult().isSuccessfulCapture()) return;
        PokemonEntity fetcher = TargetHelper.getNearestPokemon(event.getPokemonEntity(), LivelierPokemon.getAbilityConfig().ball_fetch_radius,
            (pokemonEntity) -> {
                if (!SpawnHelper.hasAbility(pokemonEntity, "ballfetch")) return false;
                else if (pokemonEntity.getOwner() == null) return false;
                else return pokemonEntity.getOwner().is(event.getThrower());
            }
        );
        if (fetcher == null) return;
        else if (!fetcher.getPokemon().heldItem().isEmpty()) return;
        else if (!(fetcher.getOwner() instanceof Player)) return;
        else if (fetcher.isBusy()) {
            fetcher.getPokemon().swapHeldItem(event.getPokeBallEntity().getPokeBall().stack(1), false);
            return;
        }

        Optional<WrappedGoal> goal = fetcher.goalSelector.getAvailableGoals().stream().filter(wrappedGoal -> wrappedGoal.getGoal() instanceof BallFetchGoal).findFirst();
        if (goal.isPresent() && ((BallFetchGoal) goal.get().getGoal()).pokeBallEntity != null) return;
        fetcher.goalSelector.addGoal(3, new BallFetchGoal(fetcher, event.getPokeBallEntity(), event.getCaptureResult().getNumberOfShakes()));
    }
}
