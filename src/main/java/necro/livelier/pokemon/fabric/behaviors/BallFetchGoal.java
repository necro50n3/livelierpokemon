package necro.livelier.pokemon.fabric.behaviors;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.entity.pokeball.EmptyPokeBallEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.EntityGetter;
import java.util.List;

public class BallFetchGoal extends Goal{
    private PokemonEntity pokemonEntity;
    private Player owner;
    private ItemStack item;
    private EntityGetter world;
    private EmptyPokeBallEntity pokeballEntityInstance;
    private boolean pokeballHit;
    private boolean pokemonCaught;

    public BallFetchGoal(PokemonEntity pokemonEntity, String parameter)
    {
        this.pokemonEntity = pokemonEntity;
        this.world = pokemonEntity.level();
        pokeballHit = false;
        pokemonCaught = false;
    }
    

    @Override
    public boolean canUse()
    {
        if (owner == null)
        {
            owner = (Player) pokemonEntity.getOwner();
            return false;
        }
        pokeballEntityInstance = this.getThrownPokeBall();
        if (pokeballEntityInstance != null)
        {
            if (item == null)
                item = pokeballEntityInstance.getPokeBall().stack(1);
            this.checkPokeball();
        }
        if (item == null)
            return false;
        if (!pokeballHit || pokemonCaught)
            return false;
        if (!pokemonEntity.getPokemon().heldItem().isEmpty())
            return false;
        if (pokeballEntityInstance != null)
            return false;
        return true;
    }

    @Override
    public void start()
    {
        if (this.canUse())
        {
            pokemonEntity.getPokemon().swapHeldItem(item, false);
            pokeballHit = false;
            pokemonCaught = false;
            item = null;
            owner.sendSystemMessage(Component.literal(pokemonEntity.getDisplayName().getString() + " has retrieved your Poké Ball!"));
        }
    }

    public EmptyPokeBallEntity getThrownPokeBall()
    {
        AABB box = new AABB(pokemonEntity.getX()-10,pokemonEntity.getY()-10,pokemonEntity.getZ()-10,pokemonEntity.getX()+10,pokemonEntity.getY()+10,pokemonEntity.getZ()+10);
        List<EmptyPokeBallEntity> pokeBallList = world.getEntitiesOfClass(EmptyPokeBallEntity.class, box);
        for (EmptyPokeBallEntity pokeballEntityInstance : pokeBallList)
        {
            if (pokeballEntityInstance.getOwner().equals(owner))
                return pokeballEntityInstance;
        }
        return null;
    }

    public void checkPokeball()
    {
        if (pokeballEntityInstance.getCaptureState().get().toString().equals("3"))
            pokeballHit = true;
        else if (pokeballEntityInstance.getCaptureState().get().toString().equals("4"))
            pokemonCaught = true;
    }
}
