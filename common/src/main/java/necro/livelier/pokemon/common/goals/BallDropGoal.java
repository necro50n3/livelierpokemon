package necro.livelier.pokemon.common.goals;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import necro.livelier.pokemon.common.LivelierPokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BallDropGoal extends FollowOwnerGoal {
    private final PokemonEntity pokemonEntity;

    public BallDropGoal(PokemonEntity pokemonEntity) {
        super(pokemonEntity, 1.2, 2, 1);
        this.pokemonEntity = pokemonEntity;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.pokemonEntity.getNavigation().isDone()) return true;
        else if (this.pokemonEntity.getPokemon().heldItem().isEmpty()) return false;

        ItemStack pokeBall = this.pokemonEntity.getPokemon().removeHeldItem();
        ItemEntity pokeBallEntity = new ItemEntity(this.pokemonEntity.level(), this.pokemonEntity.getX(),
            this.pokemonEntity.getY(), this.pokemonEntity.getZ(), pokeBall);
        this.pokemonEntity.level().addFreshEntity(pokeBallEntity);

        if (LivelierPokemon.getAbilityConfig().ball_fetch_show_message && this.pokemonEntity.getOwner() instanceof Player player) {
            player.displayClientMessage(Component.translatable(
                "ability.livelierpokemon.ballfetch", pokemonEntity.getName(), pokeBallEntity.getDisplayName()
            ), false);
        }

        return false;
    }

    @Override
    public void stop() {
        super.stop();
        this.pokemonEntity.goalSelector.removeGoal(this);
    }
}
